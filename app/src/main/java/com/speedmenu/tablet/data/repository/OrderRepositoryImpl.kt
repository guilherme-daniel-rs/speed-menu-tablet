package com.speedmenu.tablet.data.repository

import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.domain.model.CartItemOptions
import com.speedmenu.tablet.domain.repository.OrderRepository
import com.speedmenu.tablet.R
import javax.inject.Inject

/**
 * Implementação do repositório de pedidos.
 * Por enquanto, usa dados mockados. Em produção, buscará do backend.
 */
class OrderRepositoryImpl @Inject constructor() : OrderRepository {

    override suspend fun getOrderByComandaCode(comandaCode: String): Result<List<CartItem>> {
        return try {
            // Simula delay de rede
            kotlinx.coroutines.delay(500)
            
            // Mock: retorna pedido de exemplo baseado no código da comanda
            // Em produção, isso virá do backend
            val mockOrder = createMockOrder(comandaCode)
            
            Result.success(mockOrder)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun finalizeOrder(comandaCode: String, items: List<CartItem>): Result<Unit> {
        return try {
            // Simula delay de rede
            kotlinx.coroutines.delay(1000)
            
            // Mock: simula finalização bem-sucedida
            // Em produção, isso enviará os dados para o backend
            if (items.isEmpty()) {
                Result.failure(Exception("Carrinho vazio"))
            } else {
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Cria um pedido mockado para testes.
     * Em produção, isso será substituído por chamada ao backend.
     */
    private fun createMockOrder(comandaCode: String): List<CartItem> {
        // Se o código da comanda contém "VAZIO" ou "EMPTY", retorna lista vazia
        if (comandaCode.contains("VAZIO", ignoreCase = true) || 
            comandaCode.contains("EMPTY", ignoreCase = true)) {
            return emptyList()
        }
        
        // Caso contrário, retorna pedido de exemplo
        return listOf(
            CartItem(
                id = "item_1_${comandaCode}",
                productId = "file_mignon",
                name = "Filé Mignon ao Molho",
                price = 68.90,
                quantity = 1,
                imageResId = R.drawable.pratos_principais,
                options = CartItemOptions(
                    ingredients = mapOf("Batatas" to 1),
                    observations = "Bem passado"
                )
            ),
            CartItem(
                id = "item_2_${comandaCode}",
                productId = "agua",
                name = "Água Mineral",
                price = 5.00,
                quantity = 2,
                imageResId = R.drawable.bebidas,
                options = CartItemOptions()
            )
        )
    }
}

