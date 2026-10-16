package com.example.model

import com.example.R

data class MemberProfile(
    val name: String = "Willie Schulist",
    val memberId: String = "HI1418872904-BB",
    val planTier: String = "Premium",
    val avatarRes: Int = R.drawable.img_user_willie
)

data class PremiumSummary(
    val title: String = "3 Upcoming Premiums",
    val dueDaysText: String = "Due in 27 days",
    val amountFormatted: String = "$2,322.98"
)

enum class ClaimStatus(val label: String) {
    IN_PROGRESS("In Progress"),
    APPROVED("Approved"),
    UNDER_REVIEW("Under Review")
}

data class ClaimItem(
    val id: String,
    val title: String,
    val hospitalName: String,
    val amount: String,
    val date: String,
    val status: ClaimStatus
)

data class EventArticle(
    val id: String,
    val tag: String,
    val title: String,
    val imageRes: Int,
    val readTime: String = "4 min read",
    val fullSummary: String = ""
)

data class TeleconsultAppointment(
    val doctorName: String = "Dr. Emily Carter",
    val consultationType: String = "Video",
    val appointmentTime: String = "June 10, 2025 | 10:00 AM",
    val doctorAvatarRes: Int = R.drawable.img_doctor_emily,
    val checklist: List<String> = listOf(
        "Lab Test Booking Option Available",
        "Digital Prescription Issued (Covered by Insurance)"
    )
)

data class HospitalItem(
    val id: String,
    val name: String,
    val category: String,
    val address: String,
    val distance: String,
    val hours: String,
    val phone: String,
    val imageRes: Int,
    val isEmergencyOpen: Boolean = true
)

enum class AppTab {
    HOME,
    CLAIMS,
    REWARDS,
    MORE
}
