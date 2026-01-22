package com.speedmenu.tablet.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado da UI da tela de splash.
 */
data class SplashUiState(
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel da tela de splash.
 * Gerencia o estado e lógica da tela de boas-vindas.
 * TODO: Implementar lógica de inicialização quando necessário.
 */
@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        // TODO: Implementar lógica de inicialização
        // Por exemplo: verificar conexão, carregar dados iniciais, etc.
    }

    /**
     * Inicia o processo de carregamento inicial.
     */
    fun startLoading() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: Implementar lógica de carregamento
        }
    }
}

