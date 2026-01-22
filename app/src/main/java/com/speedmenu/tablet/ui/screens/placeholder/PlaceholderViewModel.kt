package com.speedmenu.tablet.ui.screens.placeholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Estado da UI da tela placeholder.
 */
data class PlaceholderUiState(
    val message: String = "Placeholder"
)

/**
 * ViewModel da tela placeholder.
 * Estrutura base para desenvolvimento futuro.
 */
@HiltViewModel
class PlaceholderViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PlaceholderUiState())
    val uiState: StateFlow<PlaceholderUiState> = _uiState.asStateFlow()

    // TODO: Implementar lógica de negócio quando necessário
}

