package com.speedmenu.tablet.domain.repository

import com.speedmenu.tablet.domain.model.AppConfig
import kotlinx.coroutines.flow.Flow

/**
 * Repository para gerenciar AppConfig.
 * Abstrai a fonte de dados (mock, cache, backend futuro).
 */
interface AppConfigRepository {
    /**
     * Observa a configuração do app para um restaurante.
     * O fluxo emite:
     * 1. DefaultAppConfig (inicial)
     * 2. Cache (se disponível)
     * 3. Config atualizada (mock/backend)
     * 
     * @param restaurantId ID do restaurante
     * @return Flow de AppConfig
     */
    fun observeConfig(restaurantId: String): Flow<AppConfig>
    
    /**
     * Atualiza a configuração manualmente (útil para refresh).
     * 
     * @param restaurantId ID do restaurante
     */
    suspend fun refreshConfig(restaurantId: String)
}

