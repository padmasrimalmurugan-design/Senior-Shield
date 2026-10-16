package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.data.Localization
import com.example.model.AppLanguage
import com.example.model.NavigationDestination
import com.example.ui.SeniorShieldViewModel
import com.example.ui.components.GoldenRuleBanner
import com.example.ui.components.LargeActionCard
import com.example.ui.components.SeniorHeader
import com.example.ui.components.SpeakIconButton
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.HighContrastDark
import com.example.ui.theme.RiskRedBackground
import com.example.ui.theme.RiskRedBorder
import com.example.ui.theme.RiskRedText

@Composable
fun HomeScreen(
    viewModel: SeniorShieldViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isSpeaking by viewModel.speechAssistant.isSpeaking.collectAsState()
    val currentUtterance by viewModel.speechAssistant.currentUtteranceId.collectAsState()
    val context = LocalContext.current

    val tipTitle = Localization.tipOfTheDayTitle(language)
    val tipText = Localization.tipOfTheDay(language)
    val goldenRuleText = Localization.goldenRuleText(language)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Header
        SeniorHeader(
            title = Localization.appName(language),
            tagline = Localization.appTagline(language),
            currentLanguage = language,
            onToggleLanguage = {
                viewModel.setLanguage(if (language == AppLanguage.ENGLISH) AppLanguage.TAMIL else AppLanguage.ENGLISH)
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Golden Rule Banner
            item {
                GoldenRuleBanner(
                    title = Localization.goldenRuleTitle(language),
                    message = goldenRuleText,
                    isSpeaking = isSpeaking && currentUtterance == "GoldenRule",
                    onSpeak = {
                        viewModel.speak(goldenRuleText, "GoldenRule")
                    }
                )
            }

            // Safety Tip of the Day
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F7FF)),
                    border = BorderStroke(1.5.dp, Color(0xFF90CAF9)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("daily_tip_card")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(GuardianNavy),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Tip",
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tipTitle,
                                style = MaterialTheme.typography.titleMedium,
                                color = GuardianNavy,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tipText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = HighContrastDark,
                                lineHeight = 24.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        SpeakIconButton(
                            isSpeaking = isSpeaking && currentUtterance == "TipOfTheDay",
                            onClick = {
                                viewModel.speak(tipText, "TipOfTheDay")
                            },
                            contentDescription = "Read safety tip aloud"
                        )
                    }
                }
            }

            // Section Label
            item {
                Text(
                    text = if (language == AppLanguage.ENGLISH) "Quick Actions" else "முக்கிய சேவைகள்",
                    style = MaterialTheme.typography.headlineSmall,
                    color = HighContrastDark,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }

            // 1. Check a Message
            item {
                LargeActionCard(
                    title = if (language == AppLanguage.ENGLISH) "1. Check a Message" else "1. செய்தியை சோதிக்கவும்",
                    subtitle = if (language == AppLanguage.ENGLISH) 
                        "Check SMS, WhatsApp message, or pension email" 
                        else "SMS, வாட்ஸ்அப் அல்லது ஓய்வூதிய செய்தியை சோதிக்கவும்",
                    icon = Icons.Default.MarkEmailRead,
                    iconBgColor = Color(0xFFE8F5E9),
                    iconTint = Color(0xFF2E7D32),
                    onClick = { viewModel.navigateTo(NavigationDestination.CHECK_MESSAGE) },
                    testTagId = "action_check_message"
                )
            }

            // 2. Check a Phone Number & Call
            item {
                LargeActionCard(
                    title = if (language == AppLanguage.ENGLISH) "2. Check a Phone Number" else "2. தொலைபேசி அழைப்பை சோதிக்கவும்",
                    subtitle = if (language == AppLanguage.ENGLISH) 
                        "Check suspicious calls from 'bank' or 'pension office'" 
                        else "வங்கி அல்லது ஓய்வூதிய அலுவலக அழைப்புகளை சோதிக்கவும்",
                    icon = Icons.Default.PhoneInTalk,
                    iconBgColor = Color(0xFFFFF3E0),
                    iconTint = Color(0xFFE65100),
                    onClick = { viewModel.navigateTo(NavigationDestination.CHECK_CALL) },
                    testTagId = "action_check_call"
                )
            }

            // 3. Learn About Scams
            item {
                LargeActionCard(
                    title = if (language == AppLanguage.ENGLISH) "3. Learn About Scams" else "3. மோசடிகள் பற்றி அறியவும்",
                    subtitle = if (language == AppLanguage.ENGLISH) 
                        "Learn 8 common scams targeting elderly pensioners" 
                        else "முதியோரை குறிவைக்கும் 8 பொதுவான மோசடிகளை கற்கவும்",
                    icon = Icons.Default.School,
                    iconBgColor = Color(0xFFE3F2FD),
                    iconTint = GuardianNavy,
                    onClick = { viewModel.navigateTo(NavigationDestination.LEARN) },
                    testTagId = "action_learn_scams"
                )
            }

            // 4. Emergency Help
            item {
                LargeActionCard(
                    title = if (language == AppLanguage.ENGLISH) "4. Emergency Help" else "4. அவசர உதவி (1930)",
                    subtitle = if (language == AppLanguage.ENGLISH) 
                        "Steps if money transferred + Call National Helpline 1930" 
                        else "பணம் இழந்திருந்தால் எடுக்க வேண்டிய நடவடிக்கைகள் + 1930",
                    icon = Icons.Default.Warning,
                    iconBgColor = RiskRedBackground,
                    iconTint = RiskRedText,
                    onClick = { viewModel.navigateTo(NavigationDestination.HELP) },
                    testTagId = "action_emergency_help"
                )
            }

            // Emergency Helpline Quick Banner
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = RiskRedBackground),
                    border = BorderStroke(2.dp, RiskRedBorder),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1930"))
                            context.startActivity(intent)
                        }
                        .testTag("home_quick_dial_1930")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(RiskRedText),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Call",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "National Cybercrime Helpline" else "தேசிய இணையக் குற்ற உதவி எண்",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = RiskRedText,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "Tap to dial 1930 immediately" else "1930 எண்ணிற்கு உடனே அழைக்க தட்டவும்",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = HighContrastDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Text(
                            text = "1930",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RiskRedText
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
