package com.speedmenu.tablet.ui.screens.order

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.components.PrimaryCTA
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.core.utils.CurrencyFormatter
import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.ui.viewmodel.CartViewModel

/**
 * Tela de carrinho - revisão do pedido antes de finalizar.
 * Design clean, escuro e sofisticado com foco em clareza e elegância.
 * 
 * Estrutura:
 * 1. Lista de produtos do carrinho
 * 2. Resumo financeiro (subtotal e total)
 * 3. Botão de finalizar pedido
 */
@Composable
fun CartSummaryScreen(
    items: List<CartItem> = emptyList(),
    cartViewModel: CartViewModel? = null,
    onNavigateBack: () -> Unit = {},
    onFinishOrder: () -> Unit = {}
) {
    // Estados mockados
    val isConnected = true
    val tableNumber = "17"
    
    // Calcula valores
    val subtotal = items.sumOf { it.totalPrice }
    val total = subtotal // Por enquanto, total = subtotal (taxa de serviço pode ser adicionada depois)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Top Action Bar
        AppTopBar(
            showBackButton = true,
            onBackClick = onNavigateBack,
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {}
        )
        
        // Título "Seu pedido"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Seu pedido",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = SpeedMenuColors.TextPrimary,
                fontSize = 28.sp
            )
        }
        
        // Divisor visual entre título e lista
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.2f))
        )
        
        // Lista de produtos (scrollável)
        // Espaçamento aumentado para destacar os gradientes sutis de cada item
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items.forEachIndexed { index, item ->
                CartItemRow(
                    item = item,
                    animationDelay = index * 50, // Delay escalonado: 0ms, 50ms, 100ms...
                    onRemoveItem = {
                        cartViewModel?.removeItem(item.id)
                    },
                    onUpdateQuantity = { newQuantity ->
                        cartViewModel?.updateItemQuantity(item.id, newQuantity)
                    }
                )
            }
        }
        
        // Divisor visual entre lista e resumo financeiro
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.2f))
        )
        
        // Resumo financeiro (fixo, não rola) - área fixa na parte inferior
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SpeedMenuColors.Surface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(horizontal = 32.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Subtotal (menor destaque visual)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Subtotal",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextSecondary.copy(alpha = 0.8f),
                    fontSize = 15.sp
                )
                Text(
                    text = CurrencyFormatter.formatCurrencyBR(subtotal),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 15.sp
                )
            }
            
            // Divisor entre subtotal e total
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.3f))
            )
            
            // Total (maior destaque tipográfico e cor)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SpeedMenuColors.TextPrimary,
                    fontSize = 24.sp
                )
                Text(
                    text = CurrencyFormatter.formatCurrencyBR(total),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SpeedMenuColors.PrimaryLight,
                    fontSize = 24.sp
                )
            }
            
            // Botão "Finalizar pedido" logo abaixo do Total
            PrimaryCTA(
                text = "Finalizar pedido",
                price = total,
                onClick = onFinishOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
        
        // Espaçamento confortável entre botão e limite inferior da tela
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
    }
}

/**
 * Linha de item do carrinho.
 * Layout: [Imagem à esquerda] | [Informações à direita da imagem] | [Preço total no canto direito]
 * 
 * Refinamento premium: Gradiente horizontal sutil conecta visualmente produto e preço.
 * 
 * @param readOnly Se true, não mostra botões de quantidade/remover, apenas texto "Qtd: X"
 */
@Composable
fun CartItemRow(
    item: CartItem,
    onRemoveItem: () -> Unit = {},
    onUpdateQuantity: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    animationDelay: Int = 0 // Delay em millisegundos para efeito cascata
) {
    // Animação de fade-in ao carregar o item com delay escalonado para efeito cascata elegante
    var isVisible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400, delayMillis = animationDelay),
        label = "item_fade_in"
    )
    
    LaunchedEffect(Unit) {
        isVisible = true
    }
    
    // Gradiente premium com variação horizontal e vertical bem visível
    // Cores derivadas do design system: preto/cinza escuro com toque perceptível de dourado
    val gradientTopLeft = SpeedMenuColors.BackgroundPrimary.copy(alpha = 0.85f) // Topo esquerda: mais claro que o fundo
    val gradientTopRight = SpeedMenuColors.Surface.copy(alpha = 0.55f) // Topo direita: bem visível
    val gradientBottomLeft = SpeedMenuColors.Surface.copy(alpha = 0.40f) // Centro esquerda: perceptível
    val gradientBottomRight = Color(
        // Centro direita: mistura bem visível de cinza escuro com toque perceptível de dourado
        // Onde o preço fica - zona mais "iluminada" do gradiente
        red = (SpeedMenuColors.Surface.red * 0.65f + SpeedMenuColors.PrimaryLight.red * 0.15f).coerceIn(0f, 1f),
        green = (SpeedMenuColors.Surface.green * 0.65f + SpeedMenuColors.PrimaryLight.green * 0.15f).coerceIn(0f, 1f),
        blue = (SpeedMenuColors.Surface.blue * 0.65f + SpeedMenuColors.PrimaryLight.blue * 0.15f).coerceIn(0f, 1f),
        alpha = 0.70f
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha)
    ) {
        // Gradiente de fundo diagonal sutil - cria "faixa visual" conectando produto ao preço
        // Variação vertical: topo mais escuro que o centro (visual premium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            gradientTopLeft,      // Topo esquerda (mais sutil)
                            gradientBottomLeft,   // Centro esquerda (transição)
                            gradientTopRight,     // Topo direita (intensificando)
                            gradientBottomRight   // Centro direita (mais intenso - onde está o preço)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.Top
            ) {
        // ========== IMAGEM DO PRATO (esquerda) ==========
        if (item.imageResId != 0) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
        
        // ========== INFORMAÇÕES DO PRATO (direita da imagem) ==========
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Nome do prato (destaque principal)
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = SpeedMenuColors.TextPrimary,
                fontSize = 18.sp
            )
            
            // Quantidade × Preço unitário
            Text(
                text = "${item.quantity} × ${CurrencyFormatter.formatCurrencyBR(item.price)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = SpeedMenuColors.TextSecondary,
                fontSize = 15.sp
            )
            
            // Seção "Observações" (se houver observações ou ingredientes)
            val hasObservations = item.options.observations.isNotBlank()
            val hasIngredients = item.options.ingredients.isNotEmpty()
            
            if (hasObservations || hasIngredients) {
                // Constrói lista de itens separados por vírgula
                val itemsList = mutableListOf<String>()
                
                // Adiciona observações
                if (hasObservations) {
                    itemsList.add(item.options.observations)
                }
                
                // Adiciona ingredientes (ajustes e modificações)
                item.options.ingredients.forEach { (ingredientName, quantity) ->
                    val ingredientText = when {
                        quantity > 1 -> "$ingredientName (${quantity}x)"
                        quantity == 1 -> ingredientName
                        else -> "Sem $ingredientName"
                    }
                    itemsList.add(ingredientText)
                }
                
                // Exibe tudo em uma única linha
                Text(
                    text = "Observações: ${itemsList.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextTertiary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            // Controles de quantidade ou texto read-only
            if (readOnly) {
                // Modo read-only: apenas texto discreto "Qtd: X"
                Text(
                    text = "Qtd: ${item.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextTertiary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                // Modo editável: botões de quantidade e remover
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    // Botão diminuir
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = SpeedMenuColors.Surface.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (item.quantity > 1) {
                                    onUpdateQuantity(item.quantity - 1)
                                } else {
                                    onRemoveItem()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Diminuir quantidade",
                            tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.8f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    
                    // Quantidade
                    Text(
                        text = "${item.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 15.sp,
                        modifier = Modifier.width(24.dp)
                    )
                    
                    // Botão aumentar
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = SpeedMenuColors.Surface.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                onUpdateQuantity(item.quantity + 1)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar quantidade",
                            tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.8f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    
                    // Botão remover (discreto)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = SpeedMenuColors.Surface.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(onClick = onRemoveItem),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remover item",
                            tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
        
                // ========== PREÇO TOTAL DO ITEM (canto direito) ==========
                // Alinhamento vertical ao centro - dentro da zona mais "iluminada" do gradiente
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(start = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = CurrencyFormatter.formatCurrencyBR(item.totalPrice),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = SpeedMenuColors.PrimaryLight,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

