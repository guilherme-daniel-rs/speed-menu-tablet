package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Wrapper padrão para todas as telas do fluxo de pedido.
 * Garante posicionamento consistente do OrderTopStatusPill em todas as telas.
 * 
 * Características:
 * - Overlay fixo no topo direito com padding consistente (top = 16.dp, end = 16.dp)
 * - Não empurra conteúdo, não quebra layout
 * - Funciona em qualquer estrutura de layout (Row, Column, Box, etc)
 * - Fica fixo mesmo em telas com scroll
 * 
 * @param isConnected Status da conexão (true = conectado, false = desconectado)
 * @param tableNumber Número da mesa (String, será formatado como "Mesa {number}")
 * @param onCallWaiterClick Callback quando o botão "Garçom" é clicado
 * @param onCartClick Callback quando o botão "Pedido" é clicado
 * @param cartItemCount Quantidade de itens no carrinho (0 = carrinho vazio, sem badge)
 * @param enabled Se o botão de garçom está habilitado (padrão: true)
 * @param topLeftContent Slot opcional para conteúdo do topo esquerdo (ex.: back button, breadcrumb)
 * @param content Conteúdo principal da tela
 */
@Composable
fun OrderFlowScaffold(
    isConnected: Boolean = true,
    tableNumber: String = "17",
    onCallWaiterClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    cartItemCount: Int = 0,
    enabled: Boolean = true,
    topLeftContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Conteúdo principal da tela
        content()
        
        // Slot opcional para conteúdo do topo esquerdo (ex.: back button, breadcrumb)
        topLeftContent?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                it()
            }
        }
        
        // Status pill fixo no topo direito (overlay)
        // Padding consistente em todas as telas: top = 16.dp, end = 16.dp
        OrderTopStatusPill(
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = onCallWaiterClick,
            onCartClick = onCartClick,
            cartItemCount = cartItemCount,
            enabled = enabled,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        )
    }
}

