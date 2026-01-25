package com.speedmenu.tablet.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.speedmenu.tablet.ui.screens.categories.CategoriesScreen
import com.speedmenu.tablet.ui.screens.home.HomeScreen
import com.speedmenu.tablet.ui.screens.placeholder.PlaceholderScreen
import com.speedmenu.tablet.ui.screens.productdetail.VerPratoScreen
import com.speedmenu.tablet.ui.screens.products.ProductsScreen
import com.speedmenu.tablet.ui.screens.splash.SplashScreen

/**
 * Configuração do grafo de navegação da aplicação.
 * Define todas as rotas e suas respectivas telas.
 *
 * @param navController Controlador de navegação do Compose
 * @param startDestination Rota inicial da aplicação (padrão: Home)
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToPlaceholder = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCategories = {
                    navController.navigate(Screen.Categories.route)
                }
            )
        }

        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCategory = { categoryId ->
                    // Navega para tela de produtos da categoria
                    navController.navigate(Screen.Products.createRoute(categoryId))
                }
            )
        }

        composable(
            route = Screen.Products.route,
            arguments = listOf(
                navArgument("categoryName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            ProductsScreen(
                categoryName = categoryName,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCart = {
                    // TODO: Implementar navegação para carrinho
                },
                onNavigateToProductDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateToCategory = { categoryId ->
                    // Navegação direta para outra categoria
                    navController.navigate(Screen.Products.createRoute(categoryId)) {
                        // Substitui a tela atual na pilha para evitar acúmulo
                        popUpTo(Screen.Categories.route) { inclusive = false }
                    }
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            // Mock de dados do produto (em produção viria de um ViewModel/Repository)
            VerPratoScreen(
                productId = productId,
                productName = "Filé Mignon ao Molho",
                productCategory = "Pratos Principais",
                productPrice = 68.90,
                productImageResId = com.speedmenu.tablet.R.drawable.pratos_principais,
                productDescription = "Filé grelhado com molho especial e acompanhamentos",
                ingredients = listOf("Filé mignon", "Molho especial", "Batatas", "Legumes", "Ervas"),
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onNavigateToCart = {
                    // TODO: Implementar navegação para carrinho
                },
                onAddToCart = {
                    // TODO: Implementar lógica de adicionar ao carrinho
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Placeholder.route) {
            PlaceholderScreen()
        }
    }
}

