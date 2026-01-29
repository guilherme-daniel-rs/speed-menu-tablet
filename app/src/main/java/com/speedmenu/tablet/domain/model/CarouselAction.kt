package com.speedmenu.tablet.domain.model

/**
 * Ação que pode ser executada ao clicar em um item do carrossel.
 * Sealed class para garantir type-safety.
 */
sealed class CarouselAction {
    /**
     * Navegar para uma categoria específica.
     * @param categoryId ID da categoria (ex: "burgers", "desserts")
     */
    data class Category(val categoryId: String) : CarouselAction()
    
    /**
     * Navegar para um produto específico.
     * @param productId ID do produto (ex: "p123", "burger-001")
     */
    data class Product(val productId: String) : CarouselAction()
    
    /**
     * Abrir uma URL externa no navegador.
     * @param url URL completa (ex: "https://instagram.com/...")
     */
    data class Url(val url: String) : CarouselAction()
}

