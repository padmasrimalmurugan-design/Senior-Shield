package com.example.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class UserProfile(
    val userId: String = "",
    val displayName: String = "",
    val language: String = "ENGLISH",
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val largeFontEnabled: Boolean = true,
    val autoTtsEnabled: Boolean = true,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class ScamCheckRecord(
    val id: String = "",
    val userId: String = "",
    val type: String = "message",
    val content: String = "",
    val riskLevel: String = "UNKNOWN",
    val headline: String = "",
    val explanation: String = "",
    val flaggedPhrases: List<String> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null
)
