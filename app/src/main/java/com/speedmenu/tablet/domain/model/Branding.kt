package com.speedmenu.tablet.domain.model

/**
 * Configurações de branding do restaurante.
 * Contém informações visuais de identidade.
 */
data class Branding(
    /**
     * Nome do restaurante.
     * Exibido no TopBar em todo o app.
     */
    val restaurantName: String,
    
    /**
     * URL do logo do restaurante (modo claro).
     * Usado no Drawer/Sidebar.
     * Pode ser null/vazio para usar placeholder.
     */
    val logoUrl: String? = null,
    
    /**
     * URL do logo do restaurante (modo escuro).
     * Usado no Drawer/Sidebar quando em dark mode.
     * Se null, usa logoUrl.
     */
    val logoUrlDark: String? = null
)

