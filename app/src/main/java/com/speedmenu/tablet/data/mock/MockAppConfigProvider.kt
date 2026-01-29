package com.speedmenu.tablet.data.mock

import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.domain.model.AppConfig
import com.speedmenu.tablet.domain.model.Branding
import com.speedmenu.tablet.domain.model.CarouselAction
import com.speedmenu.tablet.domain.model.CarouselItem
import com.speedmenu.tablet.domain.model.HomeConfig
import com.speedmenu.tablet.domain.model.ThemeColors
import com.speedmenu.tablet.domain.model.ThemeTokens
import timber.log.Timber

/**
 * Provider mockado de AppConfig.
 * Retorna uma configuração que usa EXATAMENTE as cores atuais do SpeedMenuColors.
 * 
 * Esta é a fonte de dados mockada que simula a resposta do backend.
 * Na etapa 2, será substituída por uma chamada real à API.
 */
object MockAppConfigProvider {
    /**
     * Retorna um AppConfig mockado para o restaurante especificado.
     * 
     * @param restaurantId ID do restaurante (pode ser usado para retornar configs diferentes)
     * @return AppConfig mockado
     */
    fun getMockConfig(restaurantId: String): AppConfig {
        Timber.d("MockAppConfigProvider: generating mock config for restaurant=$restaurantId")
        
        return AppConfig(
            version = 12, // Versão mockada (simula atualização)
            branding = Branding(
                restaurantName = "Restaurante Exemplo",
                logoUrl = null, // Pode ser preenchido com URL mockada se necessário
                logoUrlDark = null
            ),
            theme = ThemeTokens(
                light = ThemeColors(
                    // Usa as mesmas cores do SpeedMenuColors (app sempre dark, mas precisa de light também)
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
                    // Usa EXATAMENTE as cores atuais do SpeedMenuColors
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
                carousel = listOf(
                    // Mock de carrossel com URLs públicas de exemplo
                    // Na prática, estas URLs podem não funcionar, mas servem como exemplo
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800",
                        action = CarouselAction.Category("burgers")
                    ),
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800",
                        action = CarouselAction.Product("p123")
                    ),
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=800",
                        action = CarouselAction.Url("https://instagram.com/restaurante")
                    )
                )
            )
        )
    }
}

