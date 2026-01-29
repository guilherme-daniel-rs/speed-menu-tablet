package com.speedmenu.tablet.core.utils

import androidx.compose.ui.graphics.Color
import timber.log.Timber

/**
 * Utilitário para parsing de cores HEX.
 * Suporta formatos #RRGGBB e #AARRGGBB.
 */
object ColorParser {
    /**
     * Parse uma string HEX para Color, retornando null se inválida.
     * 
     * Formatos aceitos:
     * - #RRGGBB (ex: "#FF6A00")
     * - #AARRGGBB (ex: "#FFFF6A00")
     * 
     * @param hex String HEX com ou sem #
     * @return Color ou null se inválida
     */
    fun parseColorOrNull(hex: String?): Color? {
        if (hex.isNullOrBlank()) {
            Timber.w("ColorParser: hex is null or blank")
            return null
        }
        
        // Remove # se presente
        val cleanHex = hex.trim().removePrefix("#")
        
        // Valida formato
        if (!cleanHex.matches(Regex("^[0-9A-Fa-f]{6}$|^[0-9A-Fa-f]{8}$"))) {
            Timber.w("ColorParser: invalid hex format: $hex")
            return null
        }
        
        return try {
            when (cleanHex.length) {
                6 -> {
                    // #RRGGBB
                    val r = cleanHex.substring(0, 2).toInt(16)
                    val g = cleanHex.substring(2, 4).toInt(16)
                    val b = cleanHex.substring(4, 6).toInt(16)
                    Color(r, g, b)
                }
                8 -> {
                    // #AARRGGBB
                    val a = cleanHex.substring(0, 2).toInt(16)
                    val r = cleanHex.substring(2, 4).toInt(16)
                    val g = cleanHex.substring(4, 6).toInt(16)
                    val b = cleanHex.substring(6, 8).toInt(16)
                    Color(a, r, g, b)
                }
                else -> {
                    Timber.w("ColorParser: unexpected hex length: ${cleanHex.length}")
                    null
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "ColorParser: failed to parse hex: $hex")
            null
        }
    }
    
    /**
     * Parse uma string HEX para Color, usando fallback se inválida.
     * 
     * @param hex String HEX
     * @param fallback Color a ser usada se hex for inválida
     * @return Color parseada ou fallback
     */
    fun parseColorOrFallback(hex: String?, fallback: Color): Color {
        return parseColorOrNull(hex) ?: fallback
    }
}

