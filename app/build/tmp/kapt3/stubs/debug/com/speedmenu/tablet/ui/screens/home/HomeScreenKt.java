package com.speedmenu.tablet.ui.screens.home;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import android.util.Log;
import androidx.compose.material3.DrawerValue;
import com.speedmenu.tablet.R;
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\u001a0\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\b\u0010\t\u001a\u00020\u0001H\u0003\u001a\u0012\u0010\n\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001ar\u0010\r\u001a\u00020\u00012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u001aZ\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b#\u0010$\u001a*\u0010%\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0001\u001a&\u0010&\u001a\u00020\u00012\b\b\u0002\u0010\'\u001a\u00020\u001f2\b\b\u0002\u0010(\u001a\u00020\u001fH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010*\u001a\"\u0010+\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\f2\u000e\b\u0002\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0001\u001a\u001a\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u001fH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b0\u00101\u001aT\u00102\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00068"}, d2 = {"DrawerContent", "", "menuItems", "", "Lcom/speedmenu/tablet/ui/screens/home/HomeMenuItem;", "isVisible", "", "onCloseDrawer", "Lkotlin/Function0;", "HomeBanner", "HomeContent", "modifier", "Landroidx/compose/ui/Modifier;", "HomeScreen", "onNavigateToCategories", "onNavigateToCart", "onNavigateToViewOrder", "onNavigateToRatePlace", "onNavigateToGames", "onNavigateToAiAssistant", "cartItemCount", "", "ResponsiveSidebarMenuItem", "text", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "onClick", "style", "Lcom/speedmenu/tablet/core/ui/components/SidebarMenuItemStyle;", "itemHeight", "Landroidx/compose/ui/unit/Dp;", "iconSize", "fontSize", "Landroidx/compose/ui/unit/TextUnit;", "ResponsiveSidebarMenuItem-rkazlNE", "(Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;Lkotlin/jvm/functions/Function0;Lcom/speedmenu/tablet/core/ui/components/SidebarMenuItemStyle;FFJLandroidx/compose/ui/Modifier;)V", "Sidebar", "SidebarHeader", "logoHeight", "headerHeight", "SidebarHeader-YgX7TsA", "(FF)V", "TopRightInfo", "onWaiterClick", "calculateResponsiveSizes", "Lcom/speedmenu/tablet/ui/screens/home/ResponsiveSizes;", "maxHeight", "calculateResponsiveSizes-0680j_4", "(F)Lcom/speedmenu/tablet/ui/screens/home/ResponsiveSizes;", "rememberHomeMenuItems", "onStartOrderClick", "onViewOrderClick", "onRatePlaceClick", "onGamesClick", "onAiAssistantClick", "app_debug"})
public final class HomeScreenKt {
    
    /**
     * FONTE ÚNICA DE VERDADE: Lista de itens do menu da Home.
     * Esta lista é compartilhada entre todos os layouts (sidebar fixa, drawer, etc.)
     * para garantir consistência e evitar duplicação.
     */
    @androidx.compose.runtime.Composable()
    private static final java.util.List<com.speedmenu.tablet.ui.screens.home.HomeMenuItem> rememberHomeMenuItems(kotlin.jvm.functions.Function0<kotlin.Unit> onStartOrderClick, kotlin.jvm.functions.Function0<kotlin.Unit> onViewOrderClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRatePlaceClick, kotlin.jvm.functions.Function0<kotlin.Unit> onGamesClick, kotlin.jvm.functions.Function0<kotlin.Unit> onAiAssistantClick) {
        return null;
    }
    
    /**
     * Tela Home do SpeedMenuTablet.
     * Layout responsivo: sidebar fixa para tablets (Expanded) e drawer para celulares (Compact/Medium).
     */
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCategories, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToViewOrder, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToRatePlace, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToGames, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAiAssistant, int cartItemCount) {
    }
    
    /**
     * Sidebar fixa com logo, CTA principal e itens de menu.
     * Elemento forte de identidade visual com gradiente vertical sutil e continuidade com o ambiente.
     * Recebe a lista de itens como parâmetro (fonte única de verdade).
     * Layout responsivo SEM scroll - sempre mostra os 5 itens adaptando-se à altura disponível.
     */
    @androidx.compose.runtime.Composable()
    public static final void Sidebar(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean isVisible, @org.jetbrains.annotations.NotNull()
    java.util.List<com.speedmenu.tablet.ui.screens.home.HomeMenuItem> menuItems) {
    }
    
    /**
     * Conteúdo do drawer para layout mobile (celular).
     * Reutiliza a mesma lista de itens do menu (fonte única de verdade).
     * Layout responsivo SEM scroll - sempre mostra os 5 itens adaptando-se à altura disponível.
     */
    @androidx.compose.runtime.Composable()
    private static final void DrawerContent(java.util.List<com.speedmenu.tablet.ui.screens.home.HomeMenuItem> menuItems, boolean isVisible, kotlin.jvm.functions.Function0<kotlin.Unit> onCloseDrawer) {
    }
    
    /**
     * Conteúdo principal da Home com banner e informações.
     */
    @androidx.compose.runtime.Composable()
    private static final void HomeContent(androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Banner principal com carrossel de imagens e texto de destaque.
     * Fundo sofisticado com múltiplas camadas visuais para profundidade.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void HomeBanner() {
    }
    
    /**
     * Widget de status do sistema no topo direito.
     * Comunica estado de conexão, mesa e ações rápidas.
     * Aparência de widget de status profissional.
     * Reutilizável em outras telas.
     */
    @androidx.compose.runtime.Composable()
    public static final void TopRightInfo(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onWaiterClick) {
    }
}