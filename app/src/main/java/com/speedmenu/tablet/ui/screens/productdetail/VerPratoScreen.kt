package com.speedmenu.tablet.ui.screens.productdetail

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.components.AccordionSection
import com.speedmenu.tablet.core.ui.components.HeroImage
import com.speedmenu.tablet.core.ui.components.IngredientQuantityItem
import com.speedmenu.tablet.core.ui.components.MinimalTextField
import com.speedmenu.tablet.core.ui.components.OrderFlowScaffold
import com.speedmenu.tablet.core.ui.components.PrimaryCTA
import com.speedmenu.tablet.core.ui.components.PriceHeader
import com.speedmenu.tablet.core.ui.components.QuantityStepper
import com.speedmenu.tablet.core.ui.components.RemoveBaseIngredientDialog
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Tela de detalhes do prato (VerPratoScreen).
 * Layout minimalista com 2 colunas: imagem à esquerda, personalização à direita.
 */
@Composable
fun VerPratoScreen(
    productId: String,
    productName: String,
    productCategory: String,
    productPrice: Double,
    productImageResId: Int,
    productDescription: String,
    ingredients: List<String>,
    onNavigateBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    // Estados locais
    var quantity by remember { mutableStateOf(1) }
    var isIngredientsExpanded by remember { mutableStateOf(false) }
    var isObservationsExpanded by remember { mutableStateOf(false) }
    var observationsText by remember { mutableStateOf("") }
    
    // Estado para dialog de confirmação de remoção de ingrediente base
    var showRemoveBaseIngredientDialog by remember { mutableStateOf(false) }
    var pendingIngredientIndex by remember { mutableStateOf<Int?>(null) }
    
    // Estado de quantidades dos ingredientes
    data class IngredientQuantity(
        val name: String,
        val isBase: Boolean, // true = ingrediente base (requer confirmação ao remover de 1 para 0), false = opcional
        var quantity: Int
    )
    
    val ingredientQuantities = remember {
        mutableStateListOf(
            IngredientQuantity("Filé mignon", true, 1), // Base: mínimo 1
            IngredientQuantity("Batatas", true, 1), // Base: mínimo 1
            IngredientQuantity("Legumes", true, 1), // Base: mínimo 1
            IngredientQuantity("Cebola", false, 0), // Opcional: mínimo 0
            IngredientQuantity("Alho", false, 0), // Opcional: mínimo 0
            IngredientQuantity("Pimenta", false, 0) // Opcional: mínimo 0
        )
    }
    
    // Mock de carrinho
    val cartItemCount = remember { 0 }
    
    // Estado para controlar visibilidade do dialog de garçom
    var showWaiterCalledDialog by remember { mutableStateOf(false) }
    
    // Estados mockados (em produção viriam de um ViewModel)
    val isConnected = remember { true } // Mock: sempre conectado
    val tableNumber = remember { "17" } // Mock: mesa 17
    
    OrderFlowScaffold(
        isConnected = isConnected,
        tableNumber = tableNumber,
        onCallWaiterClick = {
            showWaiterCalledDialog = true
        },
        topLeftContent = {
            // Breadcrumb "← Menu / Início" no topo esquerdo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // "← Menu" (clicável)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable(onClick = onNavigateBack)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SpeedMenuColors.TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 16.sp
                    )
                }
                
                // Separador "/"
                Text(
                    text = "/",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextTertiary.copy(alpha = 0.5f),
                    fontSize = 16.sp
                )
                
                // "Início" (clicável, menor opacidade)
                Text(
                    text = "Início",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextTertiary.copy(alpha = 0.6f),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable(onClick = onNavigateToHome)
                        .padding(horizontal = 8.dp, vertical = 4.dp) // Touch target correto
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SpeedMenuColors.BackgroundPrimary)
        ) {
            // TopBar removido - breadcrumb agora no topLeftContent do OrderFlowScaffold
            // "Ver pedido" pode ser adicionado no topRightContent se necessário no futuro
            
            // ========== CONTEÚDO PRINCIPAL (2 COLUNAS) ==========
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.Top // Alinhamento ao topo
        ) {
            // ========== COLUNA ESQUERDA (maior) ==========
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(24.dp) // Mais respiro
            ) {
                // 1) HeroImage (alinhado ao topo)
                HeroImage(imageResId = productImageResId)
                
                // 2) PriceHeader
                PriceHeader(
                    name = productName,
                    category = productCategory,
                    price = productPrice * quantity
                )
                
                // 3) Descrição curta (1 linha, truncar)
                Text(
                    text = productDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                // 4) Indicação discreta de ingredientes (apenas ativos)
                val activeIngredientsCount = ingredientQuantities.count { it.quantity > 0 }
                val totalIngredientsCount = ingredientQuantities.size
                Text(
                    text = if (activeIngredientsCount < totalIngredientsCount) {
                        "Ingredientes: $activeIngredientsCount itens ($totalIngredientsCount disponíveis)"
                    } else {
                        "Ingredientes: $activeIngredientsCount itens"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextTertiary.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
            }
            
            // ========== COLUNA DIREITA (fixa, mais estreita) ==========
            Column(
                modifier = Modifier
                    .width(380.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(20.dp) // Espaçamento aumentado entre accordions
            ) {
                // 1) Seção "Ingredientes" (accordion com lista de ingredientes)
                AccordionSection(
                    title = "Ingredientes",
                    icon = Icons.Default.Restaurant,
                    expanded = isIngredientsExpanded,
                    onExpandedChange = { isIngredientsExpanded = it },
                    summary = run {
                        val activeCount = ingredientQuantities.count { it.quantity > 0 }
                        val totalCount = ingredientQuantities.size
                        if (activeCount == 0) {
                            "Nenhum ingrediente selecionado"
                        } else if (activeCount < totalCount) {
                            "$activeCount de $totalCount ingredientes"
                        } else {
                            "Todos os $activeCount ingredientes"
                        }
                    }
                ) {
                    // Lista vertical de ingredientes com controles de quantidade
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(0.dp) // Sem espaçamento automático, controlado manualmente
                    ) {
                        ingredientQuantities.forEachIndexed { index, ingredient ->
                            // Separador sutil entre itens (exceto no primeiro)
                            if (index > 0) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(
                                            color = SpeedMenuColors.BorderSubtle.copy(alpha = 0.15f)
                                        )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                            
                            IngredientQuantityItem(
                                name = ingredient.name,
                                quantity = ingredient.quantity,
                                isBase = ingredient.isBase,
                                maxQuantity = 5,
                                onQuantityChange = { newQuantity ->
                                    ingredientQuantities[index] = ingredient.copy(quantity = newQuantity)
                                },
                                onRemoveBaseIngredient = if (ingredient.isBase) {
                                    {
                                        // Dispara confirmação ao tentar remover ingrediente base (1 -> 0)
                                        pendingIngredientIndex = index
                                        showRemoveBaseIngredientDialog = true
                                    }
                                } else null
                            )
                        }
                    }
                }
                
                // 2) Seção "Observações" (accordion com resumo)
                AccordionSection(
                    title = "Observações",
                    icon = Icons.Default.Edit,
                    expanded = isObservationsExpanded,
                    onExpandedChange = { isObservationsExpanded = it },
                    summary = if (observationsText.isEmpty()) "Sem observações" else observationsText.take(40) + if (observationsText.length > 40) "..." else ""
                ) {
                    MinimalTextField(
                        value = observationsText,
                        onValueChange = { observationsText = it },
                        placeholder = "Ex: sem cebola, bem passado..."
                    )
                }
                
                // 3) Quantidade: stepper compacto
                QuantityStepper(
                    quantity = quantity,
                    onQuantityChange = { quantity = it }
                )
                
                // Spacer para empurrar CTA para o rodapé
                Spacer(modifier = Modifier.weight(1f))
                
                // 4) CTA fixo no rodapé
                PrimaryCTA(
                    text = "Adicionar ao pedido",
                    price = productPrice * quantity,
                    onClick = onAddToCart
                )
                    }
                }
            }
            
            // Dialog de confirmação para remover ingrediente base
            pendingIngredientIndex?.let { index ->
                RemoveBaseIngredientDialog(
                    visible = showRemoveBaseIngredientDialog,
                    ingredientName = ingredientQuantities.getOrNull(index)?.name ?: "",
                    onConfirm = {
                        // Confirma remoção: seta quantidade = 0
                        if (index < ingredientQuantities.size) {
                            ingredientQuantities[index] = ingredientQuantities[index].copy(quantity = 0)
                        }
                        showRemoveBaseIngredientDialog = false
                        pendingIngredientIndex = null
                    },
                    onDismiss = {
                        // Cancela: mantém em 1 (não faz nada)
                        showRemoveBaseIngredientDialog = false
                        pendingIngredientIndex = null
                    }
                )
            }
        }
    }

/**
 * Chip de proteína selecionável (menor, mais sutil) com ícone check quando selecionado.
 */
@Composable
private fun ProteinChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .background(
                color = if (isSelected) {
                    SpeedMenuColors.Primary.copy(alpha = 0.25f) // Mais sólido quando selecionado
                } else {
                    SpeedMenuColors.Surface.copy(alpha = 0.2f) // Mais sutil
                },
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = SpeedMenuColors.PrimaryLight,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) {
                    SpeedMenuColors.PrimaryLight
                } else {
                    SpeedMenuColors.TextSecondary.copy(alpha = 0.8f)
                },
                fontSize = 13.sp
            )
        }
    }
}

