package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.SeniorShieldViewModel
import com.example.ui.components.SpeakIconButton
import com.example.ui.theme.GoldenAlert
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.HighContrastDark
import com.example.ui.theme.RiskRedBackground
import com.example.ui.theme.RiskRedBorder
import com.example.ui.theme.RiskRedText

@Composable
fun EmergencyHelpScreen(
    viewModel: SeniorShieldViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isSpeaking by viewModel.speechAssistant.isSpeaking.collectAsState()
    val currentUtterance by viewModel.speechAssistant.currentUtteranceId.collectAsState()
    val context = LocalContext.current

    var showEmergencyGuide by remember { mutableStateOf(false) }

    val emergencySpeechText = if (language == AppLanguage.ENGLISH) {
        "Emergency Action Protocol. Step 1: Call your bank immediately using the phone number on your passbook or ATM card to freeze your card and account. Step 2: Call Cybercrime Helpline 1930. Step 3: Preserve all messages, phone numbers, and screenshots. Never share your OTP with anyone claiming to help you."
    } else {
        "அவசர உதவி நெறிமுறை. முதல் படி: உங்கள் பாஸ்புக் அல்லது ஏடிஎம் கார்டில் உள்ள வங்கி எண்ணிற்கு உடனே அழைத்து உங்கள் கணக்கை முடக்குங்கள். இரண்டாம் படி: 1930 இணைய குற்ற உதவி எண்ணை அழையுங்கள். மூன்றாம் படி: அனைத்து செய்திகளையும் பாதுகாத்து வையுங்கள்."
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // App Bar
        Surface(
            color = RiskRedText,
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
                    modifier = Modifier.testTag("emergency_back_button")
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
                    text = if (language == AppLanguage.ENGLISH) "Emergency Fraud Help" else "அவசர மோசடி உதவி மையம்",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                SpeakIconButton(
                    isSpeaking = isSpeaking && currentUtterance == "EmergencyProtocol",
                    onClick = {
                        viewModel.speak(emergencySpeechText, "EmergencyProtocol")
                    },
                    contentDescription = "Read emergency guidance aloud",
                    tint = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Big Urgent Panic Trigger Button
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = RiskRedBackground),
                    border = BorderStroke(2.5.dp, RiskRedText),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("scammed_urgent_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(RiskRedText),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ReportProblem,
                                contentDescription = "Emergency Alert",
                                tint = Color.White,
                                modifier = Modifier.size(38.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "I Think I Have Been Scammed" 
                                else "நான் ஏமாற்றப்பட்டுவிட்டேன் என உணர்கிறேன்",
                            style = MaterialTheme.typography.headlineSmall,
                            color = RiskRedText,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "Stay calm. Take immediate action below to protect your remaining funds and freeze compromised accounts." 
                                else "பதற்றமடையாதீர்கள். உங்கள் வங்கிக் கணக்கை உடனடியாக முடக்க கீழே உள்ள வழிகாட்டுதலைப் பின்பற்றுங்கள்.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = HighContrastDark,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { showEmergencyGuide = !showEmergencyGuide },
                            colors = ButtonDefaults.buttonColors(containerColor = RiskRedText),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .testTag("toggle_emergency_checklist")
                        ) {
                            Text(
                                text = if (showEmergencyGuide) 
                                    (if (language == AppLanguage.ENGLISH) "Hide Step-by-Step Guide" else "வழிகாட்டியை மறை")
                                    else (if (language == AppLanguage.ENGLISH) "Show Immediate Action Steps" else "உடனடி நடவடிக்கைகளைக் காட்டு"),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Dial 1930 Cybercrime Helpline Action
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, GuardianNavy),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(GuardianNavy),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Call 1930",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "National Cybercrime Helpline" else "தேசிய இணையக் குற்ற உதவி எண்",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = GuardianNavy,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (language == AppLanguage.ENGLISH) "Toll-free 24x7 Government Helpline" else "கட்டணமில்லா 24 மணி நேர அரசு உதவி எண்",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1930"))
                                context.startActivity(dialIntent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GuardianNavy),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("call_1930_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Call 1930 Now" else "1930 எண்ணிற்கு அழைக்கவும்",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Portal Link: National Cyber Crime Reporting Portal
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = GuardianNavy,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Official National Portal" else "அரசு அதிகாரப்பூர்வ போர்டல்",
                                style = MaterialTheme.typography.titleLarge,
                                color = HighContrastDark,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "File an online complaint with the Government of India National Cyber Crime Reporting Portal:" 
                                else "இந்திய அரசு தேசிய இணைய குற்ற அறிக்கை இணையதளத்தில் புகார் பதிவு செய்யவும்:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = HighContrastDark
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cybercrime.gov.in/"))
                                context.startActivity(browserIntent)
                            },
                            border = BorderStroke(2.dp, GuardianNavy),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("open_cybercrime_portal_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInBrowser,
                                contentDescription = null,
                                tint = GuardianNavy
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "cybercrime.gov.in",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = GuardianNavy
                            )
                        }
                    }
                }
            }

            // Step-by-Step Guidance
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = if (language == AppLanguage.ENGLISH) "Crucial Steps If You Were Scammed:" else "நீங்கள் உடனே செய்ய வேண்டிய 4 படிகள்:",
                            style = MaterialTheme.typography.titleLarge,
                            color = HighContrastDark,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Step 1: Call Bank
                        GuidanceStepItem(
                            stepNumber = "1",
                            title = if (language == AppLanguage.ENGLISH) "Call Your Bank Immediately" else "உடனடியாக உங்கள் வங்கியை தொடர்பு கொள்ளவும்",
                            description = if (language == AppLanguage.ENGLISH) 
                                "Look up your bank's phone number ONLY from the back of your passbook or ATM card. Request them to immediately block your ATM card, internet banking, and freeze your account to stop further withdrawals." 
                                else "பாஸ்புக் அல்லது ஏடிஎம் கார்டில் உள்ள எண்ணை மட்டும் அழைத்து, உங்கள் கார்டு மற்றும் கணக்கை உடனடியாக முடக்குமாறு (Block/Freeze) கோருங்கள்."
                        )

                        // Step 2: Preserve Evidence
                        GuidanceStepItem(
                            stepNumber = "2",
                            title = if (language == AppLanguage.ENGLISH) "Preserve All Evidence" else "அனைத்து ஆதாரங்களையும் சேமித்து வைக்கவும்",
                            description = if (language == AppLanguage.ENGLISH) 
                                "Do NOT delete SMS messages, WhatsApp chats, or call records. Take screenshots of transaction IDs, debit SMS alerts, and phone numbers. The police need these." 
                                else "SMS, வாட்ஸ்அப் உரையாடல்கள் அல்லது அழைப்பு பதிவுகளை அழிக்க வேண்டாம். பரிவர்த்தனை எண்கள் மற்றும் ஸ்கிரீன்ஷாட்களை பாதுகாப்பாக வைக்கவும்."
                        )

                        // Step 3: Report to 1930 within the 'Golden Hour'
                        GuidanceStepItem(
                            stepNumber = "3",
                            title = if (language == AppLanguage.ENGLISH) "Report to 1930 Promptly" else "1930 எண்ணிற்கு உடனே புகார் அளிக்கவும்",
                            description = if (language == AppLanguage.ENGLISH) 
                                "Reporting within the first few hours gives police and banks the best chance to freeze money in the scammer's beneficiary account before they withdraw it at an ATM." 
                                else "முதல் சில மணி நேரங்களுக்குள் தெரிவித்தால் மட்டுமே குற்றவாளியின் கணக்கில் உள்ள பணத்தை முடக்க முடியும்."
                        )

                        // Step 4: Disconnect Remote Apps
                        GuidanceStepItem(
                            stepNumber = "4",
                            title = if (language == AppLanguage.ENGLISH) "Uninstall Unknown Apps" else "தெரியாத செயலிகளை நீக்கவும்",
                            description = if (language == AppLanguage.ENGLISH) 
                                "If the caller instructed you to install AnyDesk, TeamViewer, or any APK, turn off your phone's Wi-Fi and Mobile Data immediately, then uninstall the application." 
                                else "AnyDesk அல்லது TeamViewer போன்ற செயலிகளை நிறுவியிருந்தால், உடனே இணையத்தை அணைத்துவிட்டு செயலியை அன்இன்ஸ்டால் செய்யவும்."
                        )
                    }
                }
            }

            // Legal Disclaimer & Golden Security Rule
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                    border = BorderStroke(1.5.dp, Color(0xFFFCA5A5)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = RiskRedText,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.ENGLISH) "Important Fraud Recovery Warning:" else "முக்கிய எச்சரிக்கை:",
                                style = MaterialTheme.typography.titleMedium,
                                color = RiskRedText,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (language == AppLanguage.ENGLISH) 
                                "Never share your OTP, PIN, password, or screen-sharing access with ANYONE claiming to 'help you recover' lost money. Legitimate authorities never ask for private passwords. Note: Senior Shield and helplines cannot guarantee recovery of lost funds, but acting quickly gives the highest chance of freezing transactions." 
                                else "இழந்த பணத்தை மீட்டுத் தருவதாக கூறி யாரேனும் OTP அல்லது கடவுச்சொல்லை கேட்டால் ஒருபோதும் கொடுக்காதீர்கள். இழந்த பணத்தை முழுமையாக மீட்பதற்கு யாரும் உத்தரவாதம் அளிக்க முடியாது, ஆனால் விரைந்து செயல்பட்டால் பணத்தை முடக்கலாம்.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = HighContrastDark,
                            lineHeight = 24.sp
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

@Composable
private fun GuidanceStepItem(
    stepNumber: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFE2E8F0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                fontWeight = FontWeight.ExtraBold,
                color = GuardianNavy,
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = HighContrastDark,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF334155),
                lineHeight = 22.sp
            )
        }
    }
}
