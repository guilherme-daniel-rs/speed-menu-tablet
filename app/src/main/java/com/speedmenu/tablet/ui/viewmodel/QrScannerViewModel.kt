package com.speedmenu.tablet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.domain.repository.OrderRepository
import com.speedmenu.tablet.ui.screens.qrscanner.QrScannerMode
import javax.inject.Inject

/**
 * Estado do scanner de QR Code.
 */
sealed class ScanState {
    object Idle : ScanState()
    object Scanning : ScanState()
    data class Success(val comandaCode: String) : ScanState()
    data class Error(val message: String) : ScanState()
}

/**
 * Estado da finalização do pedido (CHECKOUT apenas).
 */
sealed class FinalizationState {
    object Idle : FinalizationState()
    object Finalizing : FinalizationState()
    data class Success(val comandaCode: String) : FinalizationState()
    data class Error(val message: String) : FinalizationState()
}

/**
 * Estado da UI da tela de scanner de QR Code.
 */
data class QrScannerUiState(
    val mode: QrScannerMode = QrScannerMode.CHECKOUT,
    val scanState: ScanState = ScanState.Idle,
    val comandaCode: String? = null,
    // NOTA: cartItems não é mais usado no CHECKOUT - o carrinho vem diretamente do CartViewModel compartilhado
    // Mantido apenas para compatibilidade, mas sempre será emptyList()
    val cartItems: List<CartItem> = emptyList(),
    // Estado do pedido (VIEW_ORDER) - usado apenas neste modo
    val orderItems: List<CartItem> = emptyList(),
    val isLoadingOrder: Boolean = false,
    val orderError: String? = null,
    // Estado da finalização (CHECKOUT apenas)
    val finalizationState: FinalizationState = FinalizationState.Idle,
    // Estado de pedir conta (VIEW_ORDER apenas)
    val isRequestingBill: Boolean = false,
    val billRequested: Boolean = false
) {
    /**
     * Calcula o subtotal do pedido (VIEW_ORDER apenas).
     * No CHECKOUT, o total vem diretamente do CartViewModel.
     */
    val subtotal: Double
        get() = orderItems.sumOf { it.totalPrice }
    
    /**
     * Calcula o total do pedido (VIEW_ORDER apenas).
     * No CHECKOUT, o total vem diretamente do CartViewModel.
     */
    val total: Double
        get() = subtotal
    
    /**
     * Indica se o botão de finalizar pedido deve estar habilitado (apenas CHECKOUT com QRCode válido).
     * Nota: cartItems não é mais usado aqui, mas mantido para compatibilidade.
     * O carrinho real vem do CartViewModel compartilhado.
     */
    val canFinishOrder: Boolean
        get() = mode == QrScannerMode.CHECKOUT 
            && scanState is ScanState.Success
}

/**
 * ViewModel da tela de scanner de QR Code.
 * Gerencia o estado e lógica da tela de escaneamento com split view.
 */
@HiltViewModel
class QrScannerViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QrScannerUiState())
    val uiState: StateFlow<QrScannerUiState> = _uiState.asStateFlow()
    
    // Flag para proteger contra múltiplas finalizações simultâneas
    private var isFinalizing = false

    /**
     * Inicializa o ViewModel com o modo e itens do carrinho (se CHECKOUT).
     */
    fun initialize(mode: QrScannerMode, cartItems: List<CartItem> = emptyList()) {
        _uiState.value = _uiState.value.copy(
            mode = mode,
            cartItems = cartItems,
            scanState = ScanState.Idle
        )
    }

    /**
     * Atualiza o estado do scan.
     */
    fun updateScanState(state: ScanState) {
        _uiState.value = _uiState.value.copy(
            scanState = state,
            comandaCode = if (state is ScanState.Success) state.comandaCode else _uiState.value.comandaCode
        )
    }

    /**
     * Quando um QRCode válido é escaneado, carrega o pedido (VIEW_ORDER) ou finaliza automaticamente (CHECKOUT).
     * IMPORTANTE: VIEW_ORDER nunca chama finalizeCheckout.
     */
    fun onQrCodeScanned(comandaCode: String) {
        // Proteção: não processa se já está finalizando ou já finalizou com sucesso
        if (_uiState.value.finalizationState is FinalizationState.Success) {
            return
        }
        
        _uiState.value = _uiState.value.copy(
            scanState = ScanState.Success(comandaCode),
            comandaCode = comandaCode
        )
        
        // Comportamento diferente por modo (explícito, sem else genérico)
        when (_uiState.value.mode) {
            QrScannerMode.VIEW_ORDER -> {
                // VIEW_ORDER: apenas carrega o pedido
                loadOrder(comandaCode)
            }
            QrScannerMode.CHECKOUT -> {
                // CHECKOUT: finaliza automaticamente após escanear
                // Não precisa passar items aqui - será obtido do CartViewModel na tela
            }
        }
    }
    
    /**
     * Finaliza o pedido do checkout (CHECKOUT apenas).
     * @param items Lista de itens do carrinho a serem finalizados
     */
    fun finalizeCheckout(items: List<CartItem>) {
        // Proteção: não finaliza se já está finalizando ou já finalizou
        if (isFinalizing || _uiState.value.finalizationState is FinalizationState.Success) {
            return
        }
        
        // Proteção: só finaliza no modo CHECKOUT
        if (_uiState.value.mode != QrScannerMode.CHECKOUT) {
            return
        }
        
        val comandaCode = _uiState.value.comandaCode
        if (comandaCode == null) {
            _uiState.value = _uiState.value.copy(
                finalizationState = FinalizationState.Error("Código da comanda não encontrado")
            )
            return
        }
        
        if (items.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                finalizationState = FinalizationState.Error("Carrinho vazio")
            )
            return
        }
        
        isFinalizing = true
        _uiState.value = _uiState.value.copy(
            finalizationState = FinalizationState.Finalizing
        )
        
        viewModelScope.launch {
            orderRepository.finalizeOrder(comandaCode, items)
                .onSuccess {
                    isFinalizing = false
                    _uiState.value = _uiState.value.copy(
                        finalizationState = FinalizationState.Success(comandaCode)
                    )
                }
                .onFailure { exception ->
                    isFinalizing = false
                    _uiState.value = _uiState.value.copy(
                        finalizationState = FinalizationState.Error(
                            exception.message ?: "Erro ao finalizar pedido"
                        )
                    )
                }
        }
    }

    /**
     * Carrega o pedido de uma comanda (VIEW_ORDER).
     */
    private fun loadOrder(comandaCode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoadingOrder = true,
                orderError = null
            )
            
            orderRepository.getOrderByComandaCode(comandaCode)
                .onSuccess { items ->
                    _uiState.value = _uiState.value.copy(
                        isLoadingOrder = false,
                        orderItems = items,
                        orderError = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoadingOrder = false,
                        orderItems = emptyList(),
                        orderError = exception.message ?: "Erro ao carregar pedido"
                    )
                }
        }
    }

    /**
     * Reseta o scan para tentar novamente.
     */
    fun resetScan() {
        isFinalizing = false
        _uiState.value = _uiState.value.copy(
            scanState = ScanState.Idle,
            comandaCode = null,
            orderItems = emptyList(),
            orderError = null,
            finalizationState = FinalizationState.Idle,
            isRequestingBill = false,
            billRequested = false
        )
    }
    
    /**
     * Solicita a conta para a comanda (VIEW_ORDER apenas).
     */
    fun requestBill() {
        // Proteção: só permite solicitar conta no modo VIEW_ORDER
        if (_uiState.value.mode != QrScannerMode.VIEW_ORDER) {
            return
        }
        
        // Proteção: não permite solicitar se já foi solicitada
        if (_uiState.value.billRequested) {
            return
        }
        
        // Proteção: não permite solicitar se já está solicitando
        if (_uiState.value.isRequestingBill) {
            return
        }
        
        val comandaCode = _uiState.value.comandaCode
        if (comandaCode == null) {
            return
        }
        
        _uiState.value = _uiState.value.copy(
            isRequestingBill = true
        )
        
        viewModelScope.launch {
            // TODO: Chamar API para solicitar conta
            // Por enquanto, apenas simula sucesso após um delay
            delay(500) // Simula chamada de API
            
            _uiState.value = _uiState.value.copy(
                isRequestingBill = false,
                billRequested = true
            )
        }
    }
    
    /**
     * Tenta finalizar novamente após erro (CHECKOUT apenas).
     * Usa o comandaCode já escaneado, não exige novo scan.
     */
    fun retryFinalization(items: List<CartItem>) {
        finalizeCheckout(items)
    }

    /**
     * Atualiza itens do carrinho (CHECKOUT).
     * DEPRECATED: Não é mais usado. O carrinho vem diretamente do CartViewModel compartilhado.
     * Mantido apenas para compatibilidade.
     */
    @Deprecated("O carrinho agora vem diretamente do CartViewModel compartilhado")
    fun updateCartItems(items: List<CartItem>) {
        // Não faz nada - o carrinho vem do CartViewModel compartilhado
    }
}

