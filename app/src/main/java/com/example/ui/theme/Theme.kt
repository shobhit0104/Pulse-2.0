package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PulseLightColorScheme = lightColorScheme(
    primary = PulseTealPrimary,
    onPrimary = Color.White,
    primaryContainer = PulseTealContainer,
    onPrimaryContainer = PulseTealOnContainer,
    secondary = PulseTealLight,
    onSecondary = Color.White,
    secondaryContainer = PulseSurfaceVariant,
    onSecondaryContainer = PulseTextPrimary,
    tertiary = PulseGreen,
    onTertiary = Color.White,
    tertiaryContainer = PulseGreenLight,
    onTertiaryContainer = PulseGreenDark,
    background = PulseBackground,
    onBackground = PulseTextPrimary,
    surface = PulseSurface,
    onSurface = PulseTextPrimary,
    surfaceVariant = PulseSurfaceVariant,
    onSurfaceVariant = PulseTextSecondary,
    outline = PulseDivider,
    outlineVariant = Color(0xFFD4DEDC),
    error = PulseAlert,
    onError = Color.White,
    errorContainer = PulseAlertContainer,
    onErrorContainer = PulseAlert
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PulseLightColorScheme,
        typography = Typography,
        content = content
    )
}
