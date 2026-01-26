package com.speedmenu.tablet.ui.screens.order

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.speedmenu.tablet.core.ui.components.TopActionBar
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
        TopActionBar(
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
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEach { item ->
                CartItemRow(
                    item = item,
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
 */
@Composable
private fun CartItemRow(
    item: CartItem,
    onRemoveItem: () -> Unit,
    onUpdateQuantity: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
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
            
            // Controles de quantidade (discretos, abaixo das informações)
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
        
        // ========== PREÇO TOTAL DO ITEM (canto direito) ==========
        // Alinhamento consistente e destaque visual
        Box(
            modifier = Modifier
                .width(120.dp)
                .padding(start = 16.dp),
            contentAlignment = Alignment.TopEnd
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

