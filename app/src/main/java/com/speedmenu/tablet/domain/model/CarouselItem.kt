package com.speedmenu.tablet.domain.model

/**
 * Item do carrossel da Home.
 * Representa uma imagem com uma ação associada.
 */
data class CarouselItem(
    /**
     * URL da imagem do carrossel.
     * Pode ser uma URL remota ou local.
     */
    val imageUrl: String,
    
    /**
     * Ação a ser executada quando o item é clicado.
     * Pode ser null se o item não tiver ação.
     */
    val action: CarouselAction?
)

