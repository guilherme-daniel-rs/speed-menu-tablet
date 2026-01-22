package com.speedmenu.tablet.ui.screens.products

/**
 * Modelo de dados para um produto/prato do cardápio.
 */
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageResId: Int,
    val shortDescription: String
)

