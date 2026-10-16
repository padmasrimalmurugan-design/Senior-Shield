package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.ScamRiskLevel
import com.example.ui.theme.GoldenAlert
import com.example.ui.theme.GoldenAlertBg
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.HighContrastDark
import com.example.ui.theme.RiskAmberBackground
import com.example.ui.theme.RiskAmberBorder
import com.example.ui.theme.RiskAmberText
import com.example.ui.theme.RiskGreenBackground
import com.example.ui.theme.RiskGreenBorder
import com.example.ui.theme.RiskGreenText
import com.example.ui.theme.RiskRedBackground
import com.example.ui.theme.RiskRedBorder
import com.example.ui.theme.RiskRedText

@Composable
fun SeniorHeader(
    title: String,
    tagline: String,
    currentLanguage: AppLanguage,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = GuardianNavy,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = tagline,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD7E3FF),
                    fontWeight = FontWeight.Medium
                )
            }

            // Language quick toggle button
            Button(
                onClick = onToggleLanguage,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = GuardianNavy
                ),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, Color(0xFFB0BEC5)),
                modifier = Modifier.testTag("language_toggle_button")
            ) {
                Text(
                    text = if (currentLanguage == AppLanguage.ENGLISH) "தமிழ்" else "English",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun GoldenRuleBanner(
    title: String,
    message: String,
    isSpeaking: Boolean,
    onSpeak: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = GoldenAlertBg),
        border = BorderStroke(2.dp, GoldenAlert),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("golden_rule_banner")
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
                    .background(GoldenAlert),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Security Rule",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldenAlert,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = HighContrastDark,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            SpeakIconButton(
                isSpeaking = isSpeaking,
                onClick = onSpeak,
                contentDescription = "Read safety rule aloud"
            )
        }
    }
}

@Composable
fun SpeakIconButton(
    isSpeaking: Boolean,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint: Color = GuardianNavy
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (isSpeaking) GuardianNavy.copy(alpha = 0.15f) else Color.Transparent)
            .scale(if (isSpeaking) pulseScale else 1.0f)
            .testTag("speak_icon_button")
    ) {
        Icon(
            imageVector = if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = contentDescription,
            tint = if (isSpeaking) RiskRedText else tint,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun LargeActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    onClick: () -> Unit,
    testTagId: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Color(0xFFD6E4FF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTagId)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = HighContrastDark,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4B5563)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = GuardianNavy,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun RiskBanner(
    riskLevel: ScamRiskLevel,
    headline: String,
    description: String,
    isSpeaking: Boolean,
    onSpeak: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, borderColor, icon) = when (riskLevel) {
        ScamRiskLevel.LOW_RISK -> Quadruple(
            RiskGreenBackground,
            RiskGreenText,
            RiskGreenBorder,
            Icons.Default.CheckCircle
        )
        ScamRiskLevel.SUSPICIOUS -> Quadruple(
            RiskAmberBackground,
            RiskAmberText,
            RiskAmberBorder,
            Icons.Default.WarningAmber
        )
        ScamRiskLevel.HIGH_RISK -> Quadruple(
            RiskRedBackground,
            RiskRedText,
            RiskRedBorder,
            Icons.Default.Warning
        )
        ScamRiskLevel.UNKNOWN -> Quadruple(
            Color(0xFFF1F5F9),
            Color(0xFF475569),
            Color(0xFF94A3B8),
            Icons.AutoMirrored.Filled.Help
        )
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(2.5.dp, borderColor),
        shape = RoundedCornerShape(18.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("risk_banner_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Risk Status",
                        tint = textColor,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = when (riskLevel) {
                            ScamRiskLevel.LOW_RISK -> "LOW RISK"
                            ScamRiskLevel.SUSPICIOUS -> "SUSPICIOUS"
                            ScamRiskLevel.HIGH_RISK -> "HIGH RISK"
                            ScamRiskLevel.UNKNOWN -> "UNABLE TO DETERMINE"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        color = textColor,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                }

                SpeakIconButton(
                    isSpeaking = isSpeaking,
                    onClick = onSpeak,
                    contentDescription = "Read risk assessment aloud",
                    tint = textColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = HighContrastDark,
                lineHeight = 26.sp
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
