package com.speedmenu.tablet.ui.screens.home;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorMatrix;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.speedmenu.tablet.R;
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001aH\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a2\u0010\r\u001a\u00020\u00012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001a\b\u0010\u000f\u001a\u00020\u0001H\u0003\u001a,\u0010\u0010\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u00062\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0001\u001a\"\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0001\u00a8\u0006\u0015"}, d2 = {"HomeBanner", "", "HomeContent", "modifier", "Landroidx/compose/ui/Modifier;", "showWaiterDialog", "", "onShowWaiterDialog", "Lkotlin/Function1;", "onNavigateToCart", "Lkotlin/Function0;", "cartItemCount", "", "HomeScreen", "onNavigateToCategories", "RestaurantLogo", "Sidebar", "isVisible", "onStartOrderClick", "TopRightInfo", "onWaiterClick", "app_debug"})
public final class HomeScreenKt {
    
    /**
     * Tela Home do SpeedMenuTablet.
     * Layout dividido em sidebar fixa à esquerda e conteúdo principal à direita.
     * Design pensado para tablets em modo quiosque.
     */
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCategories, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, int cartItemCount) {
    }
    
    /**
     * Sidebar fixa com logo, CTA principal e itens de menu.
     * Elemento forte de identidade visual com gradiente vertical sutil e continuidade com o ambiente.
     */
    @androidx.compose.runtime.Composable()
    public static final void Sidebar(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean isVisible, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartOrderClick) {
    }
    
    /**
     * Logo do restaurante como assinatura de marca.
     * Identidade visual refinada, sem aparência de botão ou ação.
     */
    @androidx.compose.runtime.Composable()
    private static final void RestaurantLogo() {
    }
    
    /**
     * Conteúdo principal da Home com banner e informações.
     */
    @androidx.compose.runtime.Composable()
    private static final void HomeContent(androidx.compose.ui.Modifier modifier, boolean showWaiterDialog, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onShowWaiterDialog, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, int cartItemCount) {
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