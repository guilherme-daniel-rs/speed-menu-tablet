package com.speedmenu.tablet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado da UI para chamadas de garçom.
 */
data class WaiterUiState(
    val showDialog: Boolean = false,
    val lastRequestScreen: String? = null
)

/**
 * ViewModel global para gerenciar chamadas de garçom.
 * Centraliza a lógica de chamar garçom em todas as telas.
 * 
 * Este ViewModel é compartilhado entre todas as telas e mantém o estado
 * durante toda a navegação do usuário.
 */
@HiltViewModel
class WaiterViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(WaiterUiState())
    val uiState: StateFlow<WaiterUiState> = _uiState.asStateFlow()

    /**
     * Solicita chamada de garçom.
     * 
     * @param screenName Nome da tela que está chamando (para logs)
     */
    fun requestWaiter(screenName: String = "Unknown") {
        Log.d("Waiter", "🚀 requestWaiter() chamado - screen=$screenName")
        
        // Atualiza o estado diretamente (não precisa de coroutine para StateFlow)
        _uiState.update { currentState ->
            val newState = currentState.copy(
                showDialog = true,
                lastRequestScreen = screenName
            )
            Log.d("Waiter", "✅ Estado atualizado - showDialog=${newState.showDialog}, screen=${newState.lastRequestScreen}")
            newState
        }
        
        // TODO: Implementar chamada de rede/API quando necessário
        // Exemplo:
        // viewModelScope.launch {
        //     waiterRepository.callWaiter(tableNumber)
        // }
    }

    /**
     * Fecha o dialog de garçom chamado.
     */
    fun dismissDialog() {
        Log.d("Waiter", "✅ Dialog de garçom fechado")
        _uiState.update { currentState ->
            currentState.copy(showDialog = false)
        }
    }

    /**
     * Confirma a chamada de garçom (quando o usuário clica em "Ok, entendi").
     */
    fun confirmWaiterCall() {
        Log.d("Waiter", "✅ Chamada de garçom confirmada pelo usuário")
        dismissDialog()
    }
}

