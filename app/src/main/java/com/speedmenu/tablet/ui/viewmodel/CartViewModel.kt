package com.speedmenu.tablet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.speedmenu.tablet.domain.model.CartItem

/**
 * Estado do carrinho de compras.
 */
data class CartState(
    val items: List<CartItem> = emptyList()
) {
    /**
     * Total de itens no carrinho (soma de todas as quantidades).
     */
    val totalItems: Int
        get() = items.sumOf { it.quantity }

    /**
     * Valor total do carrinho.
     */
    val totalValue: Double
        get() = items.sumOf { it.totalPrice }
}

/**
 * ViewModel global do carrinho de compras.
 * Gerencia o estado do carrinho de forma persistente entre todas as telas.
 * 
 * Este ViewModel é compartilhado entre todas as telas e mantém o estado
 * durante toda a navegação do usuário.
 */
@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    // ID único da instância para debug (verificar se há múltiplas instâncias)
    private val instanceId = System.identityHashCode(this)
    
    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()
    
    init {
        // Debug: Log da criação da instância (remover em produção)
        // android.util.Log.d("CartViewModel", "Instância criada: $instanceId")
    }

    /**
     * Adiciona um item ao carrinho.
     * Se o item já existir (mesmo productId e opções idênticas), incrementa a quantidade.
     * Caso contrário, adiciona como novo item com ID único.
     * 
     * DEBUG CHECKLIST:
     * ✅ Este método é realmente chamado?
     * ✅ O estado global do carrinho é mutado?
     * ✅ O componente do topo está observando o estado correto?
     * ✅ Existe mais de um carrinho instanciado? (verificar instanceId)
     * ✅ O store não está sendo recriado ao navegar de tela?
     */
    fun addItem(item: CartItem) {
        // Debug: Verificar se o método está sendo chamado
        // android.util.Log.d("CartViewModel", "[$instanceId] addItem() chamado: ${item.name}, qty=${item.quantity}")
        
        val previousTotalItems = _cartState.value.totalItems
        
        _cartState.update { state ->
            // Busca item existente com mesmo productId e opções idênticas
            val existingItemIndex = state.items.indexOfFirst { 
                it.productId == item.productId && it.options == item.options 
            }
            
            val newState = if (existingItemIndex >= 0) {
                // Item já existe, incrementa quantidade
                val existingItem = state.items[existingItemIndex]
                val updatedItems = state.items.toMutableList()
                updatedItems[existingItemIndex] = existingItem.copy(
                    quantity = existingItem.quantity + item.quantity
                )
                // Debug: Item incrementado
                // android.util.Log.d("CartViewModel", "[$instanceId] Item incrementado: ${existingItem.name}, qty: ${existingItem.quantity} -> ${updatedItems[existingItemIndex].quantity}")
                state.copy(items = updatedItems)
            } else {
                // Novo item, adiciona à lista
                // Gera ID único se não foi fornecido
                val finalItem = if (item.id.isEmpty()) {
                    item.copy(id = "${item.productId}_${System.currentTimeMillis()}")
                } else {
                    item
                }
                // Debug: Novo item adicionado
                // android.util.Log.d("CartViewModel", "[$instanceId] Novo item adicionado: ${finalItem.name}, qty=${finalItem.quantity}")
                state.copy(items = state.items + finalItem)
            }
            
            // Debug: Verificar mudança no estado
            val newTotalItems = newState.totalItems
            // android.util.Log.d("CartViewModel", "[$instanceId] Estado atualizado: totalItems $previousTotalItems -> $newTotalItems")
            
            newState
        }
        
        // Debug: Verificar se o estado foi realmente atualizado
        val finalTotalItems = _cartState.value.totalItems
        // android.util.Log.d("CartViewModel", "[$instanceId] Estado final verificado: totalItems=$finalTotalItems")
        
        // REGRA DE OURO: Se totalItems não aumentou, algo está errado
        if (finalTotalItems != previousTotalItems + item.quantity) {
            // android.util.Log.e("CartViewModel", "[$instanceId] ⚠️ ERRO: Estado não atualizado corretamente! Esperado: ${previousTotalItems + item.quantity}, Atual: $finalTotalItems")
        }
    }

    /**
     * Remove um item do carrinho pelo ID.
     */
    fun removeItem(itemId: String) {
        _cartState.update { state ->
            state.copy(items = state.items.filter { it.id != itemId })
        }
    }

    /**
     * Atualiza a quantidade de um item no carrinho.
     * Se a quantidade for 0 ou menor, remove o item.
     */
    fun updateItemQuantity(itemId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeItem(itemId)
            return
        }

        _cartState.update { state ->
            val updatedItems = state.items.map { item ->
                if (item.id == itemId) {
                    item.copy(quantity = newQuantity)
                } else {
                    item
                }
            }
            state.copy(items = updatedItems)
        }
    }

    /**
     * Limpa todo o carrinho.
     */
    fun clearCart() {
        _cartState.update { CartState() }
    }

    /**
     * Obtém o total de itens no carrinho.
     */
    fun getTotalItems(): Int = _cartState.value.totalItems

    /**
     * Obtém o valor total do carrinho.
     */
    fun getTotalValue(): Double = _cartState.value.totalValue
}

