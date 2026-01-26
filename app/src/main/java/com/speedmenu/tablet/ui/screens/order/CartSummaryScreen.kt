package com.speedmenu.tablet.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
 * Tela de resumo do pedido.
 * Exibida quando o carrinho possui itens.
 */
@Composable
fun CartSummaryScreen(
    items: List<CartItem> = emptyList(),
    cartViewModel: CartViewModel? = null,
    onNavigateBack: () -> Unit = {},
    onFinishOrder: () -> Unit = {},
    cartItemCount: Int = 0
) {
    // Estados mockados
    val isConnected = true
    val tableNumber = "17"
    
    // Calcula total parcial
    val subtotal = items.sumOf { it.totalPrice }
    
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
        
        // Conteúdo scrollável
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Título - elegante e confiante
            Text(
                text = "Seu pedido",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold, // Menos pesado que Bold
                color = SpeedMenuColors.TextPrimary.copy(alpha = 0.95f), // Ligeiramente mais suave
                fontSize = 30.sp, // Ligeiramente menor
                letterSpacing = 0.3.sp // Espaçamento refinado
            )
            
            // Lista de itens
            if (items.isEmpty()) {
                // Estado vazio (não deveria aparecer, mas por segurança)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum item no pedido",
                        style = MaterialTheme.typography.bodyLarge,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 16.sp
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items.forEach { item ->
                        CartItemRow(
                            item = item,
                            cartViewModel = cartViewModel,
                            onRemoveItem = {
                                cartViewModel?.removeItem(item.id)
                            },
                            onUpdateQuantity = { newQuantity ->
                                cartViewModel?.updateItemQuantity(item.id, newQuantity)
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Resumo de valores - premium e discreto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SpeedMenuColors.Surface.copy(alpha = 0.12f), // Mais sutil
                        shape = RoundedCornerShape(18.dp) // Mais suave
                    )
                    .padding(24.dp) // Mais espaçoso
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Subtotal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Subtotal",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = SpeedMenuColors.TextSecondary,
                            fontSize = 16.sp
                        )
                        Text(
                            text = CurrencyFormatter.formatCurrencyBR(subtotal),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 16.sp
                        )
                    }
                    
                    // Taxa de serviço (opcional, pode ser adicionada depois)
                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold, // Menos pesado
                            color = SpeedMenuColors.TextPrimary.copy(alpha = 0.95f),
                            fontSize = 19.sp,
                            letterSpacing = 0.2.sp
                        )
                        Text(
                            text = CurrencyFormatter.formatCurrencyBR(subtotal),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold, // Menos pesado
                            color = SpeedMenuColors.PrimaryLight.copy(alpha = 0.9f), // Mais suave
                            fontSize = 19.sp,
                            letterSpacing = 0.2.sp
                        )
                    }
                }
            }
        }
        
        // CTA fixo no rodapé
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            PrimaryCTA(
                text = "Finalizar pedido",
                price = subtotal,
                onClick = onFinishOrder,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Linha de item do carrinho com controles de quantidade e remoção.
 */
@Composable
private fun CartItemRow(
    item: CartItem,
    cartViewModel: CartViewModel?,
    onRemoveItem: () -> Unit,
    onUpdateQuantity: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SpeedMenuColors.Surface.copy(alpha = 0.08f), // Mais sutil
                shape = RoundedCornerShape(14.dp) // Mais suave
            )
            .padding(18.dp) // Mais espaçoso
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Informações do item
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium, // Mais leve
                    color = SpeedMenuColors.TextPrimary.copy(alpha = 0.95f), // Ligeiramente mais suave
                    fontSize = 16.sp,
                    letterSpacing = 0.1.sp
                )
                Text(
                    text = "${item.quantity}x ${CurrencyFormatter.formatCurrencyBR(item.price)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light, // Mais leve
                    color = SpeedMenuColors.TextSecondary.copy(alpha = 0.75f), // Mais discreto
                    fontSize = 14.sp,
                    letterSpacing = 0.2.sp
                )
            }
            
            // Controles de quantidade e preço
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Controles de quantidade (discretos)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botão diminuir
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = SpeedMenuColors.Surface.copy(alpha = 0.2f),
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
                            tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.7f),
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
                                color = SpeedMenuColors.Surface.copy(alpha = 0.2f),
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
                            tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                // Preço total do item - discreto mas claro
                Text(
                    text = CurrencyFormatter.formatCurrencyBR(item.totalPrice),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium, // Mais leve
                    color = SpeedMenuColors.PrimaryLight.copy(alpha = 0.85f), // Mais suave
                    fontSize = 16.sp,
                    letterSpacing = 0.1.sp,
                    modifier = Modifier.width(80.dp)
                )
                
                // Botão remover (discreto)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = SpeedMenuColors.Surface.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(onClick = onRemoveItem),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remover item",
                        tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

