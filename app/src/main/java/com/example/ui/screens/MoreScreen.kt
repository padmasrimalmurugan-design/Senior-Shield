package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.NavigationDestination
import com.example.ui.SeniorShieldViewModel
import com.example.ui.theme.AppBackground
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardWhite
import com.example.ui.theme.DarkPillButton
import com.example.ui.theme.PeachTagBg
import com.example.ui.theme.PeachTagText
import com.example.ui.theme.RiskRedBackground
import com.example.ui.theme.RiskRedBorder
import com.example.ui.theme.RiskRedText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun MoreScreen(
    viewModel: SeniorShieldViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userProfile = viewModel.userProfile
    val language by viewModel.language.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Account & Services",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        // Profile Card
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                border = BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = userProfile.avatarRes),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = userProfile.name,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Member ID: ${userProfile.memberId}",
                            fontSize = 13.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = PeachTagBg
                        ) {
                            Text(
                                text = "${userProfile.planTier} Policyholder",
                                color = PeachTagText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Senior Shield Fraud Protection Center
        item {
            Text(
                text = "Fraud Protection & Scam Detection",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            MoreNavigationItem(
                icon = Icons.Default.Security,
                iconBg = Color(0xFFEFF6FF),
                iconTint = Color(0xFF1D4ED8),
                title = "Senior Shield AI Scam Detector",
                subtitle = "Analyze suspicious SMS, WhatsApp, and pension messages",
                onClick = { viewModel.openScamShield(NavigationDestination.CHECK_MESSAGE) }
            )
        }

        item {
            MoreNavigationItem(
                icon = Icons.Default.Call,
                iconBg = Color(0xFFFFF7ED),
                iconTint = Color(0xFFC2410C),
                title = "Check a Suspicious Phone Call",
                subtitle = "Identify bank & pension impostor phone statements",
                onClick = { viewModel.openScamShield(NavigationDestination.CHECK_CALL) }
            )
        }

        item {
            MoreNavigationItem(
                icon = Icons.Default.School,
                iconBg = Color(0xFFF0FDF4),
                iconTint = Color(0xFF15803D),
                title = "Scam Awareness & Lessons",
                subtitle = "8 common scams targeting seniors and pensioners",
                onClick = { viewModel.openScamShield(NavigationDestination.LEARN) }
            )
        }

        // Emergency Assistance
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = RiskRedBackground),
                border = BorderStroke(1.5.dp, RiskRedBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.openScamShield(NavigationDestination.HELP) }
                    .testTag("emergency_protocol_more_item")
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
                            .background(RiskRedText),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Emergency Help & Helpline 1930",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RiskRedText
                        )
                        Text(
                            text = "Report fraud & freeze bank accounts immediately",
                            fontSize = 12.sp,
                            color = TextPrimary
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = RiskRedText
                    )
                }
            }
        }

        // Settings & Language
        item {
            Text(
                text = "Preferences",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            MoreNavigationItem(
                icon = Icons.Default.Language,
                iconBg = Color(0xFFF3F4F6),
                iconTint = TextPrimary,
                title = "Language / மொழி",
                subtitle = if (language == AppLanguage.ENGLISH) "English (Tap to switch to தமிழ்)" else "தமிழ் (ஆங்கிலத்திற்கு மாற்ற தட்டவும்)",
                onClick = {
                    viewModel.setLanguage(if (language == AppLanguage.ENGLISH) AppLanguage.TAMIL else AppLanguage.ENGLISH)
                    Toast.makeText(context, "Language switched", Toast.LENGTH_SHORT).show()
                }
            )
        }

        item {
            MoreNavigationItem(
                icon = Icons.Default.Settings,
                iconBg = Color(0xFFF3F4F6),
                iconTint = TextPrimary,
                title = "Settings & Voice Controls",
                subtitle = "Adjust text size, speech cadence, and privacy",
                onClick = { viewModel.openScamShield(NavigationDestination.SETTINGS) }
            )
        }

        item {
            val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
            MoreNavigationItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                iconBg = Color(0xFFFEF2F2),
                iconTint = Color(0xFFDC2626),
                title = "Sign Out",
                subtitle = "Securely sign out of Senior Shield",
                onClick = {
                    val credentialManager = androidx.credentials.CredentialManager.create(context)
                    signOutUser(
                        context = context,
                        credentialManager = credentialManager,
                        onSignOutComplete = {
                            Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                        },
                        scope = coroutineScope
                    )
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MoreNavigationItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        border = BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
