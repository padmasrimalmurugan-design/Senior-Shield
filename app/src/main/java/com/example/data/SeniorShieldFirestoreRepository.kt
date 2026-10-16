package com.example.data

import android.content.Context
import com.example.R
import com.example.model.ScamCheckRecord
import com.example.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class SeniorShieldFirestoreRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        ),
        FirebaseAuth.getInstance()
    )

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")
    }

    fun observeProfile(): Flow<UserProfile?> = flow {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        emitAll(
            docRef.snapshots()
                .map { snapshot ->
                    if (snapshot.exists()) {
                        snapshot.toObject(UserProfile::class.java)?.copy(userId = snapshot.id)
                    } else {
                        null
                    }
                }
                .catch { error ->
                    if (error is Exception) {
                        handleFirestoreError(error, OperationType.GET, docRef.path)
                    }
                    throw error
                }
        )
    }

    suspend fun saveProfile(
        displayName: String,
        language: String,
        emergencyContactName: String = "",
        emergencyContactPhone: String = "",
        largeFontEnabled: Boolean = true,
        autoTtsEnabled: Boolean = true
    ) {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        try {
            val existing = docRef.get().await()
            val payload = mutableMapOf<String, Any>(
                "userId" to uid,
                "displayName" to displayName,
                "language" to language,
                "emergencyContactName" to emergencyContactName,
                "emergencyContactPhone" to emergencyContactPhone,
                "largeFontEnabled" to largeFontEnabled,
                "autoTtsEnabled" to autoTtsEnabled,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            if (!existing.exists() || existing.get("createdAt") == null) {
                payload["createdAt"] = FieldValue.serverTimestamp()
            } else {
                payload["createdAt"] = existing.get("createdAt")!!
            }
            docRef.set(payload).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            throw e
        }
    }

    suspend fun recordCheckLog(
        type: String,
        content: String,
        riskLevel: String,
        headline: String,
        explanation: String,
        flaggedPhrases: List<String>
    ): String {
        val uid = requireUserId()
        val colRef = db.collection("users").document(uid).collection("checkLogs")
        val docRef = colRef.document()
        try {
            val payload = mapOf(
                "id" to docRef.id,
                "userId" to uid,
                "type" to type,
                "content" to content,
                "riskLevel" to riskLevel,
                "headline" to headline,
                "explanation" to explanation,
                "flaggedPhrases" to flaggedPhrases,
                "createdAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            return docRef.id
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, colRef.path)
            throw e
        }
    }

    fun observeCheckLogs(): Flow<List<ScamCheckRecord>> = flow {
        val uid = requireUserId()
        val colRef = db.collection("users").document(uid).collection("checkLogs")
        emitAll(
            colRef.orderBy("createdAt", Query.Direction.DESCENDING)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(ScamCheckRecord::class.java)
                }
                .catch { error ->
                    if (error is Exception) {
                        handleFirestoreError(error, OperationType.LIST, colRef.path)
                    }
                    throw error
                }
        )
    }
}
