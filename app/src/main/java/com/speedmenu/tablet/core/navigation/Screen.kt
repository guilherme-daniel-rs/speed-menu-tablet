package com.speedmenu.tablet.core.navigation

/**
 * Sealed class que define todas as rotas de navegação da aplicação.
 * Cada objeto representa uma tela do aplicativo.
 */
sealed class Screen(val route: String) {
    /**
     * Tela de splash/boas-vindas
     */
    object Splash : Screen("splash")

    /**
     * Tela Home principal do aplicativo
     */
    object Home : Screen("home")

    /**
     * Tela de Produtos/Pratos de uma categoria
     */
    object Products : Screen("products/{categoryName}") {
        fun createRoute(categoryName: String) = "products/$categoryName"
    }

    /**
     * Tela de detalhes do prato (VerPratoScreen)
     */
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }

    /**
     * Tela placeholder para desenvolvimento futuro
     */
    object Placeholder : Screen("placeholder")
}

