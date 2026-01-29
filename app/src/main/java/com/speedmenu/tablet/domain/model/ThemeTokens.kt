package com.speedmenu.tablet.domain.model

/**
 * Tokens de tema (light e dark).
 * Contém os esquemas de cores para ambos os modos.
 */
data class ThemeTokens(
    /**
     * Cores do tema claro.
     */
    val light: ThemeColors,
    
    /**
     * Cores do tema escuro.
     */
    val dark: ThemeColors
)

