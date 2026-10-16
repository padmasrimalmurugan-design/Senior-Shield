package com.example

import com.example.data.ScamDetectorEngine
import com.example.model.AppLanguage
import com.example.model.ScamRiskLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ScamDetectorEngineTest {

    @Test
    fun testPensionStoppageScamIsFlaggedHighRisk() {
        val message = "URGENT: Your monthly pension has been put on hold due to missing Life Certificate. Submit details within 24 hours at http://pension-update.in"
        val result = ScamDetectorEngine.runOnDeviceHeuristics(message, AppLanguage.ENGLISH, isAiPowered = false)
        
        assertEquals(ScamRiskLevel.HIGH_RISK, result.riskLevel)
        assertTrue(result.flaggedPhrases.isNotEmpty())
        assertTrue(result.recommendedSteps.isNotEmpty())
    }

    @Test
    fun testOtpHarvestingIsFlaggedHighRisk() {
        val message = "Dear customer, your bank card has unauthorized charges. Please tell your 6-digit OTP to bank officer to cancel."
        val result = ScamDetectorEngine.runOnDeviceHeuristics(message, AppLanguage.ENGLISH, isAiPowered = false)
        
        assertEquals(ScamRiskLevel.HIGH_RISK, result.riskLevel)
        assertTrue(result.flaggedPhrases.any { it.equals("otp", ignoreCase = true) })
    }

    @Test
    fun testBankKycBlockIsFlaggedHighRisk() {
        val message = "Dear SBI user, your account will be blocked today because PAN KYC expired. Update immediately: http://bit.ly/sbi-pan"
        val result = ScamDetectorEngine.runOnDeviceHeuristics(message, AppLanguage.ENGLISH, isAiPowered = false)
        
        assertEquals(ScamRiskLevel.HIGH_RISK, result.riskLevel)
    }

    @Test
    fun testBenignFamilyMessageIsLowRisk() {
        val message = "Good morning Grandpa, we are having family dinner together this Sunday at 7 PM."
        val result = ScamDetectorEngine.runOnDeviceHeuristics(message, AppLanguage.ENGLISH, isAiPowered = false)
        
        assertEquals(ScamRiskLevel.LOW_RISK, result.riskLevel)
        // Ensure that text safety cannot be guaranteed disclaimer is present
        assertTrue(result.explanation.contains("does not guarantee", ignoreCase = true) || 
                   result.explanation.contains("safe", ignoreCase = true))
    }

    @Test
    fun testTamilPensionScamIsFlaggedHighRisk() {
        val message = "ஓய்வூதியம் நிறுத்தம்: உங்கள் ஓய்வூதியம் நிறுத்தப்பட்டுள்ளது. உடனடியாக புதுப்பிக்க http://pension.in செல்லவும்."
        val result = ScamDetectorEngine.runOnDeviceHeuristics(message, AppLanguage.TAMIL, isAiPowered = false)
        
        assertEquals(ScamRiskLevel.HIGH_RISK, result.riskLevel)
    }

    @Test
    fun testCallerAssessmentHighRiskWhenOtpDemanded() {
        val statements = listOf("Tell me the OTP to verify your bank account.")
        val assessment = ScamDetectorEngine.assessPhoneCall("+919876543210", statements, AppLanguage.ENGLISH)
        
        assertEquals(ScamRiskLevel.HIGH_RISK, assessment.riskLevel)
        assertTrue(assessment.phoneReputationDisclaimer.isNotBlank())
    }
}
