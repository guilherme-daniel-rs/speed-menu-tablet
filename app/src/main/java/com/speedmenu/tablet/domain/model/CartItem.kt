package com.speedmenu.tablet.domain.model

/**
 * Tipo de atendimento do item do carrinho.
 */
enum class FulfillmentType {
    /** Para comer no restaurante */
    DINE_IN,
    /** Para viagem */
    TAKEAWAY
}

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
    val imageResId: Int = 0, // ID do recurso de imagem do prato
    val options: CartItemOptions = CartItemOptions(),
    val fulfillmentType: FulfillmentType = FulfillmentType.DINE_IN // Default: no restaurante
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

