package com.speedmenu.tablet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.domain.repository.OrderRepository
import javax.inject.Inject

/**
 * Estado da UI da tela de visualização de pedido.
 */
data class ViewOrderUiState(
    val isLoading: Boolean = false,
    val items: List<CartItem> = emptyList(),
    val error: String? = null,
    val comandaCode: String? = null
) {
    /**
     * Indica se o pedido está vazio (sem itens).
     */
    val isEmpty: Boolean
        get() = !isLoading && items.isEmpty() && error == null
    
    /**
     * Calcula o subtotal do pedido.
     */
    val subtotal: Double
        get() = items.sumOf { it.totalPrice }
    
    /**
     * Calcula o total do pedido (por enquanto igual ao subtotal).
     */
    val total: Double
        get() = subtotal
}

/**
 * ViewModel da tela de visualização de pedido.
 * Gerencia o estado e lógica da tela read-only de visualização de pedido por comanda.
 */
@HiltViewModel
class ViewOrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ViewOrderUiState())
    val uiState: StateFlow<ViewOrderUiState> = _uiState.asStateFlow()

    /**
     * Carrega o pedido de uma comanda pelo código.
     * @param comandaCode Código da comanda extraído do QRCode
     */
    fun loadOrder(comandaCode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                comandaCode = comandaCode
            )
            
            orderRepository.getOrderByComandaCode(comandaCode)
                .onSuccess { items ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        items = items,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        items = emptyList(),
                        error = exception.message ?: "Erro ao carregar pedido"
                    )
                }
        }
    }
}

