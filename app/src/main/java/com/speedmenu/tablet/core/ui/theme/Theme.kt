package com.speedmenu.tablet.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.data.config.DefaultAppConfig
import com.speedmenu.tablet.domain.model.ThemeColors
import com.speedmenu.tablet.ui.viewmodel.AppConfigViewModel

/**
 * Esquema de cores dark premium do SpeedMenuTablet (fallback).
 * Inspirado em aplicativos profissionais de restaurante.
 * Usado quando AppConfig não está disponível.
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
 * Converte ThemeColors para ColorScheme do Material 3.
 * Inclui todos os tokens necessários para compatibilidade completa.
 */
private fun ThemeColors.toColorScheme(): ColorScheme {
    return darkColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primary.copy(alpha = 0.2f), // Container baseado na primary
        onPrimaryContainer = onPrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondary.copy(alpha = 0.2f), // Container baseado na secondary
        onSecondaryContainer = onSecondary,
        tertiary = secondary, // Usa secondary como tertiary
        onTertiary = onSecondary,
        tertiaryContainer = secondary.copy(alpha = 0.2f),
        onTertiaryContainer = onSecondary,
        error = error,
        onError = onError,
        errorContainer = error.copy(alpha = 0.2f),
        onErrorContainer = error,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surface.copy(alpha = 0.8f), // Variante mais clara
        onSurfaceVariant = onSurface.copy(alpha = 0.7f), // Variante mais escura
        outline = onSurface.copy(alpha = 0.3f), // Outline sutil
        outlineVariant = onSurface.copy(alpha = 0.1f) // Outline muito sutil
    )
}

/**
 * Theme principal do aplicativo SpeedMenuTablet.
 * Configura cores, tipografia e formas baseadas no Material Design 3.
 * Agora usa AppConfig para cores remotas, com fallback para cores padrão.
 *
 * @param darkTheme Define se o tema escuro deve ser usado
 * @param dynamicColor Habilita cores dinâmicas (Android 12+)
 * @param appConfigViewModel ViewModel do AppConfig (opcional, injeta automaticamente se não fornecido)
 * @param content Conteúdo composable que utilizará este theme
 */
@Composable
fun SpeedMenuTheme(
    darkTheme: Boolean = true, // Sempre dark para este app
    @Suppress("UNUSED_PARAMETER") dynamicColor: Boolean = false, // Reservado para uso futuro
    appConfigViewModel: AppConfigViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    // dynamicColor é reservado para uso futuro (Android 12+ dynamic colors)
    // Observa AppConfig
    val appConfig by appConfigViewModel.appConfig.collectAsState()
    
    // Resolve o ColorScheme baseado no AppConfig e dark mode
    // Usa remember para garantir que seja recalculado quando appConfig mudar
    // Usa variável local para permitir smart cast
    val colorScheme = remember(appConfig, darkTheme) {
        val currentConfig = appConfig
        if (currentConfig != null) {
            val themeColors = if (darkTheme) currentConfig.theme.dark else currentConfig.theme.light
            themeColors.toColorScheme()
        } else {
            // Fallback para cores padrão
            SpeedMenuDarkColorScheme
        }
    }

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

