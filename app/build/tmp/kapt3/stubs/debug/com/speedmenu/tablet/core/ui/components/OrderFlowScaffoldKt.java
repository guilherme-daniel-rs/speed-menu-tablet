package com.speedmenu.tablet.core.ui.components;

import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001az\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\u0015\b\u0002\u0010\f\u001a\u000f\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0007\u00a2\u0006\u0002\b\r2\u0011\u0010\u000e\u001a\r\u0012\u0004\u0012\u00020\u00010\u0007\u00a2\u0006\u0002\b\rH\u0007\u00a8\u0006\u000f"}, d2 = {"OrderFlowScaffold", "", "isConnected", "", "tableNumber", "", "onCallWaiterClick", "Lkotlin/Function0;", "onCartClick", "cartItemCount", "", "enabled", "topLeftContent", "Landroidx/compose/runtime/Composable;", "content", "app_debug"})
public final class OrderFlowScaffoldKt {
    
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
    @androidx.compose.runtime.Composable()
    public static final void OrderFlowScaffold(boolean isConnected, @org.jetbrains.annotations.NotNull()
    java.lang.String tableNumber, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCallWaiterClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCartClick, int cartItemCount, boolean enabled, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> topLeftContent, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
}