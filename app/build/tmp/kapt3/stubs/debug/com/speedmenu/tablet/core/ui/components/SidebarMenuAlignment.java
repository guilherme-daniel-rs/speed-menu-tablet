package com.speedmenu.tablet.core.ui.components;

import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

/**
 * Constantes de alinhamento horizontal para o menu lateral.
 * Garantem que todos os itens usem a mesma régua horizontal.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\b\u001a\u00020\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\n"}, d2 = {"Lcom/speedmenu/tablet/core/ui/components/SidebarMenuAlignment;", "", "()V", "ContentStartPadding", "Landroidx/compose/ui/unit/Dp;", "getContentStartPadding-D9Ej5fM", "()F", "F", "IconTextSpacing", "getIconTextSpacing-D9Ej5fM", "app_debug"})
final class SidebarMenuAlignment {
    
    /**
     * PaddingStart do conteúdo (ícone + texto) - mesma régua para todos os itens
     */
    private static final float ContentStartPadding = 0.0F;
    
    /**
     * Espaçamento entre ícone e texto - consistente em todos os itens
     */
    private static final float IconTextSpacing = 0.0F;
    @org.jetbrains.annotations.NotNull()
    public static final com.speedmenu.tablet.core.ui.components.SidebarMenuAlignment INSTANCE = null;
    
    private SidebarMenuAlignment() {
        super();
    }
}