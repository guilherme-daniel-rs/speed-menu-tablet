package com.speedmenu.tablet.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Cake
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.CategoryCard
import com.speedmenu.tablet.core.ui.components.FilterChip
import com.speedmenu.tablet.core.ui.components.SidebarMenuItem
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.ui.screens.home.Sidebar

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
    onNavigateToCategory: (String) -> Unit = {}
) {
    // Estado para filtro selecionado
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    
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
    
    // Filtros mockados
    val filters = remember {
        listOf(
            "Favoritos da casa",
            "Novidades",
            "Menu degustação",
            "Opções veganas",
            "Sem glúten"
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Sidebar fixa à esquerda (reutilizada da Home)
        Box(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
        ) {
            Sidebar(
                modifier = Modifier.fillMaxSize(),
                isVisible = true,
                onStartOrderClick = {
                    // Já estamos na tela de categorias, não precisa navegar
                }
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

        // Área principal à direita
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
            // ========== FILTROS NO TOPO (Scroll Horizontal) ==========
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        text = filter,
                        onClick = {
                            selectedFilter = if (selectedFilter == filter) null else filter
                        },
                        isSelected = selectedFilter == filter
                    )
                }
            }

            // ========== GRID DE CATEGORIAS ==========
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
                            // TODO: Implementar navegação para itens da categoria
                            onNavigateToCategory(category.id)
                        },
                        onViewItemsClick = {
                            // TODO: Implementar navegação para itens da categoria
                            onNavigateToCategory(category.id)
                        }
                    )
                }
            }
        }
    }
}

