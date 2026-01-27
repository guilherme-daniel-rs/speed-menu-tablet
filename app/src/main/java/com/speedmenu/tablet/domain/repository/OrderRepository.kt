package com.speedmenu.tablet.domain.repository

import com.speedmenu.tablet.domain.model.CartItem

/**
 * Interface do repositório de pedidos.
 * Define os contratos para acesso aos dados de pedidos.
 * A implementação concreta fica na camada de dados.
 */
interface OrderRepository {
    /**
     * Busca o pedido de uma comanda pelo código.
     * @param comandaCode Código da comanda (extraído do QRCode)
     * @return Lista de itens do pedido ou lista vazia se não encontrado
     */
    suspend fun getOrderByComandaCode(comandaCode: String): Result<List<CartItem>>
}

