package com.speedmenu.tablet.ui.screens.home;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001a\u0012\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0003\u001a\u0018\u0010\u0005\u001a\u00020\u00012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\b\u0010\b\u001a\u00020\u0001H\u0003\u001a,\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0001\u001a\u0012\u0010\r\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0003\u00a8\u0006\u000e"}, d2 = {"HomeBanner", "", "HomeContent", "modifier", "Landroidx/compose/ui/Modifier;", "HomeScreen", "onNavigateToCategories", "Lkotlin/Function0;", "RestaurantLogo", "Sidebar", "isVisible", "", "onStartOrderClick", "TopRightInfo", "app_debug"})
public final class HomeScreenKt {
    
    /**
     * Tela Home do SpeedMenuTablet.
     * Layout dividido em sidebar fixa à esquerda e conteúdo principal à direita.
     * Design pensado para tablets em modo quiosque.
     */
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCategories) {
    }
    
    /**
     * Sidebar fixa com logo, CTA principal e itens de menu.
     * Elemento forte de identidade visual com gradiente vertical sutil e continuidade com o ambiente.
     * Compartilhada entre Home e Categories.
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
    private static final void HomeContent(androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Banner principal com imagem de fundo e texto de destaque.
     * Fundo sofisticado com múltiplas camadas visuais para profundidade.
     */
    @androidx.compose.runtime.Composable()
    private static final void HomeBanner() {
    }
    
    /**
     * Widget de status do sistema no topo direito.
     * Comunica estado de conexão, mesa e ações rápidas.
     * Aparência de widget de status profissional.
     */
    @androidx.compose.runtime.Composable()
    private static final void TopRightInfo(androidx.compose.ui.Modifier modifier) {
    }
}