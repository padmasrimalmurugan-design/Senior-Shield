package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Localization
import com.example.model.AppLanguage
import com.example.model.NavigationDestination
import com.example.ui.SeniorShieldViewModel
import com.example.ui.components.RiskBanner
import com.example.ui.components.SpeakIconButton
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.HighContrastDark
import com.example.ui.theme.RiskAmberText
import com.example.ui.theme.RiskRedBackground
import com.example.ui.theme.RiskRedBorder
import com.example.ui.theme.RiskRedText

@Composable
fun CheckCallScreen(
    viewModel: SeniorShieldViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val phoneNumber by viewModel.phoneNumberInput.collectAsState()
    val selectedStatements by viewModel.selectedStatements.collectAsState()
    val assessment by viewModel.callerAssessment.collectAsState()
    val isSpeaking by viewModel.speechAssistant.isSpeaking.collectAsState()
    val currentUtterance by viewModel.speechAssistant.currentUtteranceId.collectAsState()

    val statements = if (language == AppLanguage.ENGLISH) 
        Localization.callerStatementsEn 
        else Localization.callerStatementsTa

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
                    modifier = Modifier.testTag("check_call_back_button")
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
                    text = if (language == AppLanguage.ENGLISH) "Scam Call Safety Assistant" else "தொலைபேசி அழைப்பு பாதுகாப்பு",
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
            // Number Reputation Honest Assessment Disclaimer
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Disclaimer",
                            tint = GuardianNavy,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "Honest Security Note: Phone numbers can be easily forged or spoofed by criminals. We evaluate what the caller asked you to do rather than relying on phone numbers alone." 
                                else "முக்கிய உண்மை: மோசடி செய்பவர்கள் போலி எண்களை உருவாக்க முடியும். எனவே தொலைபேசி எண்ணை விட அழைப்பாளர் என்ன கேட்டார் என்பதை வைத்தே உண்மை கண்டறியப்படும்.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = HighContrastDark,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Step 1: Optional Caller Phone Number
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
                                "1. Caller Phone Number (Optional):" 
                                else "1. அழைத்த தொலைபேசி எண் (விருப்பத்தேர்வு):",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = HighContrastDark
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { viewModel.setPhoneNumber(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("caller_phone_input"),
                            placeholder = {
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "e.g. +91 98765 43210" else "எ.கா. +91 98765 43210",
                                    color = Color(0xFF94A3B8)
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
                    }
                }
            }

            // Step 2: What did the caller say?
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
                                "2. Select What the Caller Said or Demanded:" 
                                else "2. அழைப்பாளர் கூறியவற்றை தேர்வு செய்யவும்:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = HighContrastDark
                        )
                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "Tick all statements that match your call:" 
                                else "அழைப்பில் கூறப்பட்டவற்றை டிக் செய்யவும்:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                        )

                        statements.forEach { stmt ->
                            val isChecked = selectedStatements.contains(stmt)
                            Surface(
                                color = if (isChecked) Color(0xFFEFF6FF) else Color.Transparent,
                                shape = RoundedCornerShape(10.dp),
                                border = if (isChecked) BorderStroke(1.5.dp, GuardianNavy) else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { viewModel.toggleStatement(stmt) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { viewModel.toggleStatement(stmt) },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = GuardianNavy,
                                            uncheckedColor = Color(0xFF94A3B8)
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stmt,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = if (isChecked) GuardianNavy else HighContrastDark,
                                        fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Assess Call Button
            item {
                Button(
                    onClick = { viewModel.assessCaller() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GuardianNavy,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .testTag("assess_caller_button"),
                    shape = RoundedCornerShape(14.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneInTalk,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (language == AppLanguage.ENGLISH) "Assess This Call" else "இந்த அழைப்பை மதிப்பிடவும்",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Call Assessment Results
            if (assessment != null) {
                val callResult = assessment!!

                item {
                    Text(
                        text = if (language == AppLanguage.ENGLISH) "Call Assessment Result" else "அழைப்பு மதிப்பீட்டு முடிவு",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = HighContrastDark,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Risk Banner
                item {
                    val riskHeadline = if (language == AppLanguage.ENGLISH) {
                        when (callResult.riskLevel) {
                            com.example.model.ScamRiskLevel.HIGH_RISK -> "Critical Warning: Definite Fraud Call Signs"
                            com.example.model.ScamRiskLevel.SUSPICIOUS -> "Suspicious: Unverified Caller Demands"
                            else -> "No Direct Scam Markers Selected"
                        }
                    } else {
                        when (callResult.riskLevel) {
                            com.example.model.ScamRiskLevel.HIGH_RISK -> "அபாயகரமான எச்சரிக்கை: தீவிர மோசடி அழைப்பு"
                            com.example.model.ScamRiskLevel.SUSPICIOUS -> "சந்தேகத்திற்குரிய அழைப்பு"
                            else -> "நேரடி மோசடி குறிப்புகள் தேர்ந்தெடுக்கப்படவில்லை"
                        }
                    }

                    RiskBanner(
                        riskLevel = callResult.riskLevel,
                        headline = riskHeadline,
                        description = callResult.whatScammerWants,
                        isSpeaking = isSpeaking && currentUtterance == "CallAssessment",
                        onSpeak = {
                            val textToRead = "$riskHeadline. ${callResult.whatScammerWants}"
                            viewModel.speak(textToRead, "CallAssessment")
                        }
                    )
                }

                // Reasons for Concern
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
                                    text = if (language == AppLanguage.ENGLISH) "Reasons for Concern:" else "கவலைக்கான காரணங்கள்:",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = RiskRedText
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            callResult.reasonsForConcern.forEach { reason ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "•",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 18.sp,
                                        color = RiskRedText,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = reason,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = HighContrastDark,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                // Immediate Safety Action Steps
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Immediate Safety Action Steps:" else "உடனடி பாதுகாப்பு நடவடிக்கைகள்:",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GuardianNavy
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            callResult.recommendedSteps.forEach { step ->
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

                // Check Another Call
                item {
                    OutlinedButton(
                        onClick = { viewModel.clearCallerAssessment() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("check_another_call_button"),
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
                            text = if (language == AppLanguage.ENGLISH) "Check Another Call" else "மற்றொரு அழைப்பை சோதிக்கவும்",
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
