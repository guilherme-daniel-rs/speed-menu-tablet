package com.speedmenu.tablet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.speedmenu.tablet.ui.screens.home.HomeScreen
import com.speedmenu.tablet.ui.screens.home.MenuMockupScenario
import com.speedmenu.tablet.ui.screens.home.firstCategoryId
import com.speedmenu.tablet.ui.screens.home.getMenuMockup
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
                    // Navega diretamente para a primeira categoria do primeiro tópico
                    // Usa o mesmo mockup scenario padrão usado nas outras telas
                    val defaultScenario = MenuMockupScenario.LONG_SCROLL
                    val topics = getMenuMockup(defaultScenario)
                    val firstCategory = firstCategoryId(topics)
                    
                    if (firstCategory != null) {
                        navController.navigate(Screen.Products.createRoute(firstCategory)) {
                            // Limpa a pilha até a Home (inclusive) para garantir navegação limpa
                            popUpTo(Screen.Home.route) { inclusive = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    // Se não houver categorias, não navega (caso raro)
                    // Em produção, isso seria tratado com um estado de erro/empty state
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
            
            // Restaura categoria selecionada do savedStateHandle (se existir)
            // Isso preserva a categoria quando o usuário volta do prato
            val savedCategoryId = backStackEntry.savedStateHandle.get<String>("selectedCategoryId")
            val initialCategoryId = savedCategoryId ?: categoryName.lowercase()
            
            ProductsScreen(
                categoryName = categoryName,
                initialSelectedCategoryId = initialCategoryId, // Prioriza savedStateHandle para preservar estado ao voltar do prato
                onNavigateBack = {
                    // Não usado - TopActionBar usa onNavigateToHome
                },
                onNavigateToCart = {
                    // TODO: Implementar navegação para carrinho
                },
                onNavigateToProductDetail = { productId ->
                    // Salva a categoria selecionada antes de navegar para o prato
                    backStackEntry.savedStateHandle["selectedCategoryId"] = categoryName.lowercase()
                    // Navega para detalhes do prato SEM limpar stack
                    // Back natural retornará para a categoria atual
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateToCategory = { categoryId ->
                    // Salva a categoria selecionada antes de navegar
                    backStackEntry.savedStateHandle["selectedCategoryId"] = categoryId
                    // Navegação direta para outra categoria pelo menu lateral
                    // Substitui a tela Products atual na pilha para evitar acúmulo
                    // Mantém o resto da pilha (ex: Home) intacto
                    val currentRoute = navController.currentBackStackEntry?.destination?.route
                    if (currentRoute?.startsWith("products/") == true) {
                        // Se já estamos em uma tela Products, substitui ela
                        navController.navigate(Screen.Products.createRoute(categoryId)) {
                            popUpTo(currentRoute) { inclusive = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        // Se não estamos em Products (ex: vindo de Home), apenas navega
                        navController.navigate(Screen.Products.createRoute(categoryId)) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
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

