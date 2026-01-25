package com.speedmenu.tablet.ui.screens.home;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.ui.Alignment;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a@\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0003\u001a\u0012\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0003\u001a\u001a\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0003\u00a8\u0006\u0016"}, d2 = {"CategoryItem", "", "category", "Lcom/speedmenu/tablet/ui/screens/home/MenuCategory;", "isSelected", "", "onClick", "Lkotlin/Function0;", "OrderFlowSidebar", "topics", "", "Lcom/speedmenu/tablet/ui/screens/home/MenuTopic;", "selectedCategoryId", "", "onCategoryClick", "Lkotlin/Function1;", "modifier", "Landroidx/compose/ui/Modifier;", "TopicDivider", "TopicDividerLine", "TopicTitle", "text", "app_debug"})
public final class OrderFlowSidebarKt {
    
    /**
     * Sidebar hierárquico para o fluxo "Iniciar pedido" em diante.
     * Exibe tópicos e categorias de forma organizada.
     */
    @androidx.compose.runtime.Composable()
    public static final void OrderFlowSidebar(@org.jetbrains.annotations.NotNull()
    java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> topics, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedCategoryId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCategoryClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Item de categoria clicável com highlight sutil quando selecionado.
     * Inclui transições suaves para fundo, barra lateral e cor do texto.
     * Feedback visual imediato ao clicar (ripple + mudança de cor).
     */
    @androidx.compose.runtime.Composable()
    private static final void CategoryItem(com.speedmenu.tablet.ui.screens.home.MenuCategory category, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * Linha divisória sutil para tópicos, desenhada com Canvas para garantir visibilidade.
     */
    @androidx.compose.runtime.Composable()
    private static final void TopicDividerLine(androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Título do tópico com linhas sutis à esquerda e direita.
     */
    @androidx.compose.runtime.Composable()
    private static final void TopicTitle(java.lang.String text, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Divisória sutil entre tópicos.
     */
    @androidx.compose.runtime.Composable()
    private static final void TopicDivider() {
    }
}