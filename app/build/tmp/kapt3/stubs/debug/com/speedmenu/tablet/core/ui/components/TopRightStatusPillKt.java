package com.speedmenu.tablet.core.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a<\u0010\u0000\u001a\u00020\u00012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\t"}, d2 = {"TopRightStatusPill", "", "onWaiterClick", "Lkotlin/Function0;", "onCartClick", "cartItemCount", "", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class TopRightStatusPillKt {
    
    /**
     * Componente reutilizável de status no topo direito.
     * Exibe: Conectado, Mesa, botão Garçom e botão Pedido.
     * Posicionamento pixel-perfect consistente em todas as telas.
     *
     * @param onWaiterClick Callback quando o botão "Garçom" é clicado
     * @param onCartClick Callback quando o botão "Pedido" é clicado
     * @param cartItemCount Quantidade de itens no carrinho (0 = carrinho vazio, sem badge)
     * @param modifier Modifier para customização adicional
     */
    @androidx.compose.runtime.Composable()
    public static final void TopRightStatusPill(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onWaiterClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCartClick, int cartItemCount, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}