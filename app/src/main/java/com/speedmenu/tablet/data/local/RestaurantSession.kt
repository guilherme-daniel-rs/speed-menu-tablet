package com.speedmenu.tablet.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
 * Gerencia a sessão do restaurante atual.
 * Armazena o restaurantId no DataStore.
 * Inicializa com o último restaurante usado (do AppConfigDataStore).
 * 
 * IMPORTANTE: Recebe o DataStore por injeção (singleton) para evitar múltiplas instâncias.
 */
class RestaurantSession(
    private val dataStore: DataStore<Preferences>,
    private val appConfigDataStore: AppConfigDataStore
) {
    
    private val restaurantIdKey = stringPreferencesKey("restaurant_id")
    
    /**
     * Inicializa o restaurantId com o último usado (do AppConfigDataStore).
     * Deve ser chamado após a criação da instância.
     */
    suspend fun initializeWithLastRestaurant() {
        try {
            val lastRestaurantId = appConfigDataStore.getLastRestaurantId()
            if (lastRestaurantId != null) {
                setRestaurantId(lastRestaurantId)
            }
        } catch (e: Exception) {
            Timber.e(e, "RestaurantSession: failed to initialize with last restaurant")
        }
    }
    
    /**
     * Define o restaurantId atual.
     */
    suspend fun setRestaurantId(restaurantId: String) {
        try {
            dataStore.edit { preferences ->
                preferences[restaurantIdKey] = restaurantId
            }
            Timber.d("RestaurantSession: set restaurantId=$restaurantId")
        } catch (e: Exception) {
            Timber.e(e, "RestaurantSession: failed to set restaurantId")
        }
    }
    
    /**
     * Observa o restaurantId atual.
     * Emite o primeiro restaurante mockado disponível se não houver nenhum definido.
     */
    fun observeRestaurantId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[restaurantIdKey] ?: getDefaultRestaurantId()
        }
    }
    
    /**
     * Obtém o restaurantId atual de forma síncrona (do cache).
     * Retorna o primeiro restaurante mockado se não houver nenhum definido.
     */
    suspend fun getRestaurantId(): String {
        return try {
            dataStore.data.first()[restaurantIdKey] ?: getDefaultRestaurantId()
        } catch (e: Exception) {
            Timber.e(e, "RestaurantSession: failed to get restaurantId")
            getDefaultRestaurantId()
        }
    }
    
    /**
     * Retorna o restaurantId padrão (primeiro restaurante mockado).
     */
    private fun getDefaultRestaurantId(): String {
        return "steakhouse" // Primeiro restaurante mockado
    }
}

