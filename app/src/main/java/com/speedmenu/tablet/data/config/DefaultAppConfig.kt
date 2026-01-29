package com.speedmenu.tablet.data.config

import androidx.compose.ui.graphics.Color
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.domain.model.AppConfig
import com.speedmenu.tablet.domain.model.Branding
import com.speedmenu.tablet.domain.model.CarouselAction
import com.speedmenu.tablet.domain.model.CarouselItem
import com.speedmenu.tablet.domain.model.HomeConfig
import com.speedmenu.tablet.domain.model.ThemeColors
import com.speedmenu.tablet.domain.model.ThemeTokens

/**
 * Configuração padrão (fallback) do aplicativo.
 * Usa EXATAMENTE as cores atuais do SpeedMenuColors para garantir
 * que o app continue com a mesma aparência visual.
 * 
 * Esta é a configuração usada quando:
 * - Cache está vazio
 * - Parse do cache falha
 * - Validação de cores falha
 * - Erro de rede (na etapa futura)
 */
object DefaultAppConfig {
    /**
     * Retorna a configuração padrão do app.
     * As cores são mapeadas do SpeedMenuColors atual para ThemeColors.
     */
    fun get(): AppConfig {
        return AppConfig(
            version = 1,
            branding = Branding(
                restaurantName = "SPEED MENU",
                logoUrl = null, // Usa placeholder por padrão
                logoUrlDark = null
            ),
            theme = ThemeTokens(
                light = ThemeColors(
                    // Mapeia cores do SpeedMenuColors para Material 3 Light
                    // Como o app é sempre dark, usamos as mesmas cores para light também
                    primary = SpeedMenuColors.Primary,
                    onPrimary = SpeedMenuColors.TextOnPrimary,
                    secondary = SpeedMenuColors.PrimaryLight,
                    onSecondary = SpeedMenuColors.TextOnPrimary,
                    background = SpeedMenuColors.BackgroundPrimary,
                    onBackground = SpeedMenuColors.TextPrimary,
                    surface = SpeedMenuColors.Surface,
                    onSurface = SpeedMenuColors.TextPrimary,
                    error = SpeedMenuColors.Error,
                    onError = SpeedMenuColors.TextOnPrimary
                ),
                dark = ThemeColors(
                    // Mapeia cores do SpeedMenuColors para Material 3 Dark
                    // Estas são as cores que o app usa atualmente
                    primary = SpeedMenuColors.Primary,
                    onPrimary = SpeedMenuColors.TextOnPrimary,
                    secondary = SpeedMenuColors.PrimaryLight,
                    onSecondary = SpeedMenuColors.TextOnPrimary,
                    background = SpeedMenuColors.BackgroundPrimary,
                    onBackground = SpeedMenuColors.TextPrimary,
                    surface = SpeedMenuColors.Surface,
                    onSurface = SpeedMenuColors.TextPrimary,
                    error = SpeedMenuColors.Error,
                    onError = SpeedMenuColors.TextOnPrimary
                )
            ),
            home = HomeConfig(
                carousel = emptyList() // Carrossel vazio por padrão (pode ser preenchido no mock)
            )
        )
    }
}

