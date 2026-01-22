package com.speedmenu.tablet.data.datasource.remote

import com.speedmenu.tablet.data.model.CategoryEntity
import com.speedmenu.tablet.data.model.MenuItemEntity

/**
 * Interface para acesso a dados remotos (API, servidor, etc.).
 * Define os contratos para operações de comunicação com o backend.
 */
interface RemoteDataSource {
    /**
     * Busca itens do menu do servidor.
     * @return Lista de itens do menu
     */
    suspend fun getMenuItems(): Result<List<MenuItemEntity>>

    /**
     * Busca um item específico por ID do servidor.
     * @param itemId ID do item
     * @return Item encontrado ou erro
     */
    suspend fun getMenuItemById(itemId: String): Result<MenuItemEntity>

    /**
     * Busca categorias do servidor.
     * @return Lista de categorias
     */
    suspend fun getCategories(): Result<List<CategoryEntity>>
}

