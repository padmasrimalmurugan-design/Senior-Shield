package com.example.data

import com.example.BuildConfig
import com.example.model.AnalysisResult
import com.example.model.AppLanguage
import com.example.model.CallerAssessment
import com.example.model.ScamRiskLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object ScamDetectorEngine {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    /**
     * Primary entry point for analyzing a message.
     * Uses Gemini 3.5 Flash if API key is present and network succeeds,
     * otherwise falls back seamlessly to on-device expert heuristic analysis.
     */
    suspend fun analyzeMessage(
        rawMessage: String,
        language: AppLanguage
    ): AnalysisResult = withContext(Dispatchers.IO) {
        val trimmed = rawMessage.trim()
        if (trimmed.isEmpty() || trimmed.split(Regex("\\s+")).size < 2) {
            return@withContext getInsufficientInfoResult(language)
        }

        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        val isKeyConfigured = apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY"

        if (isKeyConfigured) {
            try {
                val aiResult = callGeminiApi(trimmed, apiKey, language)
                if (aiResult != null) {
                    return@withContext aiResult
                }
            } catch (e: Exception) {
                // Graceful fallback to heuristic engine
            }
        }

        // Run smart on-device pension security heuristics
        return@withContext runOnDeviceHeuristics(trimmed, language, isAiPowered = false)
    }

    /**
     * Assesses a phone call scenario based on caller number and statements made.
     */
    fun assessPhoneCall(
        phoneNumber: String,
        selectedStatements: List<String>,
        language: AppLanguage
    ): CallerAssessment {
        val reasons = mutableListOf<String>()
        val whatWants = StringBuilder()
        val steps = mutableListOf<String>()

        var score = 0

        // Statement checks
        for (statement in selectedStatements) {
            val lower = statement.lowercase()
            when {
                lower.contains("pension") && (lower.contains("stop") || lower.contains("நிறுத்த")) -> {
                    score += 3
                    reasons.add(if (language == AppLanguage.ENGLISH) 
                        "Threatened to stop or hold your pension. Pension offices never make threatening calls." 
                        else "ஓய்வூதியம் நிறுத்தப்படும் என மிரட்டுவது. உண்மையான அலுவலகம் தொலைபேசியில் மிரட்டாது.")
                }
                lower.contains("otp") || lower.contains("ஓடிபி") -> {
                    score += 4
                    reasons.add(if (language == AppLanguage.ENGLISH) 
                        "Requested your secret OTP. Bank officials are strictly forbidden from asking for OTPs." 
                        else "உங்கள் ரகசிய OTP-ஐ கேட்டனர். வங்கி அதிகாரிகள் ஒருபோதும் OTP கேட்க மாட்டார்கள்.")
                }
                lower.contains("fee") || lower.contains("tax") || lower.contains("கட்டணம்") -> {
                    score += 3
                    reasons.add(if (language == AppLanguage.ENGLISH) 
                        "Demanded fee or advance money to release arrears. Real pensions require zero fees." 
                        else "நிலுவைத் தொகையை பெற முன் கட்டணம் கேட்டனர். உண்மையான ஓய்வூதியத்திற்கு கட்டணம் தேவையில்லை.")
                }
                lower.contains("kyc") || lower.contains("pan") || lower.contains("கேஒய்சி") -> {
                    score += 3
                    reasons.add(if (language == AppLanguage.ENGLISH) 
                        "Urgent KYC verification request over call. Banks never update KYC via phone calls." 
                        else "தொலைபேசி அழைப்பு மூலம் அவசர KYC கேட்டனர். வங்கிகள் போனில் KYC செய்வதில்லை.")
                }
                lower.contains("anydesk") || lower.contains("teamviewer") || lower.contains("செயலி") -> {
                    score += 4
                    reasons.add(if (language == AppLanguage.ENGLISH) 
                        "Asked to install remote screen-sharing software (AnyDesk/TeamViewer). This allows phone hijacking." 
                        else "AnyDesk அல்லது TeamViewer செயலியை நிறுவச் சொன்னார்கள். இது உங்கள் போனை திருட வழிவகுக்கும்.")
                }
                lower.contains("disconnect") || lower.contains("மின்சாரம்") -> {
                    score += 3
                    reasons.add(if (language == AppLanguage.ENGLISH) 
                        "Threat of immediate electricity or utility disconnection tonight." 
                        else "இன்றிரவுக்குள் மின்சாரம் துண்டிக்கப்படும் என மிரட்டுவது.")
                }
                lower.contains("lottery") || lower.contains("prize") || lower.contains("பரிசு") -> {
                    score += 3
                    reasons.add(if (language == AppLanguage.ENGLISH) 
                        "Claimed you won a government lottery or prize bonus requiring charges." 
                        else "லாட்டரி அல்லது பரிசு கிடைத்துள்ளது என ஆசை காட்டுவது.")
                }
            }
        }

        val risk = when {
            score >= 3 -> ScamRiskLevel.HIGH_RISK
            score in 1..2 -> ScamRiskLevel.SUSPICIOUS
            else -> ScamRiskLevel.UNKNOWN
        }

        if (language == AppLanguage.ENGLISH) {
            whatWants.append(when (risk) {
                ScamRiskLevel.HIGH_RISK -> "The caller is attempting to create panic, obtain your banking credentials or OTP, or take control of your device to siphon your pension savings."
                ScamRiskLevel.SUSPICIOUS -> "The caller is asking for sensitive details or attempting to pressure you into taking unverified actions."
                else -> "Caller intent is unclear from the selected statements, but always exercise caution."
            })
            steps.add("Hang up immediately. Do NOT engage or argue with the caller.")
            steps.add("Do NOT share any OTP, PIN, account number, or debit card CVV.")
            steps.add("Do NOT install any apps (AnyDesk, TeamViewer) or click links sent via SMS.")
            steps.add("Verify directly with your bank manager using the number printed on your passbook.")
        } else {
            whatWants.append(when (risk) {
                ScamRiskLevel.HIGH_RISK -> "அழைப்பாளர் உங்கள் ஓய்வூதிய பணத்தை திருட அல்லது உங்கள் போனை தனது கட்டுப்பாட்டில் கொண்டுவர முயற்சிக்கிறார்."
                ScamRiskLevel.SUSPICIOUS -> "அழைப்பாளர் உங்களை பதற்றமடைய செய்து விவரங்களை பெற முயற்சிக்கிறார்."
                else -> "அழைப்பாளரின் நோக்கம் தெளிவாக இல்லை. எச்சரிக்கையுடன் இருக்கவும்."
            })
            steps.add("அழைப்பை உடனடியாக துண்டிக்கவும். அவர்களுடன் பேச வேண்டாம்.")
            steps.add("எந்த ஒரு சூழ்நிலையிலும் OTP அல்லது கடவுச்சொல்லை சொல்லாதீர்கள்.")
            steps.add("போனில் எந்த புதிய செயலிகளையும் நிறுவ வேண்டாம்.")
            steps.add("பாஸ்புக்கில் உள்ள வங்கி கிளை எண்ணிற்கு அழைத்து நேரில் விசாரிக்கவும்.")
        }

        val disclaimer = if (language == AppLanguage.ENGLISH) {
            "Important Notice: Phone number checks alone cannot reliably establish if a caller is genuine or fraudulent because scammers frequently spoof numbers. Evaluation is based on statements and warning signs."
        } else {
            "முக்கிய அறிவிப்பு: மோசடி செய்பவர்கள் போலி எண்களைப் பயன்படுத்தக்கூடும் என்பதால், தொலைபேசி எண்ணை மட்டும் வைத்து ஒரு அழைப்பை முற்றிலும் சரிபார்க்க முடியாது. அவர்களின் பேச்சின் அடிப்படையிலேயே இந்த மதிப்பீடு செய்யப்படுகிறது."
        }

        return CallerAssessment(
            phoneNumber = phoneNumber.ifBlank { "Unspecified" },
            riskLevel = risk,
            selectedStatements = selectedStatements,
            reasonsForConcern = reasons.ifEmpty { 
                listOf(if (language == AppLanguage.ENGLISH) 
                    "No high-risk statements were marked, but remember genuine banks never ask for personal PINs over phone." 
                    else "ஆபத்தான வாக்கியங்கள் தேர்ந்தெடுக்கப்படவில்லை, ஆயினும் வங்கிகள் ஒருபோதும் போனில் PIN கேட்பதில்லை.") 
            },
            whatScammerWants = whatWants.toString(),
            recommendedSteps = steps,
            phoneReputationDisclaimer = disclaimer
        )
    }

    /**
     * Smart on-device heuristic scam detection engine.
     * Evaluates keywords, regex patterns, urgency, and threats.
     */
    fun runOnDeviceHeuristics(
        message: String,
        language: AppLanguage,
        isAiPowered: Boolean
    ): AnalysisResult {
        val lower = message.lowercase()
        val flagged = mutableListOf<String>()
        val reasons = mutableListOf<String>()
        val steps = mutableListOf<String>()

        var highRiskPoints = 0
        var suspiciousPoints = 0

        // 1. OTP / Password / PIN demands
        val otpPattern = Pattern.compile("\\b(otp|pin|cvv|password|passcode|one[- ]time password|கடவுச்சொல்|ஓடிபி)\\b", Pattern.CASE_INSENSITIVE)
        val otpMatcher = otpPattern.matcher(lower)
        if (otpMatcher.find()) {
            highRiskPoints += 5
            flagged.add(otpMatcher.group())
            reasons.add(if (language == AppLanguage.ENGLISH) 
                "Requests secret verification credentials (OTP / PIN). Real officials never ask for this." 
                else "ரகசிய OTP அல்லது PIN கேட்கிறது. உண்மையான அதிகாரிகள் இதனை ஒருபோதும் கேட்க மாட்டார்கள்.")
        }

        // 2. Pension freeze or life certificate stoppage
        val pensionPattern = Pattern.compile("(pension\\s*(stopped|hold|suspend|blocked|cancelled)|life\\s*certificate|jeevan\\s*pramaan|ஓய்வூதியம்\\s*(நிறுத்த|முடக்க)|ஆயுள்\\s*சான்றிதழ்)", Pattern.CASE_INSENSITIVE)
        val pensionMatcher = pensionPattern.matcher(lower)
        if (pensionMatcher.find()) {
            highRiskPoints += 4
            flagged.add(pensionMatcher.group())
            reasons.add(if (language == AppLanguage.ENGLISH) 
                "Threatens that your monthly pension has been suspended or held." 
                else "உங்கள் மாதாந்திர ஓய்வூதியம் நிறுத்தப்பட்டுள்ளது என பயமுறுத்துகிறது.")
        }

        // 3. Bank KYC and Account block threats
        val kycPattern = Pattern.compile("(kyc\\s*(expired|pending|update|block)|pan\\s*(card|link|update)|debit\\s*card\\s*blocked|account\\s*blocked|வங்கி\\s*கணக்கு\\s*முடக்க|கேஒய்சி)", Pattern.CASE_INSENSITIVE)
        val kycMatcher = kycPattern.matcher(lower)
        if (kycMatcher.find()) {
            highRiskPoints += 4
            flagged.add(kycMatcher.group())
            reasons.add(if (language == AppLanguage.ENGLISH) 
                "Claims bank account or debit card will be blocked due to pending KYC." 
                else "KYC புதுப்பிக்காவிட்டால் வங்கி கணக்கு முடக்கப்படும் என அச்சுறுத்துகிறது.")
        }

        // 4. Remote access software
        val remotePattern = Pattern.compile("\\b(anydesk|teamviewer|rustdesk|quicksupport|screen\\s*share|apk)\\b", Pattern.CASE_INSENSITIVE)
        val remoteMatcher = remotePattern.matcher(lower)
        if (remoteMatcher.find()) {
            highRiskPoints += 5
            flagged.add(remoteMatcher.group())
            reasons.add(if (language == AppLanguage.ENGLISH) 
                "Mentions remote screen control software or raw APK files that can compromise your phone." 
                else "போனை தொலைவிலிருந்து இயக்கும் AnyDesk அல்லது ஆபத்தான APK செயலிகளை குறிப்பிடுகிறது.")
        }

        // 5. Shortened or suspicious links
        val linkPattern = Pattern.compile("(http[s]?://|bit\\.ly|tinyurl|t\\.co|is\\.gd|\\.xyz|\\.club|\\.top|\\.click)", Pattern.CASE_INSENSITIVE)
        val linkMatcher = linkPattern.matcher(lower)
        if (linkMatcher.find()) {
            suspiciousPoints += 3
            flagged.add(linkMatcher.group())
            reasons.add(if (language == AppLanguage.ENGLISH) 
                "Contains an external link or shortened URL commonly used to lure victims to fake web pages." 
                else "போலி இணையதளங்களுக்கு அழைத்துச் செல்லும் சந்தேகத்திற்குரிய இணைப்பு (Link) உள்ளது.")
        }

        // 6. Urgency / Threat triggers
        val urgencyPattern = Pattern.compile("(immediately|within\\s*24\\s*hours|urgent|today\\s*itself|police\\s*action|legal\\s*action|உடனடியாக|24\\s*மணி)", Pattern.CASE_INSENSITIVE)
        val urgencyMatcher = urgencyPattern.matcher(lower)
        if (urgencyMatcher.find()) {
            suspiciousPoints += 2
            flagged.add(urgencyMatcher.group())
            reasons.add(if (language == AppLanguage.ENGLISH) 
                "Uses aggressive urgency tactics to panic you into acting without thinking." 
                else "பதற்றத்தை உருவாக்கி யோசிக்காமல் முடிவெடுக்க வைக்கும் அவசர வார்த்தைகள் உள்ளன.")
        }

        // 7. Lottery / Prize / Refund fees
        val lotteryPattern = Pattern.compile("(won\\s*lottery|prize|refund\\s*arrear|claim\\s*bonus|பரிசு|லாட்டரி)", Pattern.CASE_INSENSITIVE)
        val lotteryMatcher = lotteryPattern.matcher(lower)
        if (lotteryMatcher.find()) {
            highRiskPoints += 3
            flagged.add(lotteryMatcher.group())
            reasons.add(if (language == AppLanguage.ENGLISH) 
                "Prompts unexpected lottery winnings, bonus, or refund requiring fees." 
                else "எதிர்பாராத பரிசு அல்லது நிலுவை தொகை தருவதாக ஆசை காட்டுகிறது.")
        }

        val riskLevel: ScamRiskLevel
        val headline: String
        val explanation: String
        val category: String

        if (highRiskPoints >= 4 || (highRiskPoints >= 2 && suspiciousPoints >= 3)) {
            riskLevel = ScamRiskLevel.HIGH_RISK
            category = "Financial & Pension Fraud Attempt"
            if (language == AppLanguage.ENGLISH) {
                headline = "Warning! This message exhibits severe scam patterns."
                explanation = "This message strongly matches typical fraudulent techniques used by criminals targeting pensioners. They are attempting to steal your credentials or money."
                steps.add("Do NOT click any links in this message.")
                steps.add("Do NOT call back the phone number that sent it.")
                steps.add("Never share your OTP, PIN, password, or ATM card numbers.")
                steps.add("If you are worried about your pension, visit your bank branch directly.")
            } else {
                headline = "எச்சரிக்கை! இந்த செய்தி தீவிர மோசடி அறிகுறிகளை கொண்டுள்ளது."
                explanation = "ஓய்வூதியதாரர்களை குறிவைத்து நடத்தப்படும் மோசடிகளுடன் இது முழுமையாக ஒத்துப்போகிறது. உங்கள் சேமிப்பை பறிக்க முயற்சிக்கிறார்கள்."
                steps.add("இந்த செய்தியில் உள்ள எந்த இணைப்பையும் தொடாதீர்கள்.")
                steps.add("செய்தி அனுப்பிய எண்ணிற்கு திரும்ப அழைக்க வேண்டாம்.")
                steps.add("OTP, PIN அல்லது கார்டு எண்களை பகிராதீர்கள்.")
                steps.add("ஓய்வூதியம் குறித்து சந்தேகம் இருந்தால் வங்கிக்கு நேரில் செல்லவும்.")
            }
        } else if (suspiciousPoints >= 2 || highRiskPoints in 1..3) {
            riskLevel = ScamRiskLevel.SUSPICIOUS
            category = "Suspicious Communication"
            if (language == AppLanguage.ENGLISH) {
                headline = "Caution: This message contains red flags."
                explanation = "While it may not contain all classic scam markers, the tone, urgency, or links are suspicious. Treat it with extreme care."
                steps.add("Do not forward this message or reply to it.")
                steps.add("Contact your bank using the phone number printed on your passbook.")
                steps.add("Ask a trusted family member or bank official to inspect it.")
            } else {
                headline = "கவனம்: இந்த செய்தியில் சந்தேகத்திற்குரிய அம்சங்கள் உள்ளன."
                explanation = "இதில் உள்ள அவசரம் அல்லது இணைப்புகள் சந்தேகத்திற்குரியவை. மிகுந்த கவனத்துடன் அணுகவும்."
                steps.add("இந்த செய்தியை பகிரவோ அல்லது பதிலளிக்கவோ வேண்டாம்.")
                steps.add("பாஸ்புக்கில் உள்ள வங்கி எண்ணிற்கு அழைத்து சரிபார்க்கவும்.")
                steps.add("நம்பகமான குடும்பத்தினர் அல்லது அதிகாரியிடம் ஆலோசிக்கவும்.")
            }
        } else {
            riskLevel = ScamRiskLevel.LOW_RISK
            category = "General Message"
            if (language == AppLanguage.ENGLISH) {
                headline = "No obvious scam signs detected."
                explanation = "No obvious scam signs were detected in this text. However, this does not guarantee that the message is safe. Scammers constantly evolve new tricks, so remain vigilant."
                steps.add("Never share bank passwords or OTPs even if a message seems friendly.")
                steps.add("Do not send money to strangers or unverified accounts.")
            } else {
                headline = "வெளிப்படையான மோசடி அறிகுறிகள் கண்டறியப்படவில்லை."
                explanation = "இதில் வெளிப்படையான மோசடி வார்த்தைகள் இல்லை. எனினும் இது முற்றிலும் பாதுகாப்பானது என்பதற்கு உத்தரவாதமில்லை."
                steps.add("செய்தி நல்லவிதமாக தோன்றினாலும் வங்கி கடவுச்சொற்களை பகிராதீர்கள்.")
                steps.add("தெரியாத நபர்களுக்கு பணம் அனுப்பாதீர்கள்.")
            }
        }

        return AnalysisResult(
            riskLevel = riskLevel,
            headline = headline,
            explanation = explanation,
            flaggedPhrases = flagged.distinct(),
            recommendedSteps = steps,
            scamCategory = category,
            isAiPowered = isAiPowered
        )
    }

    private fun getInsufficientInfoResult(language: AppLanguage): AnalysisResult {
        return if (language == AppLanguage.ENGLISH) {
            AnalysisResult(
                riskLevel = ScamRiskLevel.UNKNOWN,
                headline = "Unable to determine — please verify independently.",
                explanation = "The provided message is too short or lacks sufficient context to evaluate safely. Please paste the complete message text.",
                flaggedPhrases = emptyList(),
                recommendedSteps = listOf(
                    "Paste the complete SMS, WhatsApp message, or notice.",
                    "Include the full sentence including any phone numbers or links mentioned."
                ),
                scamCategory = "Insufficient Text",
                isAiPowered = false
            )
        } else {
            AnalysisResult(
                riskLevel = ScamRiskLevel.UNKNOWN,
                headline = "தீர்மானிக்க போதுமான தகவல் இல்லை — நேரில் சரிபார்க்கவும்.",
                explanation = "வழங்கப்பட்ட செய்தி மிகவும் சிறியதாக உள்ளது அல்லது மதிப்பீடு செய்ய போதிய தகவல்கள் இல்லை. முழு செய்தியையும் உள்ளிடவும்.",
                flaggedPhrases = emptyList(),
                recommendedSteps = listOf(
                    "முழுமையான SMS அல்லது வாட்ஸ்அப் செய்தியை உள்ளிடவும்.",
                    "அதில் உள்ள எண்கள் அல்லது இணைப்புகளுடன் முழுமையாக நகலெடுக்கவும்."
                ),
                scamCategory = "போதுமான தகவல் இல்லை",
                isAiPowered = false
            )
        }
    }

    /**
     * Direct REST call to Gemini 3.5 Flash for deep contextual analysis.
     */
    private suspend fun callGeminiApi(
        message: String,
        apiKey: String,
        language: AppLanguage
    ): AnalysisResult? {
        val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val promptLanguageInstruction = if (language == AppLanguage.TAMIL) {
            "Respond in clear, simple Tamil language tailored for an elderly pensioner."
        } else {
            "Respond in simple, clear, reassuring English with no technical jargon, tailored for an elderly pensioner."
        }

        val systemPrompt = """
            You are Senior Shield, an AI scam detector protecting elderly pensioners in India from financial fraud, fake pension notices, fake KYC alerts, and bank theft.
            Analyze the following submitted message and classify risk into one of:
            - "LOW_RISK" (No obvious scam signs detected, but explicitly state that text safety cannot be guaranteed)
            - "SUSPICIOUS" (Contains questionable claims, urgency, unverified links)
            - "HIGH_RISK" (Definite signs of fraud: requests OTP/PIN/passwords, pension suspension threats, fake bank KYC, remote access APKs, fee for arrears, lottery)
            
            $promptLanguageInstruction
            
            Return ONLY a valid JSON object matching this exact structure:
            {
               "risk_level": "LOW_RISK" | "SUSPICIOUS" | "HIGH_RISK",
               "headline": "Short plain summary",
               "explanation": "2-3 simple sentences explaining why this is or isn't a scam without using technical jargon",
               "flagged_phrases": ["phrase1", "phrase2"],
               "recommended_steps": ["step1", "step2", "step3"],
               "scam_category": "Short Category Name"
            }
        """.trimIndent()

        val jsonBody = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", "System: $systemPrompt\n\nUser Message to Analyze:\n$message")
                        })
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("responseMimeType", "application/json")
                put("temperature", 0.1)
            })
        }

        val request = Request.Builder()
            .url(endpoint)
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            return null
        }

        val responseBody = response.body?.string() ?: return null
        val root = JSONObject(responseBody)
        val candidates = root.optJSONArray("candidates") ?: return null
        val firstCandidate = candidates.optJSONObject(0) ?: return null
        val content = firstCandidate.optJSONObject("content") ?: return null
        val parts = content.optJSONArray("parts") ?: return null
        val textPart = parts.optJSONObject(0)?.optString("text") ?: return null

        val structured = JSONObject(textPart)
        val riskStr = structured.optString("risk_level", "SUSPICIOUS").uppercase()
        val riskLevel = when (riskStr) {
            "LOW_RISK" -> ScamRiskLevel.LOW_RISK
            "HIGH_RISK" -> ScamRiskLevel.HIGH_RISK
            "SUSPICIOUS" -> ScamRiskLevel.SUSPICIOUS
            else -> ScamRiskLevel.UNKNOWN
        }

        val headline = structured.optString("headline", "")
        val explanation = structured.optString("explanation", "")
        val category = structured.optString("scam_category", "AI Analysis")

        val flaggedList = mutableListOf<String>()
        val flaggedJson = structured.optJSONArray("flagged_phrases")
        if (flaggedJson != null) {
            for (i in 0 until flaggedJson.length()) {
                flaggedList.add(flaggedJson.optString(i))
            }
        }

        val stepsList = mutableListOf<String>()
        val stepsJson = structured.optJSONArray("recommended_steps")
        if (stepsJson != null) {
            for (i in 0 until stepsJson.length()) {
                stepsList.add(stepsJson.optString(i))
            }
        }

        return AnalysisResult(
            riskLevel = riskLevel,
            headline = headline.ifBlank { Localization.riskTitle(riskLevel, language) },
            explanation = explanation.ifBlank { Localization.riskDescription(riskLevel, language) },
            flaggedPhrases = flaggedList,
            recommendedSteps = stepsList.ifEmpty { 
                listOf(
                    if (language == AppLanguage.ENGLISH) "Never share your OTP or PIN with anyone." else "OTP அல்லது PIN-ஐ எவரிடமும் பகிராதீர்கள்.",
                    if (language == AppLanguage.ENGLISH) "Verify directly with your bank branch." else "உங்கள் வங்கி கிளையில் நேரில் சரிபார்க்கவும்."
                )
            },
            scamCategory = category,
            isAiPowered = true
        )
    }
}
