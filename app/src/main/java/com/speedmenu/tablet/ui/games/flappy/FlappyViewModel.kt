package com.speedmenu.tablet.ui.games.flappy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.speedmenu.tablet.data.preferences.GamePreferences
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel do jogo Flappy.
 * Gerencia o GamePreferences para persistência de scores.
 */
@HiltViewModel
class FlappyViewModel @Inject constructor(
    val gamePreferences: GamePreferences
) : ViewModel() {
    val flappyBestScore: StateFlow<Int> = gamePreferences.flappyBestScore.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
}

