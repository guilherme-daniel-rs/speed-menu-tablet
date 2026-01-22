package com.speedmenu.tablet.domain.model

/**
 * Modelo de domínio representando um item do cardápio.
 * Esta é a entidade de negócio, independente de como os dados são armazenados.
 */
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String? = null,
    val isAvailable: Boolean = true
)

