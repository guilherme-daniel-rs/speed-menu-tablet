package com.speedmenu.tablet.ui.screens.products;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import com.speedmenu.tablet.R;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;
import com.speedmenu.tablet.ui.screens.home.MenuMockupScenario;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u001a~\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u00a8\u0006\u000f"}, d2 = {"ProductsScreen", "", "categoryName", "", "initialSelectedCategoryId", "navController", "Landroidx/navigation/NavController;", "onNavigateToCart", "Lkotlin/Function0;", "onNavigateToProductDetail", "Lkotlin/Function1;", "onNavigateToCategory", "onNavigateToHome", "cartItemCount", "", "app_debug"})
public final class ProductsScreenKt {
    
    /**
     * Tela de listagem de produtos/pratos de uma categoria.
     * Layout de lista vertical (1 prato por linha) focado em leitura, descrição longa e decisão rápida.
     */
    @androidx.compose.runtime.Composable()
    public static final void ProductsScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String categoryName, @org.jetbrains.annotations.Nullable()
    java.lang.String initialSelectedCategoryId, @org.jetbrains.annotations.Nullable()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToProductDetail, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToCategory, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, int cartItemCount) {
    }
}