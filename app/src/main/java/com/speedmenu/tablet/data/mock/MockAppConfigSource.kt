package com.speedmenu.tablet.data.mock

import androidx.compose.ui.graphics.Color
import com.speedmenu.tablet.data.config.DefaultAppConfig
import com.speedmenu.tablet.domain.model.AppConfig
import com.speedmenu.tablet.domain.model.Branding
import com.speedmenu.tablet.domain.model.CarouselAction
import com.speedmenu.tablet.domain.model.CarouselItem
import com.speedmenu.tablet.domain.model.HomeConfig
import com.speedmenu.tablet.domain.model.ThemeColors
import com.speedmenu.tablet.domain.model.ThemeTokens
import com.speedmenu.tablet.domain.repository.AppConfigSource
import timber.log.Timber

/**
 * Fonte mockada de AppConfig com múltiplos restaurantes.
 * Cada restaurante tem nome, cores e branding únicos.
 */
class MockAppConfigSource : AppConfigSource {
    
    private val mockConfigs: Map<String, AppConfig> = mapOf(
        "steakhouse" to createSteakhouseConfig(),
        "italian" to createItalianConfig(),
        "burger" to createBurgerConfig(),
        "japanese" to createJapaneseConfig(),
        "bar" to createBarConfig()
    )
    
    override suspend fun getConfig(restaurantId: String): AppConfig? {
        val config = mockConfigs[restaurantId]
        if (config == null) {
            Timber.w("MockAppConfigSource: restaurantId '$restaurantId' not found, using default")
        }
        return config
    }
    
    /**
     * Lista todos os restaurantIds disponíveis.
     */
    fun getAvailableRestaurantIds(): List<String> {
        return mockConfigs.keys.toList()
    }
    
    /**
     * Verifica se um restaurantId existe.
     */
    fun hasRestaurant(restaurantId: String): Boolean {
        return mockConfigs.containsKey(restaurantId)
    }
    
    // ==================== RESTAURANTES MOCKADOS ====================
    
    /**
     * Leo Steakhouse - Paleta quente (laranja/vermelho)
     */
    private fun createSteakhouseConfig(): AppConfig {
        return AppConfig(
            version = 1,
            branding = Branding(
                restaurantName = "Leo Steakhouse",
                logoUrl = null,
                logoUrlDark = null
            ),
            theme = ThemeTokens(
                light = ThemeColors(
                    primary = Color(0xFFD97706), // Laranja queimado
                    onPrimary = Color(0xFFFFFFFF),
                    secondary = Color(0xFFB45309), // Laranja escuro
                    onSecondary = Color(0xFFFFFFFF),
                    background = Color(0xFFFFFBFE),
                    onBackground = Color(0xFF1C1B1F),
                    surface = Color(0xFFFFFBFE),
                    onSurface = Color(0xFF1C1B1F),
                    error = Color(0xFFBA1A1A),
                    onError = Color(0xFFFFFFFF)
                ),
                dark = ThemeColors(
                    primary = Color(0xFFD97706), // Laranja queimado
                    onPrimary = Color(0xFFFFFFFF),
                    secondary = Color(0xFFF59E0B), // Laranja claro
                    onSecondary = Color(0xFF0B0F14),
                    background = Color(0xFF0B0F14), // Preto com tom quente
                    onBackground = Color(0xFFFFFFFF),
                    surface = Color(0xFF1A1F26), // Cinza escuro
                    onSurface = Color(0xFFE6E6E6),
                    error = Color(0xFFEF4444),
                    onError = Color(0xFFFFFFFF)
                )
            ),
            home = HomeConfig(
                carousel = listOf(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800",
                        action = CarouselAction.Category("steaks")
                    ),
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800",
                        action = CarouselAction.Product("ribeye")
                    )
                )
            )
        )
    }
    
    /**
     * Bella Pasta - Paleta italiana (vermelho/verde/branco)
     */
    private fun createItalianConfig(): AppConfig {
        return AppConfig(
            version = 1,
            branding = Branding(
                restaurantName = "Bella Pasta",
                logoUrl = null,
                logoUrlDark = null
            ),
            theme = ThemeTokens(
                light = ThemeColors(
                    primary = Color(0xFFDC2626), // Vermelho italiano
                    onPrimary = Color(0xFFFFFFFF),
                    secondary = Color(0xFF16A34A), // Verde italiano
                    onSecondary = Color(0xFFFFFFFF),
                    background = Color(0xFFFFFBFE),
                    onBackground = Color(0xFF1C1B1F),
                    surface = Color(0xFFFFFBFE),
                    onSurface = Color(0xFF1C1B1F),
                    error = Color(0xFFBA1A1A),
                    onError = Color(0xFFFFFFFF)
                ),
                dark = ThemeColors(
                    primary = Color(0xFFEF4444), // Vermelho mais claro
                    onPrimary = Color(0xFFFFFFFF),
                    secondary = Color(0xFF22C55E), // Verde mais claro
                    onSecondary = Color(0xFF0B0F14),
                    background = Color(0xFF0B0F14),
                    onBackground = Color(0xFFFFFFFF),
                    surface = Color(0xFF1A1F26),
                    onSurface = Color(0xFFE6E6E6),
                    error = Color(0xFFEF4444),
                    onError = Color(0xFFFFFFFF)
                )
            ),
            home = HomeConfig(
                carousel = listOf(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=800",
                        action = CarouselAction.Category("pasta")
                    ),
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800",
                        action = CarouselAction.Product("carbonara")
                    )
                )
            )
        )
    }
    
    /**
     * Urban Burger - Paleta escura/urbana (preto/amarelo)
     */
    private fun createBurgerConfig(): AppConfig {
        return AppConfig(
            version = 1,
            branding = Branding(
                restaurantName = "Urban Burger",
                logoUrl = null,
                logoUrlDark = null
            ),
            theme = ThemeTokens(
                light = ThemeColors(
                    primary = Color(0xFFFBBF24), // Amarelo urbano
                    onPrimary = Color(0xFF000000),
                    secondary = Color(0xFFFCD34D), // Amarelo claro
                    onSecondary = Color(0xFF000000),
                    background = Color(0xFFF9FAFB),
                    onBackground = Color(0xFF111827),
                    surface = Color(0xFFFFFFFF),
                    onSurface = Color(0xFF111827),
                    error = Color(0xFFDC2626),
                    onError = Color(0xFFFFFFFF)
                ),
                dark = ThemeColors(
                    primary = Color(0xFFFBBF24), // Amarelo urbano
                    onPrimary = Color(0xFF000000),
                    secondary = Color(0xFFFCD34D), // Amarelo claro
                    onSecondary = Color(0xFF000000),
                    background = Color(0xFF000000), // Preto puro
                    onBackground = Color(0xFFFFFFFF),
                    surface = Color(0xFF1F2937), // Cinza muito escuro
                    onSurface = Color(0xFFF9FAFB),
                    error = Color(0xFFEF4444),
                    onError = Color(0xFFFFFFFF)
                )
            ),
            home = HomeConfig(
                carousel = listOf(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800",
                        action = CarouselAction.Category("burgers")
                    ),
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=800",
                        action = CarouselAction.Product("classic-burger")
                    )
                )
            )
        )
    }
    
    /**
     * Sushi Zen - Paleta clean (branco/preto/vermelho)
     */
    private fun createJapaneseConfig(): AppConfig {
        return AppConfig(
            version = 1,
            branding = Branding(
                restaurantName = "Sushi Zen",
                logoUrl = null,
                logoUrlDark = null
            ),
            theme = ThemeTokens(
                light = ThemeColors(
                    primary = Color(0xFF000000), // Preto minimalista
                    onPrimary = Color(0xFFFFFFFF),
                    secondary = Color(0xFF6B7280), // Cinza
                    onSecondary = Color(0xFFFFFFFF),
                    background = Color(0xFFFFFFFF), // Branco puro
                    onBackground = Color(0xFF000000),
                    surface = Color(0xFFF9FAFB),
                    onSurface = Color(0xFF111827),
                    error = Color(0xFFDC2626), // Vermelho japonês
                    onError = Color(0xFFFFFFFF)
                ),
                dark = ThemeColors(
                    primary = Color(0xFFFFFFFF), // Branco sobre preto
                    onPrimary = Color(0xFF000000),
                    secondary = Color(0xFF9CA3AF), // Cinza claro
                    onSecondary = Color(0xFF000000),
                    background = Color(0xFF000000), // Preto puro
                    onBackground = Color(0xFFFFFFFF),
                    surface = Color(0xFF111827), // Cinza muito escuro
                    onSurface = Color(0xFFF9FAFB),
                    error = Color(0xFFEF4444), // Vermelho
                    onError = Color(0xFFFFFFFF)
                )
            ),
            home = HomeConfig(
                carousel = listOf(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800",
                        action = CarouselAction.Category("sushi")
                    ),
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=800",
                        action = CarouselAction.Product("sashimi-combo")
                    )
                )
            )
        )
    }
    
    /**
     * Moon Bar - Paleta noturna (roxo/azul)
     */
    private fun createBarConfig(): AppConfig {
        return AppConfig(
            version = 1,
            branding = Branding(
                restaurantName = "Moon Bar",
                logoUrl = null,
                logoUrlDark = null
            ),
            theme = ThemeTokens(
                light = ThemeColors(
                    primary = Color(0xFF7C3AED), // Roxo
                    onPrimary = Color(0xFFFFFFFF),
                    secondary = Color(0xFF3B82F6), // Azul
                    onSecondary = Color(0xFFFFFFFF),
                    background = Color(0xFFF3F4F6),
                    onBackground = Color(0xFF1F2937),
                    surface = Color(0xFFFFFFFF),
                    onSurface = Color(0xFF1F2937),
                    error = Color(0xFFDC2626),
                    onError = Color(0xFFFFFFFF)
                ),
                dark = ThemeColors(
                    primary = Color(0xFF8B5CF6), // Roxo mais claro
                    onPrimary = Color(0xFFFFFFFF),
                    secondary = Color(0xFF60A5FA), // Azul mais claro
                    onSecondary = Color(0xFF0B0F14),
                    background = Color(0xFF0B0F14), // Preto com tom azulado
                    onBackground = Color(0xFFFFFFFF),
                    surface = Color(0xFF1E1B4B), // Roxo muito escuro
                    onSurface = Color(0xFFE0E7FF), // Roxo muito claro
                    error = Color(0xFFEF4444),
                    onError = Color(0xFFFFFFFF)
                )
            ),
            home = HomeConfig(
                carousel = listOf(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1514362545857-3bc16c4c7d1b?w=800",
                        action = CarouselAction.Category("cocktails")
                    ),
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1551538827-9c037cb4f32a?w=800",
                        action = CarouselAction.Url("https://instagram.com/moonbar")
                    )
                )
            )
        )
    }
}

