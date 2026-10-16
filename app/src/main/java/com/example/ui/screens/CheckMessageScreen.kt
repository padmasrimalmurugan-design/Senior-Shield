package com.example.ui.screens

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.NavigationDestination
import com.example.model.ScamRiskLevel
import com.example.speech.SpeechAssistant
import com.example.ui.SeniorShieldViewModel
import com.example.ui.components.RiskBanner
import com.example.ui.components.SpeakIconButton
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.HighContrastDark
import com.example.ui.theme.HighContrastMuted
import com.example.ui.theme.RiskAmberText
import com.example.ui.theme.RiskGreenText
import com.example.ui.theme.RiskRedBackground
import com.example.ui.theme.RiskRedBorder
import com.example.ui.theme.RiskRedText

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckMessageScreen(
    viewModel: SeniorShieldViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val messageInput by viewModel.messageInput.collectAsState()
    val isAnalyzing by viewModel.isAnalyzingMessage.collectAsState()
    val result by viewModel.analysisResult.collectAsState()
    val errorMessage by viewModel.analysisError.collectAsState()
    val isSpeaking by viewModel.speechAssistant.isSpeaking.collectAsState()
    val currentUtterance by viewModel.speechAssistant.currentUtteranceId.collectAsState()
    val context = LocalContext.current

    // Speech-to-Text Activity Result Launcher
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val data = activityResult.data
            val spokenMatches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val topSpokenText = spokenMatches?.firstOrNull()
            if (!topSpokenText.isNullOrBlank()) {
                viewModel.setMessageInput(topSpokenText)
            }
        }
    }

    val sampleScamsEn = listOf(
        "Pension Stopped: Your pension of Rs. 32,000 is put on hold. Submit digital Life Certificate at http://pension-verify-portal.in in 24 hours.",
        "Bank KYC Alert: SBI YONO blocked. Click http://bit.ly/sbi-kyc-pan to link Aadhaar immediately.",
        "Caller asking for OTP: We detected suspicious Rs. 15,000 debit. Tell OTP to cancel.",
        "Family greeting: Happy retirement anniversary Grandpa, we are visiting this Sunday!"
    )

    val sampleScamsTa = listOf(
        "ஓய்வூதியம் நிறுத்தம்: உங்கள் ஓய்வூதியம் நிறுத்தப்பட்டுள்ளது. உடனே புதுப்பிக்க http://pension-verify-portal.in லிங்க் கிளிக் செய்க.",
        "வங்கி கணக்கு முடக்கப்படும்: உங்களின் KYC காலாவதியானது. புதுப்பிக்க http://bit.ly/sbi-kyc-pan கிளிக் செய்யவும்.",
        "வங்கி அதிகாரி பேசுவதாக கூறி OTP கேட்கும் அழைப்பு.",
        "தாத்தா, நாங்கள் ஞாயிற்றுக்கிழமை வருகிறோம் - குடும்ப செய்தி."
    )

    val samples = if (language == AppLanguage.ENGLISH) sampleScamsEn else sampleScamsTa

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // App Bar
        Surface(
            color = GuardianNavy,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo(NavigationDestination.HOME) },
                    modifier = Modifier.testTag("check_message_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (language == AppLanguage.ENGLISH) "Check a Suspicious Message" else "செய்தியை சோதிக்கவும்",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Privacy Warning Banner
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Privacy",
                            tint = GuardianNavy,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "Safety Note: Messages are checked securely. Do NOT paste your real passwords or bank PINs here." 
                                else "பாதுகாப்பு குறிப்பு: உங்கள் உண்மையான ரகசிய கடவுச்சொற்களை இதில் உள்ளிட வேண்டாம்.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = HighContrastDark,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Input Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "Paste or Type Message Below:" 
                                else "செய்தியை கீழே உள்ளிடவும்:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = HighContrastDark
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = messageInput,
                            onValueChange = { viewModel.setMessageInput(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .testTag("message_input_field"),
                            placeholder = {
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) 
                                        "Paste SMS, WhatsApp text, email, or pension alert here..." 
                                        else "SMS, வாட்ஸ்அப் அல்லது ஓய்வூதிய செய்தியை இங்கே ஒட்டவும்...",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 16.sp
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GuardianNavy,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedTextColor = HighContrastDark,
                                unfocusedTextColor = HighContrastDark
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action Buttons: Paste from Clipboard, Speak Voice, Clear
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Paste Button
                            OutlinedButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = clipboard.primaryClip
                                    if (clip != null && clip.itemCount > 0) {
                                        val pasteText = clip.getItemAt(0).text?.toString() ?: ""
                                        if (pasteText.isNotBlank()) {
                                            viewModel.setMessageInput(pasteText)
                                        }
                                    } else {
                                        Toast.makeText(context, "Clipboard empty", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("paste_button"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentPaste,
                                    contentDescription = "Paste",
                                    tint = GuardianNavy,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "Paste" else "ஒட்டுக",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Speak Voice Button
                            OutlinedButton(
                                onClick = {
                                    try {
                                        val intent = SpeechAssistant.createSpeechIntent(language)
                                        speechLauncher.launch(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Voice input not available on this device", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("voice_input_button"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "Speak",
                                    tint = GuardianNavy,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "Speak" else "பேசுக",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Clear Button
                            if (messageInput.isNotEmpty()) {
                                IconButton(
                                    onClick = { viewModel.clearMessageInput() },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .testTag("clear_input_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = HighContrastMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Sample test messages for quick testing
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (language == AppLanguage.ENGLISH) 
                            "Tap an example message to test:" 
                            else "சோதனைக்கு உதாரண செய்தியை தேர்ந்தெடுக்கவும்:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = HighContrastDark
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        samples.forEachIndexed { index, sample ->
                            val label = when (index) {
                                0 -> if (language == AppLanguage.ENGLISH) "Pension Hold Scam" else "ஓய்வூதிய மோசடி"
                                1 -> if (language == AppLanguage.ENGLISH) "Bank KYC Scam" else "KYC மோசடி"
                                2 -> if (language == AppLanguage.ENGLISH) "OTP Fraud" else "OTP திருட்டு"
                                else -> if (language == AppLanguage.ENGLISH) "Family Safe Message" else "பாதுகாப்பான செய்தி"
                            }

                            SuggestionChip(
                                onClick = {
                                    viewModel.setMessageInput(sample)
                                    viewModel.analyzeMessage(sample)
                                },
                                label = {
                                    Text(
                                        text = label,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (index == 3) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                                    labelColor = if (index == 3) RiskGreenText else RiskRedText
                                ),
                                border = BorderStroke(1.dp, if (index == 3) Color(0xFFA5D6A7) else Color(0xFFFFCDD2)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("sample_chip_$index")
                            )
                        }
                    }
                }
            }

            // Primary Analyze Button
            item {
                Button(
                    onClick = { viewModel.analyzeMessage() },
                    enabled = !isAnalyzing,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GuardianNavy,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .testTag("analyze_message_button"),
                    shape = RoundedCornerShape(14.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(26.dp),
                            strokeWidth = 3.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (language == AppLanguage.ENGLISH) "Analyzing with Senior Shield..." else "பகுப்பாய்வு செய்யப்படுகிறது...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (language == AppLanguage.ENGLISH) "Analyze Message" else "செய்தியை சோதிக்கவும்",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Error Message (if any)
            if (errorMessage != null) {
                item {
                    Text(
                        text = errorMessage ?: "",
                        color = RiskRedText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Analysis Result Section
            if (result != null) {
                val currentResult = result!!

                item {
                    Text(
                        text = if (language == AppLanguage.ENGLISH) "Detection Result" else "முடிவு",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = HighContrastDark,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Risk Banner
                item {
                    RiskBanner(
                        riskLevel = currentResult.riskLevel,
                        headline = currentResult.headline,
                        description = currentResult.explanation,
                        isSpeaking = isSpeaking && currentUtterance == "AnalysisResult",
                        onSpeak = {
                            val textToRead = "${currentResult.headline}. ${currentResult.explanation}"
                            viewModel.speak(textToRead, "AnalysisResult")
                        }
                    )
                }

                // AI vs Heuristic Engine Badge
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (currentResult.isAiPowered) Icons.Default.AutoAwesome else Icons.Default.Shield,
                            contentDescription = null,
                            tint = GuardianNavy,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentResult.isAiPowered) 
                                (if (language == AppLanguage.ENGLISH) "Powered by Gemini AI" else "Gemini AI மூலம் பகுப்பாய்வு செய்யப்பட்டது")
                                else (if (language == AppLanguage.ENGLISH) "Senior Shield Security Heuristics (Offline Demo Mode)" else "சீனியர் ஷீல்ட் ஆஃப்லைன் பாதுகாப்பு இயந்திரம்"),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 13.sp,
                            color = GuardianNavy,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Why Flagged / Specific Suspicious Phrases
                if (currentResult.flaggedPhrases.isNotEmpty()) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.5.dp, Color(0xFFFFCDD2)),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = RiskRedText,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (language == AppLanguage.ENGLISH) "Specific Suspicious Phrases Found:" else "கண்டறியப்பட்ட சந்தேகத்திற்குரிய வார்த்தைகள்:",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = RiskRedText
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    currentResult.flaggedPhrases.forEach { phrase ->
                                        Surface(
                                            color = RiskRedBackground,
                                            shape = RoundedCornerShape(8.dp),
                                            border = BorderStroke(1.dp, RiskRedBorder)
                                        ) {
                                            Text(
                                                text = "\"$phrase\"",
                                                color = RiskRedText,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Recommended Next Steps
                if (currentResult.recommendedSteps.isNotEmpty()) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "Recommended Safety Steps:" else "பரிந்துரைக்கப்படும் பாதுகாப்பு நடவடிக்கைகள்:",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GuardianNavy
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                currentResult.recommendedSteps.forEachIndexed { i, step ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = GuardianNavy,
                                            modifier = Modifier
                                                .size(22.dp)
                                                .padding(top = 2.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = step,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = HighContrastDark,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Action: Check Another Message
                item {
                    OutlinedButton(
                        onClick = { viewModel.clearMessageInput() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("check_another_message_button"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, GuardianNavy)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = GuardianNavy
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == AppLanguage.ENGLISH) "Check Another Message" else "மற்றொரு செய்தியை சோதிக்கவும்",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = GuardianNavy
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
