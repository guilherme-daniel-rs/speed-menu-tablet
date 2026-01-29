package com.speedmenu.tablet.domain.repository

import com.speedmenu.tablet.domain.model.AppConfig

/**
 * Fonte de configuração do app (mock ou backend real).
 * Abstrai a origem dos dados para facilitar troca entre mock e backend.
 */
interface AppConfigSource {
    /**
     * Obtém a configuração do app para um restaurante específico.
     * 
     * @param restaurantId ID do restaurante
     * @return AppConfig ou null se não encontrado
     */
    suspend fun getConfig(restaurantId: String): AppConfig?
}

