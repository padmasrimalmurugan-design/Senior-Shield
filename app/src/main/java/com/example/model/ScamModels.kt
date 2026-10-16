package com.example.model

enum class ScamRiskLevel {
    LOW_RISK,
    SUSPICIOUS,
    HIGH_RISK,
    UNKNOWN
}

data class AnalysisResult(
    val riskLevel: ScamRiskLevel,
    val headline: String,
    val explanation: String,
    val flaggedPhrases: List<String> = emptyList(),
    val recommendedSteps: List<String> = emptyList(),
    val scamCategory: String = "",
    val isAiPowered: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

data class CallerAssessment(
    val phoneNumber: String,
    val riskLevel: ScamRiskLevel,
    val selectedStatements: List<String>,
    val reasonsForConcern: List<String>,
    val whatScammerWants: String,
    val recommendedSteps: List<String>,
    val phoneReputationDisclaimer: String
)

data class ScamEducationalScenario(
    val id: Int,
    val titleEn: String,
    val titleTa: String,
    val categoryEn: String,
    val categoryTa: String,
    val sampleMessageEn: String,
    val sampleMessageTa: String,
    val warningSignsEn: List<String>,
    val warningSignsTa: List<String>,
    val whatScammerWantsEn: String,
    val whatScammerWantsTa: String,
    val howToProtectEn: List<String>,
    val howToProtectTa: List<String>
)

enum class AppLanguage {
    ENGLISH,
    TAMIL
}

enum class TextScale(val multiplier: Float, val labelEn: String, val labelTa: String) {
    NORMAL(1.0f, "Normal", "சாதாரண"),
    LARGE(1.18f, "Large", "பெரியது"),
    EXTRA_LARGE(1.35f, "Extra Large", "மிகப் பெரியது")
}

enum class NavigationDestination {
    HOME,
    CHECK_MESSAGE,
    CHECK_CALL,
    LEARN,
    HELP,
    SETTINGS
}
