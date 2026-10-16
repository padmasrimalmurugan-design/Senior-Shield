package com.example.data

import com.example.model.AppLanguage
import com.example.model.ScamRiskLevel

object Localization {

    fun appName(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Senior Shield"
        AppLanguage.TAMIL -> "சீனியர் ஷீல்ட் (Senior Shield)"
    }

    fun appTagline(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Your Safety, Our Priority"
        AppLanguage.TAMIL -> "உங்கள் பாதுகாப்பு, எங்கள் கடமை"
    }

    fun goldenRuleTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Golden Safety Rule"
        AppLanguage.TAMIL -> "முக்கிய பாதுகாப்பு விதி"
    }

    fun goldenRuleText(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Never share your OTP, PIN, password, or bank details with anyone — even if they claim to be from the Pension Office, Bank, or Government."
        AppLanguage.TAMIL -> "உங்கள் OTP, PIN, கடவுச்சொல் அல்லது வங்கி விவரங்களை ஒருபோதும் யாரிடமும் பகிர வேண்டாம் — அவர்கள் வங்கி அல்லது ஓய்வூதியத் துறை அதிகாரி என்று கூறினாலும் கூட."
    }

    fun tipOfTheDayTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Safety Tip of the Day"
        AppLanguage.TAMIL -> "இன்றைய பாதுகாப்பு குறிப்பு"
    }

    fun tipOfTheDay(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Real pension offices and banks will NEVER call or message you demanding money or OTP to release your monthly pension or verify your Life Certificate."
        AppLanguage.TAMIL -> "உங்கள் மாதாந்திர ஓய்வூதியத்தை வழங்கவோ அல்லது ஆயுள் சான்றிதழை சரிபார்க்கவோ உண்மையான ஓய்வூதிய அலுவலகமோ அல்லது வங்கியோ ஒருபோதும் தொலைபேசியில் OTP அல்லது பணத்தை கேட்காது."
    }

    fun riskTitle(level: ScamRiskLevel, lang: AppLanguage): String = when (level) {
        ScamRiskLevel.LOW_RISK -> when (lang) {
            AppLanguage.ENGLISH -> "LOW RISK"
            AppLanguage.TAMIL -> "குறைந்த ஆபத்து (LOW RISK)"
        }
        ScamRiskLevel.SUSPICIOUS -> when (lang) {
            AppLanguage.ENGLISH -> "SUSPICIOUS"
            AppLanguage.TAMIL -> "சந்தேகத்திற்குரியது (SUSPICIOUS)"
        }
        ScamRiskLevel.HIGH_RISK -> when (lang) {
            AppLanguage.ENGLISH -> "HIGH RISK"
            AppLanguage.TAMIL -> "அதிக ஆபத்து (HIGH RISK)"
        }
        ScamRiskLevel.UNKNOWN -> when (lang) {
            AppLanguage.ENGLISH -> "UNABLE TO DETERMINE"
            AppLanguage.TAMIL -> "தீர்மானிக்க முடியவில்லை"
        }
    }

    fun riskDescription(level: ScamRiskLevel, lang: AppLanguage): String = when (level) {
        ScamRiskLevel.LOW_RISK -> when (lang) {
            AppLanguage.ENGLISH -> "No obvious scam signs were detected. However, this does not guarantee that the message is safe. Never share private details."
            AppLanguage.TAMIL -> "வெளிப்படையான மோசடி அறிகுறிகள் கண்டறியப்படவில்லை. இருப்பினும், இந்த செய்தி முற்றிலும் பாதுகாப்பானது என்பதற்கு இது உத்தரவாதமல்ல."
        }
        ScamRiskLevel.SUSPICIOUS -> when (lang) {
            AppLanguage.ENGLISH -> "This message contains suspicious signs. Please verify it through official bank or pension office channels before taking action."
            AppLanguage.TAMIL -> "இந்த செய்தியில் சந்தேகத்திற்குரிய அம்சங்கள் உள்ளன. எந்த நடவடிக்கையும் எடுப்பதற்கு முன் உங்கள் வங்கியிலோ அல்லது அலுவலகத்திலோ நேரில் உறுதிப்படுத்தவும்."
        }
        ScamRiskLevel.HIGH_RISK -> when (lang) {
            AppLanguage.ENGLISH -> "Warning! This message may be a scam. Do not click links, share personal details, or send money under any circumstances."
            AppLanguage.TAMIL -> "எச்சரிக்கை! இந்த செய்தி மோசடியாக இருக்கலாம். எந்த இணைப்பையும் (Link) கிளிக் செய்யாதீர்கள், பணம் அனுப்பாதீர்கள், விவரங்களை பகிராதீர்கள்."
        }
        ScamRiskLevel.UNKNOWN -> when (lang) {
            AppLanguage.ENGLISH -> "Unable to determine — please verify independently with your local bank or pension authority."
            AppLanguage.TAMIL -> "தீர்மானிக்க போதுமான தகவல் இல்லை — தயவுசெய்து உங்கள் வங்கி அல்லது ஓய்வூதிய அலுவலகத்தில் நேரில் சரிபார்க்கவும்."
        }
    }

    // Common Caller statements
    val callerStatementsEn = listOf(
        "Your pension will be stopped today.",
        "Tell me the OTP to verify your bank account.",
        "Pay a small fee or tax to release your pension arrears.",
        "Your bank account needs immediate KYC verification or PAN linking.",
        "Install AnyDesk, TeamViewer, or QuickSupport app on your phone.",
        "Your electricity or water connection will be disconnected tonight.",
        "You have won a government lottery, prize, or welfare bonus."
    )

    val callerStatementsTa = listOf(
        "உங்கள் ஓய்வூதியம் இன்று நிறுத்தப்படும் என்று கூறினார்களா?",
        "வங்கி கணக்கை சரிபார்க்க OTP சொல்லுங்கள் என்று கேட்டார்களா?",
        "ஓய்வூதிய நிலுவைத் தொகையைப் பெற கட்டணம் செலுத்தச் சொன்னார்களா?",
        "உங்கள் வங்கி கணக்கிற்கு உடனே KYC அல்லது PAN புதுப்பிக்க வேண்டும் என்றார்களா?",
        "உங்கள் மொபைலில் AnyDesk அல்லது TeamViewer செயலியை நிறுவச் சொன்னார்களா?",
        "இன்றிரவுக்குள் மின்சாரம் அல்லது கட்டணம் செலுத்த வேண்டும் என்று மிரட்டினார்களா?",
        "அரசு லாட்டரி அல்லது பரிசு கிடைத்துள்ளது என்று கூறினார்களா?"
    )
}
