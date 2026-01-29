package com.speedmenu.tablet.ui.screens.products

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.ProductListItem
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.core.ui.components.ItemAddedDialog
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.ui.screens.products.ProductDetailsBottomSheet
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.ui.screens.home.OrderFlowSidebar
import com.speedmenu.tablet.ui.screens.home.MenuMockupScenario
import com.speedmenu.tablet.ui.screens.home.getMenuMockup
import com.speedmenu.tablet.ui.screens.home.getSelectedCategoryIdForScenario

/**
 * Tela de listagem de produtos/pratos de uma categoria.
 * Layout de lista vertical (1 prato por linha) focado em leitura, descrição longa e decisão rápida.
 */
@Composable
fun ProductsScreen(
    categoryName: String,
    initialSelectedCategoryId: String? = null, // Categoria selecionada restaurada do savedStateHandle
    navController: androidx.navigation.NavController? = null, // NavController para observar savedStateHandle
    onNavigateToCart: () -> Unit = {},
    onNavigateToProductDetail: (String) -> Unit = {},
    onNavigateToCategory: (String) -> Unit = {}, // Navegação direta para outra categoria
    onNavigateToHome: () -> Unit = {}, // Callback para navegar para HOME
    cartItemCount: Int = 0 // Quantidade de itens no carrinho (vem do ViewModel)
) {
    // Estado para controlar bottom sheet
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
    // WaiterViewModel centralizado para gerenciar chamadas de garçom
    val waiterViewModel: WaiterViewModel = hiltViewModel()
    val waiterUiState by waiterViewModel.uiState.collectAsState()
    
    // Estado para controlar visibilidade do dialog de item adicionado
    var showItemAddedDialog by remember { mutableStateOf(false) }
    var productNameForDialog by remember { mutableStateOf<String?>(null) }
    
    // Observa savedStateHandle como StateFlow para detectar eventos de item adicionado
    val currentBackStackEntry = navController?.currentBackStackEntryAsState()?.value
    val eventId by remember(currentBackStackEntry) {
        currentBackStackEntry?.savedStateHandle?.getStateFlow<String>("itemAddedEventId", "")
            ?: MutableStateFlow("")
    }.collectAsState()
    
    val addedProductName by remember(currentBackStackEntry) {
        currentBackStackEntry?.savedStateHandle?.getStateFlow<String>("addedProductName", "")
            ?: MutableStateFlow("")
    }.collectAsState()
    
    // Dispara o dialog quando eventId mudar e NÃO for vazio
    LaunchedEffect(eventId) {
        if (eventId.isNotBlank()) {
            productNameForDialog = if (addedProductName.isNotBlank()) addedProductName else null
            showItemAddedDialog = true
        }
    }
    
    // Estados mockados (em produção viriam de um ViewModel)
    val isConnected = remember { true } // Mock: sempre conectado
    val tableNumber = remember { "17" } // Mock: mesa 17
    
    // ========== MOCKUP SCENARIO SELECTOR ==========
    // Altere este valor para testar diferentes cenários:
    // - FEW_TOPICS_MANY_CATEGORIES: Poucos tópicos, muitas categorias
    // - MANY_TOPICS_FEW_CATEGORIES: Muitos tópicos, poucas categorias
    // - LONG_CATEGORY_NAMES: Categorias com nomes longos
    // - SELECTED_IN_MIDDLE: Categoria selecionada no meio
    // - LONG_SCROLL: Menu com scroll longo
    val mockupScenario = MenuMockupScenario. LONG_SCROLL // Altere aqui para testar
    
    // Estado para categoria selecionada no sidebar
    // Usa initialSelectedCategoryId se fornecido (restaurado do savedStateHandle),
    // caso contrário usa a categoria atual ou a default do mockup
    var selectedCategoryId by remember(initialSelectedCategoryId) { 
        mutableStateOf(
            initialSelectedCategoryId 
                ?: categoryName.lowercase()
        )
    }
    
    // Atualiza selectedCategoryId quando categoryName muda (navegação entre categorias pelo menu lateral)
    // Mas só atualiza se não houver initialSelectedCategoryId (para preservar estado restaurado ao voltar do prato)
    LaunchedEffect(categoryName) {
        // Se não há initialSelectedCategoryId (não veio do savedStateHandle), atualiza com categoryName
        // Isso garante que navegação direta entre categorias funcione
        if (initialSelectedCategoryId == null && categoryName.lowercase() != selectedCategoryId) {
            selectedCategoryId = categoryName.lowercase()
        }
    }
    
    // Dados mockados de tópicos e categorias para o sidebar hierárquico
    val menuTopics = remember(mockupScenario) {
        getMenuMockup(mockupScenario)
    }
    
    // Dados mockados de produtos (usando imagens das categorias como placeholder)
    val products = remember(categoryName) {
        when (categoryName.lowercase()) {
            "entradas" -> listOf(
                Product("1", "Bruschetta Italiana", 24.90, R.drawable.entradas, "Pão artesanal com tomate, manjericão e azeite"),
                Product("2", "Carpaccio de Salmão", 32.50, R.drawable.entradas, "Salmão fresco com rúcula e parmesão"),
                Product("3", "Tartar de Atum", 28.90, R.drawable.entradas, "Atum fresco com abacate e molho especial"),
                Product("4", "Ceviche de Peixe", 29.90, R.drawable.entradas, "Peixe branco marinado com limão e cebola roxa"),
                Product("5", "Salada Caprese", 22.90, R.drawable.entradas, "Mozzarella, tomate e manjericão fresco"),
                Product("6", "Crostini de Queijo", 26.50, R.drawable.entradas, "Pão crocante com queijo brie e geleia"),
                Product("7", "Antepasto Italiano", 35.90, R.drawable.entradas, "Seleção de embutidos e queijos"),
                Product("8", "Tábua de Frios", 38.90, R.drawable.entradas, "Variedade de frios e queijos artesanais")
            )
            "pratos principais" -> listOf(
                Product("9", "Filé Mignon ao Molho", 68.90, R.drawable.pratos_principais, "Filé grelhado com molho especial"),
                Product("10", "Risotto de Camarão", 54.90, R.drawable.pratos_principais, "Arroz cremoso com camarões frescos"),
                Product("11", "Salmão Grelhado", 62.50, R.drawable.pratos_principais, "Salmão com legumes grelhados"),
                Product("12", "Penne ao Pesto", 42.90, R.drawable.pratos_principais, "Massa com molho pesto artesanal"),
                Product("13", "Frango à Parmegiana", 48.90, R.drawable.pratos_principais, "Frango empanado com molho de tomate"),
                Product("14", "Costela de Porco", 58.90, R.drawable.pratos_principais, "Costela assada com molho barbecue"),
                Product("15", "Lasanha Bolonhesa", 52.90, R.drawable.pratos_principais, "Lasanha tradicional italiana"),
                Product("16", "Peixe à Moda do Chef", 59.90, R.drawable.pratos_principais, "Peixe fresco com molho exclusivo")
            )
            "bebidas" -> listOf(
                Product("17", "Suco Natural Laranja", 12.90, R.drawable.bebidas, "Suco fresco de laranja"),
                Product("18", "Água com Gás", 6.90, R.drawable.bebidas, "Água mineral com gás"),
                Product("19", "Refrigerante", 8.90, R.drawable.bebidas, "Refrigerante gelado"),
                Product("20", "Cerveja Artesanal", 15.90, R.drawable.bebidas, "Cerveja artesanal local"),
                Product("21", "Vinho Tinto", 45.90, R.drawable.bebidas, "Vinho tinto selecionado"),
                Product("22", "Caipirinha", 18.90, R.drawable.bebidas, "Caipirinha tradicional"),
                Product("23", "Água Mineral", 5.90, R.drawable.bebidas, "Água mineral sem gás"),
                Product("24", "Suco Detox", 14.90, R.drawable.bebidas, "Suco verde detox")
            )
            "sobremesas" -> listOf(
                Product("25", "Brownie com Sorvete", 22.90, R.drawable.sobremesas, "Brownie quente com sorvete de creme"),
                Product("26", "Tiramisu", 24.90, R.drawable.sobremesas, "Tiramisu tradicional italiano"),
                Product("27", "Cheesecake de Frutas", 23.90, R.drawable.sobremesas, "Cheesecake com frutas vermelhas"),
                Product("28", "Pudim de Leite", 18.90, R.drawable.sobremesas, "Pudim caseiro com calda"),
                Product("29", "Mousse de Chocolate", 20.90, R.drawable.sobremesas, "Mousse cremosa de chocolate"),
                Product("30", "Petit Gateau", 26.90, R.drawable.sobremesas, "Bolinho quente com sorvete"),
                Product("31", "Torta de Limão", 21.90, R.drawable.sobremesas, "Torta refrescante de limão"),
                Product("32", "Sorvete Artesanal", 16.90, R.drawable.sobremesas, "Sorvete artesanal com cobertura")
            )
            else -> emptyList()
        }
    }

    val colorScheme = MaterialTheme.colorScheme
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        // ========== TOP ACTION BAR PADRONIZADA ==========
        // REGRA: Botão "Voltar" na tela de categoria navega para HOME
        AppTopBar(
            showBackButton = true,
            onBackClick = onNavigateToHome, // Voltar navega para HOME
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {
                waiterViewModel.requestWaiter("ProductsScreen")
            },
            screenName = "ProductsScreen"
        )
        
        // ========== CONTEÚDO PRINCIPAL ==========
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
        ) {
            // Sidebar fixa à esquerda (novo menu hierárquico)
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
            ) {
                OrderFlowSidebar(
                    topics = menuTopics,
                    selectedCategoryId = selectedCategoryId,
                    onCategoryClick = { categoryId ->
                        // Atualiza o estado local antes de navegar
                        selectedCategoryId = categoryId
                        // Navegação direta para a listagem da categoria selecionada
                        if (categoryId != categoryName.lowercase()) {
                            onNavigateToCategory(categoryId)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                // Borda sutil à direita
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(0.5.dp)
                        .fillMaxHeight()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    colorScheme.outlineVariant.copy(alpha = 0.08f),
                                    colorScheme.outlineVariant.copy(alpha = 0.15f),
                                    colorScheme.outlineVariant.copy(alpha = 0.08f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            // Área principal à direita
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorScheme.background,
                                colorScheme.background.copy(red = 0.08f, green = 0.10f, blue = 0.08f),
                                colorScheme.surface
                            )
                        )
                    )
                    .padding(top = 8.dp) // Reduzido de 18.dp para aproximar do menu superior
                    .padding(horizontal = 24.dp)
            ) {
                // ========== HEADER ==========
                ProductsHeader(
                    categoryName = categoryName,
                    productCount = products.size,
                    cartItemCount = cartItemCount,
                    onCartClick = onNavigateToCart,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // ========== LISTA VERTICAL DE PRODUTOS COM FADE-IN SUAVE ==========
                Crossfade(
                    targetState = categoryName,
                    animationSpec = tween(durationMillis = 220), // Duração entre 200-250ms
                    label = "products_content_fade"
                ) { _ ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        items(products) { product ->
                            // Badge emocional apenas para alguns produtos específicos
                            val badgeText = when (product.id) {
                                "1" -> "Mais pedido" // Bruschetta Italiana
                                "9" -> "Chef recomenda" // Filé Mignon ao Molho
                                "25" -> "Mais pedido" // Brownie com Sorvete
                                else -> null
                            }
                            
                            ProductListItem(
                                name = product.name,
                                description = product.shortDescription,
                                price = product.price,
                                imageResId = product.imageResId,
                                onClick = {
                                    // Navega para tela de detalhes do produto
                                    onNavigateToProductDetail(product.id)
                                },
                                onSelectClick = {
                                    // Navega para tela de detalhes do produto
                                    // O carrinho será gerenciado na tela de detalhes
                                    onNavigateToProductDetail(product.id)
                                },
                                badgeText = badgeText
                            )
                        }
                    }
                }
            }
        }
    }
    
    // ========== BOTTOM SHEET DE DETALHES ==========
    selectedProduct?.let { product ->
        ProductDetailsBottomSheet(
            product = product,
            onDismiss = { selectedProduct = null },
            onAddToCart = {
                selectedProduct = null
                // TODO: Implementar lógica real de carrinho quando necessário
                // Por enquanto, apenas fecha o bottom sheet
            }
        )
    }
    
    // ========== DIALOG DE GARÇOM CHAMADO ==========
    // Fora do scaffold para não ser afetado pelo overlay
    // Dialog de garçom chamado (gerenciado pelo WaiterViewModel)
    WaiterCalledDialog(
        visible = waiterUiState.showDialog,
        onDismiss = { waiterViewModel.dismissDialog() },
        onConfirm = { waiterViewModel.confirmWaiterCall() }
    )
    
    // Dialog de item adicionado ao carrinho
    if (showItemAddedDialog) {
        ItemAddedDialog(
            visible = showItemAddedDialog,
            productName = productNameForDialog,
            onDismiss = {
                showItemAddedDialog = false
                // Limpa o evento SOMENTE ao fechar o dialog
                currentBackStackEntry?.savedStateHandle?.apply {
                    set("itemAddedEventId", "")
                    set("addedProductName", "")
                }
            },
            onFinishOrder = {
                showItemAddedDialog = false
                // Limpa o evento antes de navegar
                currentBackStackEntry?.savedStateHandle?.apply {
                    set("itemAddedEventId", "")
                    set("addedProductName", "")
                }
                // Navega diretamente para a tela de scanner de QRCode no modo CHECKOUT
                navController?.navigate(com.speedmenu.tablet.core.navigation.Screen.QrScanner.createRoute("checkout"))
            },
            onContinueShopping = {
                showItemAddedDialog = false
                // Limpa o evento ao continuar comprando
                currentBackStackEntry?.savedStateHandle?.apply {
                    set("itemAddedEventId", "")
                    set("addedProductName", "")
                }
            }
        )
    }
}

