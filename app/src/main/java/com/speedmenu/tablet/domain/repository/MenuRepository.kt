package com.speedmenu.tablet.domain.repository

import com.speedmenu.tablet.domain.model.Category
import com.speedmenu.tablet.domain.model.MenuItem

/**
 * Interface do repositório de cardápio.
 * Define os contratos para acesso aos dados do menu.
 * A implementação concreta fica na camada de dados.
 */
interface MenuRepository {
    /**
     * Busca todos os itens do cardápio.
     * @return Lista de itens do menu
     */
    suspend fun getMenuItems(): Result<List<MenuItem>>

    /**
     * Busca um item específico do cardápio por ID.
     * @param itemId ID do item a ser buscado
     * @return Item do menu ou null se não encontrado
     */
    suspend fun getMenuItemById(itemId: String): Result<MenuItem?>

    /**
     * Busca todos os itens de uma categoria específica.
     * @param categoryId ID da categoria
     * @return Lista de itens da categoria
     */
    suspend fun getMenuItemsByCategory(categoryId: String): Result<List<MenuItem>>

    /**
     * Busca todas as categorias do cardápio.
     * @return Lista de categorias
     */
    suspend fun getCategories(): Result<List<Category>>
}

