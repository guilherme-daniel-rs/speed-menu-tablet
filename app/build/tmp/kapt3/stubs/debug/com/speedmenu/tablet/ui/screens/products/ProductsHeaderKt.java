package com.speedmenu.tablet.ui.screens.products;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2 = {"ProductsHeader", "", "categoryName", "", "productCount", "", "cartItemCount", "onCartClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class ProductsHeaderKt {
    
    /**
     * Header da tela de produtos.
     * Contém título, subtítulo e botão de carrinho (CTA oficial e único de acesso ao carrinho).
     * Botão "Voltar para categorias" removido - navegação apenas via sidebar.
     */
    @androidx.compose.runtime.Composable()
    public static final void ProductsHeader(@org.jetbrains.annotations.NotNull()
    java.lang.String categoryName, int productCount, int cartItemCount, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCartClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}