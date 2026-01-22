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
     * Tela placeholder para desenvolvimento futuro
     */
    object Placeholder : Screen("placeholder")
}

