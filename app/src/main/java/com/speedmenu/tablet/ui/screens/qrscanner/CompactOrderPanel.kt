package com.speedmenu.tablet.ui.screens.qrscanner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.utils.CurrencyFormatter
import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.ui.screens.order.CartItemRow
import com.speedmenu.tablet.ui.viewmodel.FinalizationState

/**
 * Painel compacto de carrinho/pedido para o split view do scanner.
 * 
 * Comportamento por modo:
 * - CHECKOUT: SEMPRE mostra carrinho (mesmo vazio). Auto-finaliza após scan (sem botão).
 * - VIEW_ORDER: Antes do scan mostra instrução. Após scan, mostra pedido read-only.
 * 
 * @param mode Modo de operação (CHECKOUT ou VIEW_ORDER)
 * @param items Lista de itens do carrinho (CHECKOUT) ou pedido (VIEW_ORDER)
 * @param isLoading Se true, mostra loading (apenas VIEW_ORDER)
 * @param error Mensagem de erro (se houver, apenas VIEW_ORDER)
 * @param comandaCode Código da comanda escaneada (se houver)
 * @param finalizationState Estado da finalização (apenas CHECKOUT, null em VIEW_ORDER)
 * @param onUpdateQuantity Callback para atualizar quantidade (apenas CHECKOUT)
 * @param onRemoveItem Callback para remover item (apenas CHECKOUT)
 * @param onRetryFinalization Callback para tentar finalizar novamente após erro (apenas CHECKOUT)
 */
@Composable
fun CompactOrderPanel(
    mode: QrScannerMode,
    items: List<CartItem>,
    isLoading: Boolean = false,
    error: String? = null,
    comandaCode: String? = null,
    finalizationState: FinalizationState? = null,
    onUpdateQuantity: ((String, Int) -> Unit)? = null,
    onRemoveItem: ((String) -> Unit)? = null,
    onRetryFinalization: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(
                color = colorScheme.surface.copy(alpha = 0.98f), // Opaco (98%) para evitar câmera aparecer por trás
                shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
            )
            .padding(24.dp)
    ) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Título dinâmico
            Text(
                text = when (mode) {
                    QrScannerMode.CHECKOUT -> "Resumo do pedido"
                    QrScannerMode.VIEW_ORDER -> "Pedido da comanda"
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface,
                fontSize = 22.sp
            )
            
            // Subtítulo: apenas no CHECKOUT (no VIEW_ORDER, a instrução fica no overlay da câmera)
            if (mode == QrScannerMode.CHECKOUT) {
                Text(
                    text = "Escaneie a comanda para finalizar",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
            
            // Badge da comanda (se houver)
            if (comandaCode != null) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(
                            color = colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Comanda: $comandaCode",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.primary,
                        fontSize = 12.sp
                    )
                }
            }
        }
        
        // Divisor
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(vertical = 16.dp)
                .background(colorScheme.outlineVariant.copy(alpha = 0.3f))
        )
        
        // Conteúdo: Lista de itens ou estados
        // Lógica diferente por modo:
        // - CHECKOUT: sempre mostra carrinho (mesmo vazio)
        // - VIEW_ORDER: antes do scan mostra instrução, depois mostra pedido
        Box(modifier = Modifier.weight(1f)) {
            when (mode) {
                QrScannerMode.CHECKOUT -> {
                    // CHECKOUT: sempre mostra carrinho
                    if (items.isEmpty()) {
                        // Carrinho vazio
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Carrinho vazio",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.onSurfaceVariant,
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        // Lista de itens do carrinho (scrollável e editável)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items.forEachIndexed { index, item ->
                                CartItemRow(
                                    item = item,
                                    readOnly = false, // CHECKOUT sempre editável
                                    animationDelay = index * 30,
                                    onRemoveItem = {
                                        onRemoveItem?.invoke(item.id)
                                    },
                                    onUpdateQuantity = { newQuantity ->
                                        onUpdateQuantity?.invoke(item.id, newQuantity)
                                    }
                                )
                            }
                        }
                    }
                }
                
                QrScannerMode.VIEW_ORDER -> {
                    // VIEW_ORDER: antes do scan mostra instrução, depois mostra pedido
                    when {
                        isLoading -> {
                            // Loading (após scan, carregando pedido)
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    CircularProgressIndicator(
                                        color = colorScheme.primary
                                    )
                                    Text(
                                        text = "Carregando pedido...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colorScheme.onSurfaceVariant,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                        
                        error != null -> {
                            // Erro ao carregar pedido
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Erro",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colorScheme.error,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = error,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colorScheme.onSurfaceVariant,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                        
                        items.isEmpty() -> {
                            // Estado vazio: antes do scan ou após erro
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                    Text(
                                        text = if (comandaCode == null) {
                                            "Escaneie a comanda para carregar o pedido"
                                        } else {
                                            "Nenhum pedido encontrado"
                                        },
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colorScheme.onSurfaceVariant,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                            }
                        }
                        
                        else -> {
                            // Lista de itens do pedido (scrollável e read-only)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items.forEachIndexed { index, item ->
                                    CartItemRow(
                                        item = item,
                                        readOnly = true, // VIEW_ORDER sempre read-only
                                        animationDelay = index * 30,
                                        onRemoveItem = {},
                                        onUpdateQuantity = {}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Rodapé fixo
        // CHECKOUT: sempre mostra (mesmo se vazio, mostra total e botão desabilitado)
        // VIEW_ORDER: só mostra se houver itens carregados
        val shouldShowFooter = when (mode) {
            QrScannerMode.CHECKOUT -> true // Sempre mostra no CHECKOUT
            QrScannerMode.VIEW_ORDER -> items.isNotEmpty() && !isLoading && error == null
        }
        
        if (shouldShowFooter) {
            // Divisor
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(vertical = 16.dp)
                    .background(colorScheme.outlineVariant.copy(alpha = 0.3f))
            )
            
            // Total (sempre calcula, mesmo se vazio)
            val total = items.sumOf { it.totalPrice }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface,
                    fontSize = 20.sp
                )
                Text(
                    text = CurrencyFormatter.formatCurrencyBR(total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary,
                    fontSize = 20.sp
                )
            }
            
            // Estados de finalização (apenas CHECKOUT)
            // REMOVIDO: Botão "Finalizar pedido" - agora finaliza automaticamente após scan
            if (mode == QrScannerMode.CHECKOUT) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    when (finalizationState ?: FinalizationState.Idle) {
                        is FinalizationState.Idle -> {
                            // Antes do scan: instrução
                            Text(
                                text = "Escaneie a comanda para finalizar",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.onSurfaceVariant,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        
                        is FinalizationState.Finalizing -> {
                            // Finalizando: loading
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    color = colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Finalizando pedido...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        
                        is FinalizationState.Success -> {
                            // Sucesso: mensagem de confirmação
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = colorScheme.tertiaryContainer.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = "✓ Pedido finalizado!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorScheme.tertiary,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        
                        is FinalizationState.Error -> {
                            // Erro: mensagem + botão tentar novamente
                            val errorState = finalizationState as FinalizationState.Error
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = colorScheme.errorContainer.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = errorState.message,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colorScheme.error,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                
                                // Botão "Tentar novamente"
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = colorScheme.primary.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable(onClick = onRetryFinalization)
                                        .padding(horizontal = 20.dp, vertical = 12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = null,
                                            tint = colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Tentar novamente",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = colorScheme.primary,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

