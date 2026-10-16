package com.example.data

import com.example.R
import com.example.model.ClaimItem
import com.example.model.ClaimStatus
import com.example.model.EventArticle
import com.example.model.HospitalItem
import com.example.model.PremiumSummary
import com.example.model.TeleconsultAppointment
import com.example.model.MemberProfile

object HealthInsuranceRepository {

    val userProfile = MemberProfile(
        name = "Willie Schulist",
        memberId = "HI1418872904-BB",
        planTier = "Premium",
        avatarRes = R.drawable.img_user_willie
    )

    val premiumSummary = PremiumSummary(
        title = "3 Upcoming Premiums",
        dueDaysText = "Due in 27 days",
        amountFormatted = "$2,322.98"
    )

    val claims = listOf(
        ClaimItem(
            id = "CL-9842",
            title = "Specialist Consultation & Labs",
            hospitalName = "Harmony General Hospital",
            amount = "$420.00",
            date = "Oct 12, 2026",
            status = ClaimStatus.IN_PROGRESS
        ),
        ClaimItem(
            id = "CL-9841",
            title = "Dental & Oral Restoration",
            hospitalName = "VitalSpring Medical",
            amount = "$280.00",
            date = "Oct 08, 2026",
            status = ClaimStatus.IN_PROGRESS
        ),
        ClaimItem(
            id = "CL-8820",
            title = "Cardiology Teleconsultation",
            hospitalName = "Harmony General Hospital",
            amount = "$195.00",
            date = "Sep 22, 2026",
            status = ClaimStatus.APPROVED
        )
    )

    val hospitals = listOf(
        HospitalItem(
            id = "hosp_1",
            name = "Harmony General Hospital",
            category = "General & Primary Care",
            address = "Fenimore St 22A",
            distance = "2.3km",
            hours = "Open • Close at 23:30",
            phone = "+1 (555) 234-8901",
            imageRes = R.drawable.img_hospital_harmony,
            isEmergencyOpen = true
        ),
        HospitalItem(
            id = "hosp_2",
            name = "VitalSpring Medical",
            category = "Dental & Oral Health",
            address = "Fenimore St 22A",
            distance = "2.3km",
            hours = "Open • Close at 20:00",
            phone = "+1 (555) 678-1234",
            imageRes = R.drawable.img_hospital_harmony,
            isEmergencyOpen = false
        ),
        HospitalItem(
            id = "hosp_3",
            name = "Metropolitan Clinic",
            category = "Mental & Behavioral Health",
            address = "Bedford Ave 104",
            distance = "3.8km",
            hours = "Open 24 Hours",
            phone = "+1 (555) 987-6543",
            imageRes = R.drawable.img_hospital_harmony,
            isEmergencyOpen = true
        )
    )

    val events = listOf(
        EventArticle(
            id = "event_1",
            tag = "News",
            title = "How Gamification is Reshaping the Insurance Industry",
            imageRes = R.drawable.img_event_gamification,
            readTime = "4 min read",
            fullSummary = "Interactive health tracking and real-time biometric rewards encourage healthier habits, reducing claims and offering pensioners lower annual premiums."
        ),
        EventArticle(
            id = "event_2",
            tag = "Event Recap",
            title = "A Glimpse Into Exclusive Insurance Summit",
            imageRes = R.drawable.img_event_gamification,
            readTime = "3 min read",
            fullSummary = "Global leaders met to establish universal digital identity cards, streamlined teleconsultations, and immediate emergency hospital admission protocols."
        ),
        EventArticle(
            id = "event_3",
            tag = "Health Tip",
            title = "Preventive Health Screening Covered in 2026",
            imageRes = R.drawable.img_event_gamification,
            readTime = "2 min read",
            fullSummary = "All policyholders are entitled to annual comprehensive blood panels, cardiology stress tests, and dental checkups at no out-of-pocket cost."
        )
    )

    val latestTeleconsult = TeleconsultAppointment(
        doctorName = "Dr. Emily Carter",
        consultationType = "Video",
        appointmentTime = "June 10, 2025 | 10:00 AM",
        doctorAvatarRes = R.drawable.img_doctor_emily,
        checklist = listOf(
            "Lab Test Booking Option Available",
            "Digital Prescription Issued (Covered by Insurance)"
        )
    )
}
