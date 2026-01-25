package com.speedmenu.tablet.core.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Design System de Cores do SpeedMenuTablet.
 * Paleta dark premium inspirada em aplicativos profissionais de restaurante.
 */
object SpeedMenuColors {
    // ==================== BACKGROUNDS ====================
    
    /**
     * Background principal do app.
     * Dark quase preto com leve tom azulado/esverdeado.
     */
    val BackgroundPrimary = Color(0xFF0F1419) // Quase preto com tom azulado
    
    /**
     * Background secundário (sidebar, containers).
     * Tom mais escuro que o principal.
     */
    val BackgroundSecondary = Color(0xFF0A0E12) // Mais escuro
    
    /**
     * Surface / Cards.
     * Cinza escuro com leve elevação visual.
     */
    val Surface = Color(0xFF1A1F26) // Cinza escuro
    val SurfaceElevated = Color(0xFF232830) // Cinza um pouco mais claro (elevação)
    
    // ==================== COR PRIMÁRIA ====================
    
    /**
     * Cor primária: Laranja queimado / âmbar (estilo gastronômico).
     * Usada para CTAs principais, destaques e estados ativos.
     */
    val Primary = Color(0xFFD97706) // Laranja queimado
    val PrimaryLight = Color(0xFFF59E0B) // Laranja mais claro
    val PrimaryDark = Color(0xFFB45309) // Laranja mais escuro
    val PrimaryContainer = Color(0xFF78350F) // Container escuro para primária
    
    // ==================== TEXTOS ====================
    
    /**
     * Texto primário (títulos, conteúdo principal).
     */
    val TextPrimary = Color(0xFFFFFFFF) // Branco
    
    /**
     * Texto secundário (subtítulos, descrições).
     */
    val TextSecondary = Color(0xFFD1D5DB) // Cinza claro
    
    /**
     * Texto terciário (hints, labels discretos).
     */
    val TextTertiary = Color(0xFF9CA3AF) // Cinza médio
    
    /**
     * Texto em fundo primário.
     */
    val TextOnPrimary = Color(0xFFFFFFFF) // Branco
    
    /**
     * Cor para elementos sobre Surface (equivalente a OnSurface no Material Design).
     * Em tema dark, geralmente é branco ou cinza muito claro.
     */
    val OnSurface = Color(0xFFFFFFFF) // Branco (equivalente a TextPrimary em tema dark)
    
    // ==================== ESTADOS ====================
    
    /**
     * Hover / Pressed (clareamento leve).
     */
    val Hover = Color(0x1AFFFFFF) // Branco 10% opacidade
    
    /**
     * Disabled (opacidade reduzida).
     */
    val Disabled = Color(0xFF4B5563) // Cinza escuro
    
    /**
     * Success / Confirmação.
     */
    val Success = Color(0xFF10B981) // Verde
    
    /**
     * Error / Aviso.
     */
    val Error = Color(0xFFEF4444) // Vermelho
    
    /**
     * Warning.
     */
    val Warning = Color(0xFFF59E0B) // Amarelo/laranja
    
    // ==================== BORDAS E DIVISORES ====================
    
    /**
     * Borda sutil para separação.
     */
    val Border = Color(0xFF374151) // Cinza médio-escuro
    
    /**
     * Borda mais discreta.
     */
    val BorderSubtle = Color(0xFF1F2937) // Cinza muito escuro
    
    // ==================== OVERLAYS ====================
    
    /**
     * Overlay escuro para modais, dialogs.
     */
    val Overlay = Color(0xCC000000) // Preto 80% opacidade
    
    /**
     * Overlay claro para destaque.
     */
    val OverlayLight = Color(0x33000000) // Preto 20% opacidade
}

