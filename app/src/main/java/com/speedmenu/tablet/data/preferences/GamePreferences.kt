package com.speedmenu.tablet.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.gameDataStore: DataStore<Preferences> by preferencesDataStore(name = "game_preferences")

/**
 * Helper para persistir preferências dos jogos usando DataStore Preferences.
 * Gerencia scores e configurações dos jogos offline.
 */
@Singleton
class GamePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val FLAPPY_BEST_SCORE_KEY = intPreferencesKey("flappy_best_score")
    }

    /**
     * Flow do melhor score do Flappy.
     */
    val flappyBestScore: Flow<Int> = context.gameDataStore.data.map { preferences ->
        preferences[FLAPPY_BEST_SCORE_KEY] ?: 0
    }

    /**
     * Salva o melhor score do Flappy se for maior que o atual.
     * @param score Score a ser salvo
     * @return true se o score foi salvo (era maior que o anterior), false caso contrário
     */
    suspend fun saveFlappyBestScore(score: Int): Boolean {
        var wasSaved = false
        context.gameDataStore.edit { preferences ->
            val currentBest = preferences[FLAPPY_BEST_SCORE_KEY] ?: 0
            if (score > currentBest) {
                preferences[FLAPPY_BEST_SCORE_KEY] = score
                wasSaved = true
            }
        }
        return wasSaved
    }

    /**
     * Obtém o melhor score do Flappy de forma síncrona (útil para leitura inicial).
     * Para observação contínua, use o Flow flappyBestScore.
     */
    suspend fun getFlappyBestScore(): Int {
        return context.gameDataStore.data.map { preferences ->
            preferences[FLAPPY_BEST_SCORE_KEY] ?: 0
        }.first()
    }
}

