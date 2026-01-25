package com.speedmenu.tablet.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.CategoryCard
import com.speedmenu.tablet.core.ui.components.OrderFlowScaffold
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.ui.screens.home.OrderFlowSidebar
import com.speedmenu.tablet.ui.screens.home.MenuTopic
import com.speedmenu.tablet.ui.screens.home.MenuCategory
import com.speedmenu.tablet.ui.screens.home.MenuMockupScenario
import com.speedmenu.tablet.ui.screens.home.getMenuMockup
import com.speedmenu.tablet.ui.screens.home.getSelectedCategoryIdForScenario

/**
 * Dados mockados de categoria.
 */
data class CategoryData(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val imageResId: Int
)

/**
 * Tela de Categorias do Cardápio.
 * Grid de categorias com filtros no topo.
 */
@Composable
fun CategoriesScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToCategory: (String) -> Unit = {},
    onNavigateToHome: () -> Unit = {} // Callback para navegar para HOME
) {
    // Estado para controlar visibilidade do dialog de garçom
    var showWaiterCalledDialog by remember { mutableStateOf(false) }
    
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
    var selectedCategoryId by remember { 
        mutableStateOf<String?>(getSelectedCategoryIdForScenario(mockupScenario))
    }
    
    // Dados mockados de tópicos e categorias para o sidebar hierárquico
    val menuTopics = remember(mockupScenario) {
        getMenuMockup(mockupScenario)
    }
    
    // Dados mockados de categorias
    val categories = remember {
        listOf(
            CategoryData(
                id = "entradas",
                title = "Entradas",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.Restaurant,
                imageResId = R.drawable.entradas
            ),
            CategoryData(
                id = "pratos",
                title = "Pratos Principais",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.LocalDining,
                imageResId = R.drawable.pratos_principais
            ),
            CategoryData(
                id = "bebidas",
                title = "Bebidas",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.LocalBar,
                imageResId = R.drawable.bebidas
            ),
            CategoryData(
                id = "sobremesas",
                title = "Sobremesas",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.Cake,
                imageResId = R.drawable.sobremesas
            )
        )
    }

    // OrderFlowScaffold envolve toda a tela para garantir posicionamento consistente do status pill
    OrderFlowScaffold(
        isConnected = isConnected,
        tableNumber = tableNumber,
        onCallWaiterClick = {
            showWaiterCalledDialog = true
        },
        onCartClick = {
            // TODO: Implementar navegação para carrinho
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(SpeedMenuColors.BackgroundPrimary)
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
                        // Navegação direta para a listagem da categoria
                        selectedCategoryId = categoryId
                        onNavigateToCategory(categoryId)
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
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    SpeedMenuColors.BorderSubtle.copy(alpha = 0.08f),
                                    SpeedMenuColors.BorderSubtle.copy(alpha = 0.15f),
                                    SpeedMenuColors.BorderSubtle.copy(alpha = 0.08f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            // Área principal à direita (sem menu superior de tabs - navegação apenas via sidebar)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                SpeedMenuColors.BackgroundPrimary,
                                SpeedMenuColors.BackgroundPrimary.copy(red = 0.08f, green = 0.10f, blue = 0.08f),
                                SpeedMenuColors.BackgroundSecondary
                            )
                        )
                    )
                    .padding(horizontal = 40.dp, vertical = 32.dp)
            ) {
                // ========== CONTEÚDO PRINCIPAL (Grid de categorias) ==========
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 colunas para tablet
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    items(categories) { category ->
                        CategoryCard(
                            title = category.title,
                            subtitle = category.subtitle,
                            description = category.description,
                            icon = category.icon,
                            imageResId = category.imageResId,
                            onClick = {
                                // Navega para tela de produtos da categoria
                                onNavigateToCategory(category.title)
                            },
                            onViewItemsClick = {
                                // Navega para tela de produtos da categoria
                                onNavigateToCategory(category.title)
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Dialog de garçom chamado (fora do scaffold para não ser afetado pelo overlay)
    if (showWaiterCalledDialog) {
        WaiterCalledDialog(
            visible = showWaiterCalledDialog,
            onDismiss = { showWaiterCalledDialog = false },
            onConfirm = { showWaiterCalledDialog = false }
        )
    }
}

