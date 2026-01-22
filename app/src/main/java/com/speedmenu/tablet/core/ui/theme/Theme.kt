package com.speedmenu.tablet.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Esquema de cores dark premium do SpeedMenuTablet.
 * Inspirado em aplicativos profissionais de restaurante.
 */
private val SpeedMenuDarkColorScheme = darkColorScheme(
    primary = SpeedMenuColors.Primary,
    onPrimary = SpeedMenuColors.TextOnPrimary,
    primaryContainer = SpeedMenuColors.PrimaryContainer,
    onPrimaryContainer = SpeedMenuColors.TextOnPrimary,
    secondary = SpeedMenuColors.PrimaryLight,
    onSecondary = SpeedMenuColors.TextOnPrimary,
    secondaryContainer = SpeedMenuColors.PrimaryContainer,
    onSecondaryContainer = SpeedMenuColors.TextOnPrimary,
    tertiary = SpeedMenuColors.PrimaryDark,
    onTertiary = SpeedMenuColors.TextOnPrimary,
    tertiaryContainer = SpeedMenuColors.PrimaryContainer,
    onTertiaryContainer = SpeedMenuColors.TextOnPrimary,
    error = SpeedMenuColors.Error,
    onError = SpeedMenuColors.TextOnPrimary,
    errorContainer = SpeedMenuColors.Error.copy(alpha = 0.2f),
    onErrorContainer = SpeedMenuColors.Error,
    background = SpeedMenuColors.BackgroundPrimary,
    onBackground = SpeedMenuColors.TextPrimary,
    surface = SpeedMenuColors.Surface,
    onSurface = SpeedMenuColors.TextPrimary,
    surfaceVariant = SpeedMenuColors.SurfaceElevated,
    onSurfaceVariant = SpeedMenuColors.TextSecondary,
    outline = SpeedMenuColors.Border,
    outlineVariant = SpeedMenuColors.BorderSubtle
)

/**
 * Theme principal do aplicativo SpeedMenuTablet.
 * Configura cores, tipografia e formas baseadas no Material Design 3.
 *
 * @param darkTheme Define se o tema escuro deve ser usado
 * @param dynamicColor Habilita cores dinâmicas (Android 12+)
 * @param content Conteúdo composable que utilizará este theme
 */
@Composable
fun SpeedMenuTheme(
    darkTheme: Boolean = true, // Sempre dark para este app
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // SpeedMenuTablet sempre usa tema dark premium
    val colorScheme = SpeedMenuDarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

