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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.EducationalScamsData
import com.example.model.AppLanguage
import com.example.model.NavigationDestination
import com.example.model.ScamEducationalScenario
import com.example.ui.SeniorShieldViewModel
import com.example.ui.components.SpeakIconButton
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.HighContrastDark
import com.example.ui.theme.RiskGreenText
import com.example.ui.theme.RiskRedBackground
import com.example.ui.theme.RiskRedBorder
import com.example.ui.theme.RiskRedText

@Composable
fun LearnScamsScreen(
    viewModel: SeniorShieldViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val activeScenario by viewModel.activeScenario.collectAsState()
    val isSpeaking by viewModel.speechAssistant.isSpeaking.collectAsState()
    val currentUtterance by viewModel.speechAssistant.currentUtteranceId.collectAsState()

    val scenarios = EducationalScamsData.scenarios

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
                    modifier = Modifier.testTag("learn_scams_back_button")
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
                    text = if (language == AppLanguage.ENGLISH) "Scam Awareness & Learning" else "மோசடிகள் பற்றி அறிதல்",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Educational Simulation Notice
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Educational",
                            tint = GuardianNavy,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "All sample messages below are educational simulations designed to help you recognize and defeat real fraud attempts." 
                                else "கீழே உள்ள அனைத்து செய்திகளும் நீங்கள் விழிப்புணர்வு பெற உருவாக்கப்பட்ட மாதிரி உதாரணங்கள் மட்டுமே.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = HighContrastDark,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // List of 8 Scenarios
            items(scenarios) { scenario ->
                val title = if (language == AppLanguage.ENGLISH) scenario.titleEn else scenario.titleTa
                val category = if (language == AppLanguage.ENGLISH) scenario.categoryEn else scenario.categoryTa

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.openScenario(scenario) }
                        .testTag("scenario_card_${scenario.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE3F2FD)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${scenario.id}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = GuardianNavy
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HighContrastDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = category,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF64748B),
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "View Details",
                            tint = GuardianNavy,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    // Detail Dialog for Selected Scenario
    if (activeScenario != null) {
        val scenario = activeScenario!!
        val title = if (language == AppLanguage.ENGLISH) scenario.titleEn else scenario.titleTa
        val sampleMsg = if (language == AppLanguage.ENGLISH) scenario.sampleMessageEn else scenario.sampleMessageTa
        val whatWants = if (language == AppLanguage.ENGLISH) scenario.whatScammerWantsEn else scenario.whatScammerWantsTa
        val warningSigns = if (language == AppLanguage.ENGLISH) scenario.warningSignsEn else scenario.warningSignsTa
        val howToProtect = if (language == AppLanguage.ENGLISH) scenario.howToProtectEn else scenario.howToProtectTa

        val fullTextToSpeak = "$title. Realistic Sample: $sampleMsg. What the scammer wants: $whatWants"

        Dialog(
            onDismissRequest = { viewModel.closeScenario() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Dialog Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(GuardianNavy)
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SpeakIconButton(
                                isSpeaking = isSpeaking && currentUtterance == "LearnScenario_${scenario.id}",
                                onClick = {
                                    viewModel.speak(fullTextToSpeak, "LearnScenario_${scenario.id}")
                                },
                                contentDescription = "Read lesson aloud",
                                tint = Color.White
                            )

                            IconButton(
                                onClick = { viewModel.closeScenario() },
                                modifier = Modifier.testTag("close_scenario_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Simulation Tag
                        item {
                            Surface(
                                color = Color(0xFFFEF3C7),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, Color(0xFFF59E0B))
                            ) {
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "EDUCATIONAL SIMULATION" else "கல்வி மாதிரி பயிற்சி",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF92400E),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        // Realistic Sample Scam Message
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = RiskRedBackground),
                                border = BorderStroke(1.5.dp, RiskRedBorder),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = if (language == AppLanguage.ENGLISH) "Realistic Sample Scam Message:" else "மாதிரி மோசடி செய்தி:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = RiskRedText,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "\"$sampleMsg\"",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = HighContrastDark,
                                        lineHeight = 24.sp
                                    )
                                }
                            }
                        }

                        // Warning Signs
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.5.dp, Color(0xFFFFCC80)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Warning,
                                            contentDescription = null,
                                            tint = Color(0xFFE65100),
                                            modifier = Modifier.size(22.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (language == AppLanguage.ENGLISH) "Warning Signs:" else "எச்சரிக்கை அறிகுறிகள்:",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color(0xFFE65100),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    warningSigns.forEach { sign ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text(
                                                text = "•",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                color = Color(0xFFE65100),
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                            Text(
                                                text = sign,
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = HighContrastDark
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // What the Scammer Wants
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                                border = BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = if (language == AppLanguage.ENGLISH) "What the Scammer Wants:" else "மோசடிக்காரனின் உண்மையான நோக்கம்:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = GuardianNavy,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = whatWants,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = HighContrastDark,
                                        lineHeight = 24.sp
                                    )
                                }
                            }
                        }

                        // How to Protect Yourself
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                                border = BorderStroke(1.5.dp, Color(0xFF81C784)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = RiskGreenText,
                                            modifier = Modifier.size(22.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (language == AppLanguage.ENGLISH) "How to Protect Yourself:" else "நீங்கள் செய்ய வேண்டியவை:",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = RiskGreenText,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    howToProtect.forEach { tip ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text(
                                                text = "✓",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = RiskGreenText,
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                            Text(
                                                text = tip,
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

                    // Done Button
                    Box(modifier = Modifier.padding(16.dp)) {
                        Button(
                            onClick = { viewModel.closeScenario() },
                            colors = ButtonDefaults.buttonColors(containerColor = GuardianNavy),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Understood – Close Lesson" else "புரிந்துகொண்டேன் – மூடுக",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
