package com.speedmenu.tablet.domain.model

/**
 * Item do carrinho de compras.
 * Representa um produto adicionado ao pedido com suas personalizações.
 */
data class CartItem(
    val id: String,
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val options: CartItemOptions = CartItemOptions()
) {
    /**
     * Calcula o preço total do item (preço unitário * quantidade).
     */
    val totalPrice: Double
        get() = price * quantity
}

/**
 * Opções e personalizações do item do carrinho.
 */
data class CartItemOptions(
    val ingredients: Map<String, Int> = emptyMap(), // Nome do ingrediente -> quantidade
    val observations: String = ""
)

