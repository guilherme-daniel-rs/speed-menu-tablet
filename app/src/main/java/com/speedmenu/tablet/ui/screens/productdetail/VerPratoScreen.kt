package com.speedmenu.tablet.ui.screens.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.speedmenu.tablet.core.ui.components.AccordionSection
import com.speedmenu.tablet.core.ui.components.HeroImage
import com.speedmenu.tablet.core.ui.components.IngredientQuantityItem
import com.speedmenu.tablet.core.ui.components.MinimalTextField
import com.speedmenu.tablet.core.ui.components.DiscreteToast
import com.speedmenu.tablet.core.ui.components.PrimaryCTA
import com.speedmenu.tablet.core.ui.components.PriceHeader
import com.speedmenu.tablet.core.ui.components.QuantityStepper
import com.speedmenu.tablet.core.ui.components.RemoveBaseIngredientDialog
import com.speedmenu.tablet.core.ui.components.TopActionBar
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.domain.model.CartItemOptions
import com.speedmenu.tablet.ui.viewmodel.CartViewModel
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.core.utils.CurrencyFormatter

/**
 * Estado de quantidade de um ingrediente.
 */
data class IngredientQuantity(
    val name: String,
    val isBase: Boolean, // true = ingrediente base (requer confirmação ao remover de 1 para 0), false = opcional
    var quantity: Int
)

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
    cartViewModel: CartViewModel,
    onNavigateBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    // Estados locais
    var quantity by remember { mutableStateOf(1) }
    var observationsText by remember { mutableStateOf("") }
    
    // Estados para modais
    var showIngredientsModal by remember { mutableStateOf(false) }
    var showObservationsModal by remember { mutableStateOf(false) }
    
    // Estado para dialog de confirmação de remoção de ingrediente base
    var showRemoveBaseIngredientDialog by remember { mutableStateOf(false) }
    var pendingIngredientIndex by remember { mutableStateOf<Int?>(null) }
    
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
    
    // Estado do carrinho do ViewModel
    val cartState by cartViewModel.cartState.collectAsState()
    val cartItemCount = cartState.totalItems
    
    // Estado para feedback de adição ao pedido
    var isAddedToCart by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    
    // Estado para controlar visibilidade do dialog de garçom
    var showWaiterCalledDialog by remember { mutableStateOf(false) }
    
    // Estados mockados (em produção viriam de um ViewModel)
    val isConnected = remember { true } // Mock: sempre conectado
    val tableNumber = remember { "17" } // Mock: mesa 17
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // ========== TOP ACTION BAR FIXA ==========
        // REGRA: Botão "Voltar" na tela de prato retorna para a categoria anterior (popBackStack)
        // Mantém categoria selecionada preservada via savedStateHandle
        TopActionBar(
            onBackClick = onNavigateBack, // Retorna para a categoria anterior (popBackStack)
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {
                showWaiterCalledDialog = true
            }
        )
        
        // ========== CONTEÚDO PRINCIPAL (2 COLUNAS) ==========
        Row(
            modifier = Modifier
                .weight(1f) // Usa weight ao invés de fillMaxSize para não cobrir a TopActionBar
                .fillMaxWidth()
                .padding(top = 18.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.Top
        ) {
            // ========== COLUNA ESQUERDA (weight 1.45f) ==========
            Column(
                modifier = Modifier
                    .weight(1.45f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1) Imagem do prato com altura fixa (aumentada para maior destaque)
                ProductImage(
                    imageResId = productImageResId,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                
                // 2) PriceHeader (preço sempre unitário, fixo)
                PriceHeader(
                    name = productName,
                    category = productCategory,
                    price = productPrice
                )
                
                // 3) Descrição curta (máximo 2 linhas)
                Text(
                    text = productDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                // 4) Indicação discreta de ingredientes (1 linha)
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
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // ========== COLUNA DIREITA (weight 0.55f) ==========
            Column(
                modifier = Modifier
                    .weight(0.55f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1) Seção "Ingredientes" (accordion - inicia fechada)
                val activeCount = ingredientQuantities.count { it.quantity > 0 }
                val totalCount = ingredientQuantities.size
                var isIngredientsExpanded by remember { mutableStateOf(false) }
                
                AccordionSection(
                    title = "Ingredientes",
                    icon = Icons.Default.Restaurant,
                    expanded = isIngredientsExpanded,
                    onExpandedChange = { isIngredientsExpanded = it },
                    summary = "$activeCount de $totalCount ingredientes",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Lista de ingredientes
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        ingredientQuantities.forEachIndexed { index, ingredient ->
                            if (index > 0) {
                                // Divisória sutil entre itens
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(
                                            color = SpeedMenuColors.BorderSubtle.copy(alpha = 0.06f)
                                        )
                                )
                            }
                            
                            // Item com padding vertical de 10.dp
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            ) {
                                IngredientQuantityItem(
                                    name = ingredient.name,
                                    quantity = ingredient.quantity,
                                    isBase = ingredient.isBase,
                                    maxQuantity = 5,
                                    onQuantityChange = { newQuantity ->
                                        val actualIndex = ingredientQuantities.indexOf(ingredient)
                                        if (actualIndex >= 0) {
                                            ingredientQuantities[actualIndex] = ingredient.copy(quantity = newQuantity)
                                        }
                                    },
                                    onRemoveBaseIngredient = if (ingredient.isBase) {
                                        {
                                            val actualIndex = ingredientQuantities.indexOf(ingredient)
                                            if (actualIndex >= 0) {
                                                pendingIngredientIndex = actualIndex
                                                showRemoveBaseIngredientDialog = true
                                            }
                                        }
                                    } else null
                                )
                            }
                        }
                    }
                }
                
                // 2) Botão "Adicionar observações"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SpeedMenuColors.Surface.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { showObservationsModal = true }
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.6f),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (observationsText.isEmpty()) {
                                    "Adicionar observações"
                                } else {
                                    "Com observações"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (observationsText.isEmpty()) {
                                    SpeedMenuColors.TextSecondary
                                } else {
                                    SpeedMenuColors.TextPrimary
                                },
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                
                // 3) Quantidade: stepper compacto
                QuantityStepper(
                    quantity = quantity,
                    onQuantityChange = { quantity = it }
                )
                
                // Spacer para empurrar CTA para o rodapé
                Spacer(modifier = Modifier.weight(1f))
                
                // 4) CTA fixo no rodapé (com espaçamento inferior)
                PrimaryCTA(
                    modifier = Modifier.padding(bottom = 24.dp),
                    text = if (isAddedToCart) "Adicionado ao pedido" else "Adicionar ao pedido",
                    price = if (isAddedToCart) 0.0 else productPrice * quantity,
                    onClick = {
                        // Cria CartItem com as informações do produto
                        val cartItemOptions = CartItemOptions(
                            ingredients = ingredientQuantities
                                .filter { it.quantity > 0 }
                                .associate { it.name to it.quantity },
                            observations = observationsText
                        )
                        
                        // Cria CartItem com as informações do produto
                        // ID vazio será gerado pelo ViewModel se necessário
                        val cartItem = CartItem(
                            id = "", // Será gerado pelo ViewModel se necessário
                            productId = productId,
                            name = productName,
                            price = productPrice,
                            quantity = quantity,
                            options = cartItemOptions
                        )
                        
                        // Adiciona ao carrinho via ViewModel
                        // A adição é síncrona e atualiza o estado imediatamente
                        val previousItemCount = cartViewModel.cartState.value.totalItems
                        
                        // DEBUG CHECKLIST:
                        // ✅ O método addItem() é realmente chamado? (verificar logs)
                        // ✅ O estado global do carrinho é mutado? (verificar newItemCount)
                        // ✅ O componente do topo está observando o estado correto? (cartState.totalItems)
                        // ✅ Existe mais de um carrinho instanciado? (verificar logs do ViewModel)
                        // ✅ O store não está sendo recriado ao navegar de tela? (hiltViewModel garante instância única)
                        
                        cartViewModel.addItem(cartItem)
                        
                        // Verifica se o item foi realmente adicionado ao estado
                        // REGRA DE OURO: Se o botão "Pedido" mostra vazio, o estado está vazio.
                        // Nunca confiar na animação como confirmação.
                        val newItemCount = cartViewModel.cartState.value.totalItems
                        
                        // Verifica se totalItems aumentou (considera tanto novo item quanto incremento)
                        // Se o item já existia, a quantidade será incrementada, então newItemCount deve ser > previousItemCount
                        val itemWasAdded = newItemCount > previousItemCount
                        
                        // REGRA DE UX: Nenhuma animação deve acontecer sem mudança real de estado.
                        // Se algo animou, algo mudou. Se nada mudou, nada anima.
                        // Só atualiza feedback visual se o item foi realmente adicionado
                        // A verificação garante que totalItems aumentou (novo item ou incremento)
                        if (itemWasAdded) {
                            // Atualiza estado de feedback visual local (botão e toast)
                            isAddedToCart = true
                            showToast = true
                            
                            // NOTA: A animação do carrinho no topo é disparada automaticamente
                            // pela mudança de cartItemCount via LaunchedEffect nos componentes
                            // OrderTopStatusPill/TopRightStatusPill. Não é necessário chamar
                            // onAddToCart() aqui, pois a animação já está vinculada à mudança
                            // real de estado (cartItemCount).
                        }
                    }
                )
            }
        }
    }
    
    // Modal de ingredientes (lista completa)
    if (showIngredientsModal) {
        IngredientsModal(
            ingredients = ingredientQuantities,
            onDismiss = { showIngredientsModal = false },
            onQuantityChange = { index, newQuantity ->
                if (index < ingredientQuantities.size) {
                    ingredientQuantities[index] = ingredientQuantities[index].copy(quantity = newQuantity)
                }
            },
            onRemoveBaseIngredient = { index ->
                pendingIngredientIndex = index
                showRemoveBaseIngredientDialog = true
            }
        )
    }
    
    // Modal de observações
    if (showObservationsModal) {
        ObservationsModal(
            observations = observationsText,
            onDismiss = { showObservationsModal = false },
            onSave = { text ->
                observationsText = text
                showObservationsModal = false
            }
        )
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
    
    // Dialog de garçom chamado
    if (showWaiterCalledDialog) {
        WaiterCalledDialog(
            visible = showWaiterCalledDialog,
            onDismiss = { showWaiterCalledDialog = false },
            onConfirm = { showWaiterCalledDialog = false }
        )
    }
    
    // Toast discreto de confirmação
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp) // Posiciona abaixo da TopActionBar
    ) {
        DiscreteToast(
            message = "Item adicionado ao pedido",
            visible = showToast,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
    
    // Auto-dismiss do toast após 2 segundos
    LaunchedEffect(showToast) {
        if (showToast) {
            delay(2000)
            showToast = false
        }
    }
    
    // Reseta estado do botão após 1 segundo
    LaunchedEffect(isAddedToCart) {
        if (isAddedToCart) {
            delay(1000)
            isAddedToCart = false
        }
    }
}

/**
 * Modal com lista completa de ingredientes (scrollável dentro do modal).
 */
@Composable
private fun IngredientsModal(
    ingredients: List<IngredientQuantity>,
    onDismiss: () -> Unit,
    onQuantityChange: (Int, Int) -> Unit,
    onRemoveBaseIngredient: (Int) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.82f))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 640.dp)
                    .fillMaxWidth(0.75f)
                    .heightIn(max = 600.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.95f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = Color.Black.copy(alpha = 0.35f),
                            ambientColor = Color.Black.copy(alpha = 0.18f)
                        )
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ingredientes",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 24.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = SpeedMenuColors.TextSecondary,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(onClick = onDismiss)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Lista scrollável de ingredientes
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        ingredients.forEachIndexed { index, ingredient ->
                            if (index > 0) {
                                // Divisória sutil entre itens
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(
                                            color = SpeedMenuColors.BorderSubtle.copy(alpha = 0.06f)
                                        )
                                )
                            }
                            
                            // Item com padding vertical de 10.dp
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            ) {
                                IngredientQuantityItem(
                                    name = ingredient.name,
                                    quantity = ingredient.quantity,
                                    isBase = ingredient.isBase,
                                    maxQuantity = 5,
                                    onQuantityChange = { newQuantity ->
                                        onQuantityChange(index, newQuantity)
                                    },
                                    onRemoveBaseIngredient = if (ingredient.isBase) {
                                        { onRemoveBaseIngredient(index) }
                                    } else null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Modal para adicionar observações.
 */
@Composable
private fun ObservationsModal(
    observations: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(observations) }
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.82f))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 640.dp)
                    .fillMaxWidth(0.75f)
                    .heightIn(max = 500.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.95f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = Color.Black.copy(alpha = 0.35f),
                            ambientColor = Color.Black.copy(alpha = 0.18f)
                        )
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Observações",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 24.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = SpeedMenuColors.TextSecondary,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(onClick = onDismiss)
                        )
                    }
                    
                    // TextField grande (multiline)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp, max = 200.dp)
                            .background(
                                color = SpeedMenuColors.Surface.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp)
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier.fillMaxSize(),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = SpeedMenuColors.TextPrimary,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            ),
                            maxLines = 8,
                            decorationBox = { innerTextField ->
                                if (text.isEmpty()) {
                                    Text(
                                        text = "Ex: sem cebola, bem passado, sem sal...",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = SpeedMenuColors.TextTertiary,
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Botões
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Cancelar
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .background(
                                    color = SpeedMenuColors.Surface.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .clickable(onClick = onDismiss),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cancelar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SpeedMenuColors.TextSecondary,
                                fontSize = 16.sp
                            )
                        }
                        
                        // Salvar
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            SpeedMenuColors.Primary,
                                            SpeedMenuColors.PrimaryDark
                                        )
                                    ),
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .clickable(onClick = { onSave(text) }),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Salvar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SpeedMenuColors.TextOnPrimary,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Imagem do prato com altura fixa e rounded corners.
 */
@Composable
private fun ProductImage(
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
    ) {
        HeroImage(
            imageResId = imageResId,
            modifier = Modifier.fillMaxSize()
        )
    }
}

