package com.speedmenu.tablet.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.core.ui.components.PrimaryCTA
import com.speedmenu.tablet.core.ui.components.TopActionBar
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.core.utils.CurrencyFormatter
import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.ui.screens.order.CartItemRow
import com.speedmenu.tablet.ui.viewmodel.ViewOrderViewModel

/**
 * Tela de visualização de pedido por comanda (read-only).
 * Visualmente idêntica ao CartSummaryScreen, mas sem botões de edição.
 * 
 * Estrutura:
 * 1. Lista de produtos do pedido (read-only)
 * 2. Resumo financeiro (subtotal e total)
 * 3. Sem botão de finalizar pedido
 */
@Composable
fun ViewOrderScreen(
    comandaCode: String,
    viewModel: ViewOrderViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Carrega o pedido quando a tela é exibida
    LaunchedEffect(comandaCode) {
        viewModel.loadOrder(comandaCode)
    }
    
    // Estados mockados
    val isConnected = true
    val tableNumber = "17"
    
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
        
        when {
            uiState.isLoading -> {
                // Estado de loading
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(
                            color = SpeedMenuColors.PrimaryLight
                        )
                        Text(
                            text = "Carregando pedido...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SpeedMenuColors.TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            uiState.error != null -> {
                // Estado de erro
                val errorMessage = uiState.error ?: "Erro desconhecido"
                ViewOrderErrorScreen(
                    error = errorMessage,
                    onNavigateBack = onNavigateBack
                )
            }
            
            uiState.isEmpty -> {
                // Estado vazio
                ViewOrderEmptyScreen(
                    onNavigateBack = onNavigateBack
                )
            }
            
            else -> {
                // Estado de sucesso - mostra lista de itens
                ViewOrderContentScreen(
                    items = uiState.items,
                    subtotal = uiState.subtotal,
                    total = uiState.total
                )
            }
        }
    }
}

/**
 * Conteúdo principal da tela de visualização de pedido (quando há itens).
 */
@Composable
private fun ViewOrderContentScreen(
    items: List<CartItem>,
    subtotal: Double,
    total: Double
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
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
                    readOnly = true, // Modo read-only
                    animationDelay = index * 50 // Delay escalonado: 0ms, 50ms, 100ms...
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
        }
        
        // Espaçamento confortável entre resumo e limite inferior da tela
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
    }
}

/**
 * Tela de estado vazio quando não há pedido para a comanda.
 */
@Composable
private fun ViewOrderEmptyScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Top Action Bar
        TopActionBar(
            onBackClick = onNavigateBack,
            isConnected = true,
            tableNumber = "17",
            onCallWaiterClick = {}
        )
        
        // Conteúdo centralizado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Ícone discreto
                Box(
                    modifier = Modifier
                        .background(
                            color = SpeedMenuColors.Surface.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Pedido vazio",
                        tint = SpeedMenuColors.TextTertiary.copy(alpha = 0.25f),
                        modifier = Modifier.size(72.dp)
                    )
                }
                
                // Mensagem principal
                Text(
                    text = "Nenhum pedido encontrado",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = SpeedMenuColors.TextPrimary.copy(alpha = 0.9f),
                    fontSize = 26.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                
                // Mensagem secundária
                Text(
                    text = "Não há pedidos para esta comanda",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                    color = SpeedMenuColors.TextSecondary.copy(alpha = 0.7f),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                
                // Botão "Voltar para Home"
                PrimaryCTA(
                    text = "Voltar para Home",
                    price = 0.0,
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

/**
 * Tela de erro ao carregar pedido.
 */
@Composable
private fun ViewOrderErrorScreen(
    error: String,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Top Action Bar
        TopActionBar(
            onBackClick = onNavigateBack,
            isConnected = true,
            tableNumber = "17",
            onCallWaiterClick = {}
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Erro ao carregar pedido",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = SpeedMenuColors.TextPrimary,
                    fontSize = 20.sp
                )
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

