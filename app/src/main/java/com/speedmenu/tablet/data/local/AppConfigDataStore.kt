package com.speedmenu.tablet.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.speedmenu.tablet.domain.model.AppConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 * DataStore para cache de AppConfig.
 * Armazena a configuração como JSON string junto com version e updatedAt.
 */
class AppConfigDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_config")
    
    // Chaves dinâmicas por restaurantId
    private fun configKey(restaurantId: String) = stringPreferencesKey("app_config_json_$restaurantId")
    private fun versionKey(restaurantId: String) = intPreferencesKey("app_config_version_$restaurantId")
    
    // Chave para último restaurante usado
    private val lastRestaurantIdKey = stringPreferencesKey("last_restaurant_id")
    
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    
    /**
     * Salva o AppConfig no cache para um restaurante específico.
     * 
     * @param restaurantId ID do restaurante
     * @param config Configuração a ser salva
     */
    suspend fun saveConfig(restaurantId: String, config: AppConfig) {
        try {
            context.dataStore.edit { preferences ->
                preferences[configKey(restaurantId)] = json.encodeToString(AppConfigDto.fromDomain(config))
                preferences[versionKey(restaurantId)] = config.version
                preferences[lastRestaurantIdKey] = restaurantId
            }
            Timber.d("AppConfigDataStore: saved config version=${config.version} for restaurant=$restaurantId")
        } catch (e: Exception) {
            Timber.e(e, "AppConfigDataStore: failed to save config")
        }
    }
    
    /**
     * Carrega o AppConfig do cache para um restaurante específico.
     * 
     * @param restaurantId ID do restaurante
     * @return AppConfig ou null se não existir
     */
    suspend fun loadConfig(restaurantId: String): AppConfig? {
        return try {
            val preferences = context.dataStore.data.first()
            val cachedJson = preferences[configKey(restaurantId)]
            val cachedVersion = preferences[versionKey(restaurantId)]
            
            if (cachedJson == null || cachedVersion == null) {
                Timber.d("AppConfigDataStore: no cached config found for restaurant=$restaurantId")
                return null
            }
            
            val dto = json.decodeFromString<AppConfigDto>(cachedJson)
            val parser: (String) -> androidx.compose.ui.graphics.Color? = { hex ->
                com.speedmenu.tablet.core.utils.ColorParser.parseColorOrNull(hex)
            }
            val config = AppConfig(
                version = dto.version,
                branding = dto.branding.toDomain(),
                theme = dto.theme.toDomain(parser),
                home = dto.home.toDomain()
            )
            
            Timber.d("AppConfigDataStore: loaded config version=${config.version} for restaurant=$restaurantId")
            config
        } catch (e: Exception) {
            Timber.e(e, "AppConfigDataStore: failed to load config for restaurant=$restaurantId")
            null
        }
    }
    
    /**
     * Obtém o último restaurantId usado.
     */
    suspend fun getLastRestaurantId(): String? {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[lastRestaurantIdKey]
        } catch (e: Exception) {
            Timber.e(e, "AppConfigDataStore: failed to get last restaurant id")
            null
        }
    }
    
    /**
     * Limpa o cache de um restaurante específico.
     */
    suspend fun clearCache(restaurantId: String) {
        try {
            context.dataStore.edit { preferences ->
                preferences.remove(configKey(restaurantId))
                preferences.remove(versionKey(restaurantId))
            }
            Timber.d("AppConfigDataStore: cache cleared for restaurant=$restaurantId")
        } catch (e: Exception) {
            Timber.e(e, "AppConfigDataStore: failed to clear cache for restaurant=$restaurantId")
        }
    }
}

/**
 * DTO para serialização JSON do AppConfig.
 * Usado apenas para persistência no DataStore.
 */
@Serializable
private data class AppConfigDto(
    val version: Int,
    val branding: BrandingDto,
    val theme: ThemeTokensDto,
    val home: HomeConfigDto
) {
    // Nota: toDomain() não é usado diretamente porque ThemeTokensDto.toDomain() precisa de parser
    // A conversão é feita manualmente em loadConfig()
    
    companion object {
        fun fromDomain(config: AppConfig): AppConfigDto {
            return AppConfigDto(
                version = config.version,
                branding = BrandingDto.fromDomain(config.branding),
                theme = ThemeTokensDto.fromDomain(config.theme),
                home = HomeConfigDto.fromDomain(config.home)
            )
        }
    }
}

@Serializable
private data class BrandingDto(
    val restaurantName: String,
    val logoUrl: String? = null,
    val logoUrlDark: String? = null
) {
    fun toDomain() = com.speedmenu.tablet.domain.model.Branding(
        restaurantName = restaurantName,
        logoUrl = logoUrl,
        logoUrlDark = logoUrlDark
    )
    
    companion object {
        fun fromDomain(branding: com.speedmenu.tablet.domain.model.Branding) = BrandingDto(
            restaurantName = branding.restaurantName,
            logoUrl = branding.logoUrl,
            logoUrlDark = branding.logoUrlDark
        )
    }
}

@Serializable
private data class ThemeColorsDto(
    val primary: String,
    val onPrimary: String,
    val secondary: String,
    val onSecondary: String,
    val background: String,
    val onBackground: String,
    val surface: String,
    val onSurface: String,
    val error: String,
    val onError: String
) {
    fun toDomain(parser: (String) -> androidx.compose.ui.graphics.Color?): com.speedmenu.tablet.domain.model.ThemeColors {
        return com.speedmenu.tablet.domain.model.ThemeColors(
            primary = parser(primary) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.Primary,
            onPrimary = parser(onPrimary) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.TextOnPrimary,
            secondary = parser(secondary) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.PrimaryLight,
            onSecondary = parser(onSecondary) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.TextOnPrimary,
            background = parser(background) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.BackgroundPrimary,
            onBackground = parser(onBackground) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.TextPrimary,
            surface = parser(surface) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.Surface,
            onSurface = parser(onSurface) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.TextPrimary,
            error = parser(error) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.Error,
            onError = parser(onError) ?: com.speedmenu.tablet.core.ui.theme.SpeedMenuColors.TextOnPrimary
        )
    }
    
    companion object {
        fun fromDomain(colors: com.speedmenu.tablet.domain.model.ThemeColors): ThemeColorsDto {
            fun colorToHex(color: androidx.compose.ui.graphics.Color): String {
                val r = (color.red * 255).toInt()
                val g = (color.green * 255).toInt()
                val b = (color.blue * 255).toInt()
                return "#%02X%02X%02X".format(r, g, b)
            }
            
            return ThemeColorsDto(
                primary = colorToHex(colors.primary),
                onPrimary = colorToHex(colors.onPrimary),
                secondary = colorToHex(colors.secondary),
                onSecondary = colorToHex(colors.onSecondary),
                background = colorToHex(colors.background),
                onBackground = colorToHex(colors.onBackground),
                surface = colorToHex(colors.surface),
                onSurface = colorToHex(colors.onSurface),
                error = colorToHex(colors.error),
                onError = colorToHex(colors.onError)
            )
        }
    }
}

@Serializable
private data class ThemeTokensDto(
    val light: ThemeColorsDto,
    val dark: ThemeColorsDto
) {
    fun toDomain(parser: (String) -> androidx.compose.ui.graphics.Color?): com.speedmenu.tablet.domain.model.ThemeTokens {
        return com.speedmenu.tablet.domain.model.ThemeTokens(
            light = light.toDomain(parser),
            dark = dark.toDomain(parser)
        )
    }
    
    companion object {
        fun fromDomain(tokens: com.speedmenu.tablet.domain.model.ThemeTokens) = ThemeTokensDto(
            light = ThemeColorsDto.fromDomain(tokens.light),
            dark = ThemeColorsDto.fromDomain(tokens.dark)
        )
    }
}

@Serializable
private data class CarouselActionDto(
    val type: String,
    val categoryId: String? = null,
    val productId: String? = null,
    val url: String? = null
) {
    fun toDomain(): com.speedmenu.tablet.domain.model.CarouselAction? {
        return when (type) {
            "CATEGORY" -> categoryId?.let { com.speedmenu.tablet.domain.model.CarouselAction.Category(it) }
            "PRODUCT" -> productId?.let { com.speedmenu.tablet.domain.model.CarouselAction.Product(it) }
            "URL" -> url?.let { com.speedmenu.tablet.domain.model.CarouselAction.Url(it) }
            else -> null
        }
    }
    
    companion object {
        fun fromDomain(action: com.speedmenu.tablet.domain.model.CarouselAction): CarouselActionDto {
            return when (action) {
                is com.speedmenu.tablet.domain.model.CarouselAction.Category -> CarouselActionDto(
                    type = "CATEGORY",
                    categoryId = action.categoryId
                )
                is com.speedmenu.tablet.domain.model.CarouselAction.Product -> CarouselActionDto(
                    type = "PRODUCT",
                    productId = action.productId
                )
                is com.speedmenu.tablet.domain.model.CarouselAction.Url -> CarouselActionDto(
                    type = "URL",
                    url = action.url
                )
            }
        }
    }
}

@Serializable
private data class CarouselItemDto(
    val imageUrl: String,
    val action: CarouselActionDto? = null
) {
    fun toDomain() = com.speedmenu.tablet.domain.model.CarouselItem(
        imageUrl = imageUrl,
        action = action?.toDomain()
    )
    
    companion object {
        fun fromDomain(item: com.speedmenu.tablet.domain.model.CarouselItem) = CarouselItemDto(
            imageUrl = item.imageUrl,
            action = item.action?.let { CarouselActionDto.fromDomain(it) }
        )
    }
}

@Serializable
private data class HomeConfigDto(
    val carousel: List<CarouselItemDto> = emptyList()
) {
    fun toDomain() = com.speedmenu.tablet.domain.model.HomeConfig(
        carousel = carousel.map { it.toDomain() }
    )
    
    companion object {
        fun fromDomain(config: com.speedmenu.tablet.domain.model.HomeConfig) = HomeConfigDto(
            carousel = config.carousel.map { CarouselItemDto.fromDomain(it) }
        )
    }
}

