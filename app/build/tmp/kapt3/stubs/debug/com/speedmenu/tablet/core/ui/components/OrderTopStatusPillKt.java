package com.speedmenu.tablet.core.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2 = {"OrderTopStatusPill", "", "isConnected", "", "tableNumber", "", "onCallWaiterClick", "Lkotlin/Function0;", "enabled", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class OrderTopStatusPillKt {
    
    /**
     * Componente reutilizável de status no topo direito para o fluxo de pedidos.
     * Exibe: Status de conexão, Número da mesa e botão para chamar garçom.
     *
     * Características:
     * - 100% reutilizável e independente de layout específico
     * - Posicionamento consistente (topo direito com padding)
     * - Visual premium com fundo translúcido e cantos arredondados
     * - Acessível com áreas clicáveis confortáveis e contentDescription
     * - Micro-interações suaves no botão de garçom
     *
     * @param isConnected Status da conexão (true = conectado, false = desconectado)
     * @param tableNumber Número da mesa (String ou Int, será formatado como "Mesa {number}")
     * @param onCallWaiterClick Callback quando o botão "Garçom" é clicado
     * @param enabled Se o botão de garçom está habilitado (padrão: true)
     * @param modifier Modifier para customização adicional
     */
    @androidx.compose.runtime.Composable()
    public static final void OrderTopStatusPill(boolean isConnected, @org.jetbrains.annotations.NotNull()
    java.lang.String tableNumber, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCallWaiterClick, boolean enabled, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}