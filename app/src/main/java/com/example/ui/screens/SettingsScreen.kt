package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import com.example.model.AppLanguage
import com.example.model.NavigationDestination
import com.example.model.TextScale
import com.example.ui.SeniorShieldViewModel
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.HighContrastDark

@Composable
fun SettingsScreen(
    viewModel: SeniorShieldViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val textScale by viewModel.textScale.collectAsState()
    val isSpeaking by viewModel.speechAssistant.isSpeaking.collectAsState()
    val currentUtterance by viewModel.speechAssistant.currentUtteranceId.collectAsState()

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
                    modifier = Modifier.testTag("settings_back_button")
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
                    text = if (language == AppLanguage.ENGLISH) "Settings & Privacy" else "அமைப்புகள் & தனியுரிமை",
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
            // 1. Language Selection
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = GuardianNavy,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "App Language / மொழி" else "மொழி தேர்வு",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HighContrastDark
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // English Option
                        LanguageOptionItem(
                            title = "English (ஆங்கிலம்)",
                            isSelected = language == AppLanguage.ENGLISH,
                            onSelect = { viewModel.setLanguage(AppLanguage.ENGLISH) },
                            testTagId = "language_option_english"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Tamil Option
                        LanguageOptionItem(
                            title = "தமிழ் (Tamil)",
                            isSelected = language == AppLanguage.TAMIL,
                            onSelect = { viewModel.setLanguage(AppLanguage.TAMIL) },
                            testTagId = "language_option_tamil"
                        )
                    }
                }
            }

            // 2. Text Size Adjustment for Elderly Readability
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = null,
                                tint = GuardianNavy,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Text Size / எழுத்து அளவு" else "எழுத்து அளவு",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HighContrastDark
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        TextScale.values().forEach { scale ->
                            val label = if (language == AppLanguage.ENGLISH) scale.labelEn else scale.labelTa
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { viewModel.setTextScale(scale) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = textScale == scale,
                                    onClick = { viewModel.setTextScale(scale) },
                                    colors = RadioButtonDefaults.colors(selectedColor = GuardianNavy)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (textScale == scale) FontWeight.Bold else FontWeight.Medium,
                                    color = HighContrastDark
                                )
                            }
                        }
                    }
                }
            }

            // 3. Voice Assistance Testing
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = null,
                                tint = GuardianNavy,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Voice Assistance Test" else "குரல் உதவி பரிசோதனை",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HighContrastDark
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "Tap below to test reading warnings aloud in your selected language at a slow, clear pace." 
                                else "தேர்ந்தெடுக்கப்பட்ட மொழியில் அறிவிப்புகளை வாசிப்பதை சோதிக்க கீழே தட்டவும்.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val testSample = if (language == AppLanguage.ENGLISH) {
                            "Hello! Senior Shield is ready to protect your pension from fraudulent calls and messages."
                        } else {
                            "வணக்கம்! உங்கள் ஓய்வூதியத்தை மோசடிகளிலிருந்து பாதுகாக்க சீனியர் ஷீல்ட் எப்போதும் தயாராக உள்ளது."
                        }

                        Button(
                            onClick = {
                                viewModel.speak(testSample, "SettingsVoiceTest")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GuardianNavy),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("test_speech_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Test Voice Read-Aloud" else "குரல் பரிசோதனை",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // 4. Privacy & Security Promise
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PrivacyTip,
                                contentDescription = null,
                                tint = GuardianNavy,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Privacy & Safety Guarantee" else "தனியுரிமை & பாதுகாப்பு உறுதி",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HighContrastDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val pointsEn = listOf(
                            "Zero Password Storage: Senior Shield NEVER requests, stores, or transmits bank passwords, ATM PINs, OTPs, or CVV codes.",
                            "No Background Interception: The app does NOT record calls, intercept private SMS, or spy on contacts in the background.",
                            "Ephemeral Processing: Messages submitted for analysis are examined in memory and discarded immediately.",
                            "Offline Capability: Smart security heuristics function fully on your device even without internet connection."
                        )

                        val pointsTa = listOf(
                            "கடவுச்சொற்கள் சேமிக்கப்படுவதில்லை: உங்கள் வங்கி PIN, OTP அல்லது கடவுச்சொற்களை இந்த செயலி ஒருபோதும் கேட்காது.",
                            "பின்னணி உளவு கிடையாது: அழைப்புகளை தானாக பதிவு செய்வதோ, SMS-ஐ ரகசியமாக படிப்போ செயலி செய்யாது.",
                            "நினைவகத்தில் மட்டுமே ஆய்வு: நீங்கள் சோதிக்க உள்ளிடும் செய்தி முடிந்தவுடன் அழிக்கப்படுகிறது.",
                            "ஆஃப்லைன் பாதுகாப்பு: இணையம் இல்லாவிட்டாலும் உங்கள் போனில் உள்ள பாதுகாப்பு இயந்திரம் செயல்படும்."
                        )

                        val points = if (language == AppLanguage.ENGLISH) pointsEn else pointsTa

                        points.forEach { pt ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "🔒",
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = pt,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = HighContrastDark,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }

            // About Senior Shield
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Senior Shield v1.0",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GuardianNavy
                    )
                    Text(
                        text = "Dedicated Fraud Protection for Pensioners",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun LanguageOptionItem(
    title: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    testTagId: String
) {
    Surface(
        color = if (isSelected) Color(0xFFEFF6FF) else Color(0xFFF8FAFC),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, if (isSelected) GuardianNavy else Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .testTag(testTagId)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = GuardianNavy)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) GuardianNavy else HighContrastDark,
                fontSize = 17.sp
            )
        }
    }
}
