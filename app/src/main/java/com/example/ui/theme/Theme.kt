package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CoralHeroMid,
    onPrimary = Color.White,
    primaryContainer = PeachTagBg,
    onPrimaryContainer = PeachTagText,
    secondary = DarkPillButton,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF3F4F6),
    onSecondaryContainer = TextPrimary,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = CardWhite,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFF9FAFB),
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle
)

private val DarkColorScheme = darkColorScheme(
    primary = CoralHeroMid,
    onPrimary = Color.White,
    background = Color(0xFF121418),
    onBackground = Color(0xFFF3F4F6),
    surface = Color(0xFF1C1E24),
    onSurface = Color(0xFFF3F4F6),
    surfaceVariant = Color(0xFF262932),
    onSurfaceVariant = Color(0xFF9CA3AF),
    outline = Color(0xFF374151)
)

@Composable
fun SeniorShieldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
