package com.example.data

import com.example.base.FirestoreEmulatorTestBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SeniorShieldRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun saveAndObserveProfile_authenticatedUser_success() = runBlocking {
        val uid = signInTestUser("pensioner@example.com")
        val repository = SeniorShieldFirestoreRepository(firestore, auth)

        withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.saveProfile(
                displayName = "Ramasamy Pensioner",
                language = "TAMIL",
                emergencyContactName = "Suresh (Son)",
                emergencyContactPhone = "9876543210"
            )
        }

        val profile = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.observeProfile().first { it != null }
        }

        assertNotNull(profile)
        assertEquals(uid, profile?.userId)
        assertEquals("Ramasamy Pensioner", profile?.displayName)
        assertEquals("TAMIL", profile?.language)
    }

    @Test
    fun recordAndObserveCheckLogs_authenticatedUser_success() = runBlocking {
        val uid = signInTestUser("pensioner2@example.com")
        val repository = SeniorShieldFirestoreRepository(firestore, auth)

        val logId = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.recordCheckLog(
                type = "message",
                content = "Your pension is stopped. Send OTP to unlock.",
                riskLevel = "HIGH_RISK",
                headline = "High Risk Scam",
                explanation = "Threatens pension stoppage and demands OTP.",
                flaggedPhrases = listOf("pension is stopped", "Send OTP")
            )
        }

        assertTrue(logId.isNotBlank())

        val logs = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.observeCheckLogs().first { list -> list.any { it.id == logId } }
        }

        assertTrue(logs.any { it.id == logId })
    }
}
