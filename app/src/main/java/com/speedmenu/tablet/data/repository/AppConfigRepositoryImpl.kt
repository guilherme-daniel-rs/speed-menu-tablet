package com.speedmenu.tablet.data.repository

import android.content.Context
import com.speedmenu.tablet.data.config.DefaultAppConfig
import com.speedmenu.tablet.data.local.AppConfigDataStore
import com.speedmenu.tablet.domain.model.AppConfig
import com.speedmenu.tablet.domain.repository.AppConfigRepository
import com.speedmenu.tablet.domain.repository.AppConfigSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * Implementação do AppConfigRepository.
 * 
 * Fluxo de dados:
 * 1. Emite DefaultAppConfig imediatamente (para evitar "piscar")
 * 2. Tenta carregar do cache e emite se válido
 * 3. Busca do AppConfigSource (mock ou backend) e atualiza cache
 * 4. Emite config atualizada
 * 
 * Se qualquer etapa falhar, mantém o config atual.
 */
class AppConfigRepositoryImpl(
    private val context: Context,
    private val appConfigSource: AppConfigSource
) : AppConfigRepository {
    
    private val dataStore = AppConfigDataStore(context)
    
    override fun observeConfig(restaurantId: String): Flow<AppConfig> = flow {
        // 1. Emite DefaultAppConfig primeiro (evita "piscar")
        val defaultConfig = DefaultAppConfig.get()
        Timber.d("AppConfigRepository: emitting default config for restaurant=$restaurantId")
        emit(defaultConfig)
        
        // 2. Tenta carregar do cache
        val cachedConfig = dataStore.loadConfig(restaurantId)
        if (cachedConfig != null) {
            Timber.d("AppConfigRepository: emitting cached config version=${cachedConfig.version} for restaurant=$restaurantId")
            emit(cachedConfig)
        } else {
            Timber.d("AppConfigRepository: no cached config found for restaurant=$restaurantId")
        }
        
        // 3. Busca do AppConfigSource (mock ou backend)
        try {
            val sourceConfig = appConfigSource.getConfig(restaurantId)
            
            if (sourceConfig == null) {
                Timber.w("AppConfigRepository: no config found in source for restaurant=$restaurantId, using default")
                return@flow
            }
            
            // Só atualiza se a versão mudou (ou se não havia cache)
            if (cachedConfig == null || sourceConfig.version > cachedConfig.version) {
                Timber.d("AppConfigRepository: updating config from source version=${sourceConfig.version} for restaurant=$restaurantId")
                
                // Salva no cache
                dataStore.saveConfig(restaurantId, sourceConfig)
                
                // Emite config atualizada
                emit(sourceConfig)
            } else {
                Timber.d("AppConfigRepository: source config version (${sourceConfig.version}) <= cached (${cachedConfig.version}), skipping update")
            }
        } catch (e: Exception) {
            Timber.e(e, "AppConfigRepository: failed to fetch config from source for restaurant=$restaurantId")
            // Em caso de erro, mantém o config atual (cached ou default)
        }
    }
    
    override suspend fun refreshConfig(restaurantId: String) {
        try {
            Timber.d("AppConfigRepository: refreshing config for restaurant=$restaurantId")
            
            // Busca do AppConfigSource (mock ou backend)
            val sourceConfig = appConfigSource.getConfig(restaurantId)
            
            if (sourceConfig != null) {
                // Salva no cache (força atualização)
                dataStore.saveConfig(restaurantId, sourceConfig)
                Timber.d("AppConfigRepository: config refreshed version=${sourceConfig.version} for restaurant=$restaurantId")
            } else {
                Timber.w("AppConfigRepository: no config found in source for restaurant=$restaurantId")
            }
        } catch (e: Exception) {
            Timber.e(e, "AppConfigRepository: failed to refresh config for restaurant=$restaurantId")
        }
    }
}

