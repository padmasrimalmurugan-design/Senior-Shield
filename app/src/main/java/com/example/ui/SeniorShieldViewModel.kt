package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.HealthInsuranceRepository
import com.example.data.ScamDetectorEngine
import com.example.model.AnalysisResult
import com.example.model.AppLanguage
import com.example.model.AppTab
import com.example.model.CallerAssessment
import com.example.model.ClaimItem
import com.example.model.EventArticle
import com.example.model.HospitalItem
import com.example.data.SeniorShieldFirestoreRepository
import com.example.model.MemberProfile
import com.example.model.NavigationDestination
import com.example.model.ScamCheckRecord
import com.example.model.ScamEducationalScenario
import com.example.model.TextScale
import com.example.model.UserProfile
import com.example.speech.SpeechAssistant
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SeniorShieldViewModel(application: Application) : AndroidViewModel(application) {

    val speechAssistant = SpeechAssistant(application)
    private val firestoreRepository by lazy { SeniorShieldFirestoreRepository(application) }

    // Cloud Profile State from Firestore
    private val _cloudProfile = MutableStateFlow<UserProfile?>(null)
    val cloudProfile: StateFlow<UserProfile?> = _cloudProfile.asStateFlow()

    // Cloud Scam Check History
    private val _pastCheckLogs = MutableStateFlow<List<ScamCheckRecord>>(emptyList())
    val pastCheckLogs: StateFlow<List<ScamCheckRecord>> = _pastCheckLogs.asStateFlow()

    // Current Tab in Bottom Navigation (matching Home, Claims, Rewards, More)
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    // Subscreen state for Hospitals Directory & Detail (center phone in mockup)
    private val _isHospitalsScreenOpen = MutableStateFlow(false)
    val isHospitalsScreenOpen: StateFlow<Boolean> = _isHospitalsScreenOpen.asStateFlow()

    private val _selectedHospital = MutableStateFlow<HospitalItem?>(null)
    val selectedHospital: StateFlow<HospitalItem?> = _selectedHospital.asStateFlow()

    private val _isHospitalMapView = MutableStateFlow(false)
    val isHospitalMapView: StateFlow<Boolean> = _isHospitalMapView.asStateFlow()

    // Subscreen state for Scam Detector Shield
    private val _isScamShieldOpen = MutableStateFlow(false)
    val isScamShieldOpen: StateFlow<Boolean> = _isScamShieldOpen.asStateFlow()

    // Subscreen for Event Article Detail
    private val _selectedEvent = MutableStateFlow<EventArticle?>(null)
    val selectedEvent: StateFlow<EventArticle?> = _selectedEvent.asStateFlow()

    // Language state
    private val _language = MutableStateFlow(AppLanguage.ENGLISH)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    // Accessible Text Scale
    private val _textScale = MutableStateFlow(TextScale.NORMAL)
    val textScale: StateFlow<TextScale> = _textScale.asStateFlow()

    // User Profile & Data
    val userProfile = HealthInsuranceRepository.userProfile
    val premiumSummary = HealthInsuranceRepository.premiumSummary
    val claimsList = HealthInsuranceRepository.claims
    val hospitalsList = HealthInsuranceRepository.hospitals
    val eventsList = HealthInsuranceRepository.events
    val latestTeleconsult = HealthInsuranceRepository.latestTeleconsult

    // Scam Detector Screen States
    private val _scamDestination = MutableStateFlow(NavigationDestination.HOME)
    val scamDestination: StateFlow<NavigationDestination> = _scamDestination.asStateFlow()

    private val _messageInput = MutableStateFlow("")
    val messageInput: StateFlow<String> = _messageInput.asStateFlow()

    private val _isAnalyzingMessage = MutableStateFlow(false)
    val isAnalyzingMessage: StateFlow<Boolean> = _isAnalyzingMessage.asStateFlow()

    private val _analysisResult = MutableStateFlow<AnalysisResult?>(null)
    val analysisResult: StateFlow<AnalysisResult?> = _analysisResult.asStateFlow()

    private val _analysisError = MutableStateFlow<String?>(null)
    val analysisError: StateFlow<String?> = _analysisError.asStateFlow()

    private val _phoneNumberInput = MutableStateFlow("")
    val phoneNumberInput: StateFlow<String> = _phoneNumberInput.asStateFlow()

    private val _selectedStatements = MutableStateFlow<Set<String>>(emptySet())
    val selectedStatements: StateFlow<Set<String>> = _selectedStatements.asStateFlow()

    private val _callerAssessment = MutableStateFlow<CallerAssessment?>(null)
    val callerAssessment: StateFlow<CallerAssessment?> = _callerAssessment.asStateFlow()

    private val _activeScenario = MutableStateFlow<ScamEducationalScenario?>(null)
    val activeScenario: StateFlow<ScamEducationalScenario?> = _activeScenario.asStateFlow()

    fun switchTab(tab: AppTab) {
        speechAssistant.stop()
        _currentTab.value = tab
        _isHospitalsScreenOpen.value = false
        _isScamShieldOpen.value = false
    }

    fun openHospitalsScreen(hospitalToSelect: HospitalItem? = null) {
        _selectedHospital.value = hospitalToSelect ?: HealthInsuranceRepository.hospitals.firstOrNull()
        _isHospitalsScreenOpen.value = true
    }

    fun closeHospitalsScreen() {
        _isHospitalsScreenOpen.value = false
        _selectedHospital.value = null
    }

    fun selectHospital(hospital: HospitalItem) {
        _selectedHospital.value = hospital
    }

    fun toggleHospitalMapView() {
        _isHospitalMapView.value = !_isHospitalMapView.value
    }

    fun openScamShield(initialDestination: NavigationDestination = NavigationDestination.HOME) {
        _scamDestination.value = initialDestination
        _isScamShieldOpen.value = true
    }

    fun closeScamShield() {
        _isScamShieldOpen.value = false
        speechAssistant.stop()
    }

    fun navigateScamTo(destination: NavigationDestination) {
        speechAssistant.stop()
        _scamDestination.value = destination
    }

    fun navigateTo(destination: NavigationDestination) = navigateScamTo(destination)

    fun openEvent(article: EventArticle) {
        _selectedEvent.value = article
    }

    fun closeEvent() {
        _selectedEvent.value = null
    }

    fun setLanguage(lang: AppLanguage) {
        _language.value = lang
        speechAssistant.stop()
    }

    fun setTextScale(scale: TextScale) {
        _textScale.value = scale
    }

    fun setMessageInput(text: String) {
        _messageInput.value = text
        _analysisError.value = null
    }

    fun clearMessageInput() {
        _messageInput.value = ""
        _analysisResult.value = null
        _analysisError.value = null
        speechAssistant.stop()
    }

    fun initUserSession() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModelScope.launch {
                try {
                    firestoreRepository.observeProfile().collect { profile ->
                        _cloudProfile.value = profile
                        if (profile != null) {
                            if (profile.language == "TAMIL") {
                                _language.value = AppLanguage.TAMIL
                            } else if (profile.language == "ENGLISH") {
                                _language.value = AppLanguage.ENGLISH
                            }
                            if (profile.largeFontEnabled) {
                                _textScale.value = TextScale.LARGE
                            }
                        }
                    }
                } catch (ignored: Exception) {}
            }

            viewModelScope.launch {
                try {
                    firestoreRepository.observeCheckLogs().collect { logs ->
                        _pastCheckLogs.value = logs
                    }
                } catch (ignored: Exception) {}
            }
        }
    }

    fun saveEmergencyContact(name: String, phone: String) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        viewModelScope.launch {
            try {
                firestoreRepository.saveProfile(
                    displayName = user.displayName ?: "Pensioner",
                    language = _language.value.name,
                    emergencyContactName = name,
                    emergencyContactPhone = phone,
                    largeFontEnabled = _textScale.value != TextScale.NORMAL,
                    autoTtsEnabled = true
                )
            } catch (ignored: Exception) {}
        }
    }

    fun analyzeMessage(textToAnalyze: String = _messageInput.value) {
        val trimmed = textToAnalyze.trim()
        if (trimmed.isEmpty()) {
            _analysisError.value = if (_language.value == AppLanguage.ENGLISH) 
                "Please enter or paste a message to analyze." 
                else "தயவுசெய்து பகுப்பாய்வு செய்ய ஒரு செய்தியை உள்ளிடவும்."
            return
        }

        viewModelScope.launch {
            _isAnalyzingMessage.value = true
            _analysisError.value = null
            speechAssistant.stop()
            try {
                val result = ScamDetectorEngine.analyzeMessage(trimmed, _language.value)
                _analysisResult.value = result

                if (FirebaseAuth.getInstance().currentUser != null) {
                    launch {
                        try {
                            firestoreRepository.recordCheckLog(
                                type = "message",
                                content = trimmed,
                                riskLevel = result.riskLevel.name,
                                headline = result.headline,
                                explanation = result.explanation,
                                flaggedPhrases = result.flaggedPhrases
                            )
                        } catch (ignored: Exception) {}
                    }
                }
            } catch (e: Exception) {
                _analysisError.value = "Fallback to on-device heuristics"
                _analysisResult.value = ScamDetectorEngine.runOnDeviceHeuristics(trimmed, _language.value, isAiPowered = false)
            } finally {
                _isAnalyzingMessage.value = false
            }
        }
    }

    fun setPhoneNumber(number: String) {
        _phoneNumberInput.value = number
    }

    fun toggleStatement(statement: String) {
        val current = _selectedStatements.value.toMutableSet()
        if (current.contains(statement)) {
            current.remove(statement)
        } else {
            current.add(statement)
        }
        _selectedStatements.value = current
    }

    fun assessCaller() {
        speechAssistant.stop()
        val assessment = ScamDetectorEngine.assessPhoneCall(
            phoneNumber = _phoneNumberInput.value,
            selectedStatements = _selectedStatements.value.toList(),
            language = _language.value
        )
        _callerAssessment.value = assessment

        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModelScope.launch {
                try {
                    val contentSummary = buildString {
                        if (_phoneNumberInput.value.isNotBlank()) append("Phone: ${_phoneNumberInput.value}. ")
                        append("Statements: ${_selectedStatements.value.joinToString(", ")}")
                    }
                    firestoreRepository.recordCheckLog(
                        type = "call",
                        content = contentSummary,
                        riskLevel = assessment.riskLevel.name,
                        headline = "Call Assessment: ${assessment.riskLevel.name}",
                        explanation = assessment.whatScammerWants,
                        flaggedPhrases = assessment.reasonsForConcern
                    )
                } catch (ignored: Exception) {}
            }
        }
    }

    fun clearCallerAssessment() {
        _phoneNumberInput.value = ""
        _selectedStatements.value = emptySet()
        _callerAssessment.value = null
        speechAssistant.stop()
    }

    fun openScenario(scenario: ScamEducationalScenario) {
        _activeScenario.value = scenario
    }

    fun closeScenario() {
        _activeScenario.value = null
        speechAssistant.stop()
    }

    fun speak(text: String, utteranceId: String) {
        if (speechAssistant.isSpeaking.value && speechAssistant.currentUtteranceId.value == utteranceId) {
            speechAssistant.stop()
        } else {
            speechAssistant.speak(text, _language.value, utteranceId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        speechAssistant.shutdown()
    }
}
