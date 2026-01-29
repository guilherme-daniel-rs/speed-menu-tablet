package com.speedmenu.tablet.domain.model

/**
 * Configuração completa do aplicativo para um restaurante (tenant).
 * Esta é a fonte única de verdade para todas as configurações remotas.
 * 
 * @param version Versão do config (usado para cache e atualizações incrementais)
 * @param branding Configurações de branding (nome, logo)
 * @param theme Esquemas de cores (light/dark)
 * @param home Configurações da tela Home (carrossel, etc.)
 */
data class AppConfig(
    val version: Int,
    val branding: Branding,
    val theme: ThemeTokens,
    val home: HomeConfig
)

