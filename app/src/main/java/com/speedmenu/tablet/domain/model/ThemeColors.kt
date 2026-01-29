package com.speedmenu.tablet.domain.model

import androidx.compose.ui.graphics.Color

/**
 * Cores do Material Design 3 para um tema (light ou dark).
 * Representa o esquema de cores completo do Material 3.
 */
data class ThemeColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val error: Color,
    val onError: Color
)

