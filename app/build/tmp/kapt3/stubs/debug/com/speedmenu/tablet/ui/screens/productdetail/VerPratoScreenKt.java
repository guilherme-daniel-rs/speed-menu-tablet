package com.speedmenu.tablet.ui.screens.productdetail;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a:\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a\u0086\u0001\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u00172\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u00a8\u0006\u001c"}, d2 = {"ProteinChip", "", "text", "", "isSelected", "", "onClick", "Lkotlin/Function0;", "TopBar", "onBackClick", "onHomeClick", "cartItemCount", "", "onCartClick", "VerPratoScreen", "productId", "productName", "productCategory", "productPrice", "", "productImageResId", "productDescription", "ingredients", "", "onNavigateBack", "onNavigateToHome", "onNavigateToCart", "onAddToCart", "app_debug"})
public final class VerPratoScreenKt {
    
    /**
     * Tela de detalhes do prato (VerPratoScreen).
     * Layout minimalista com 2 colunas: imagem à esquerda, personalização à direita.
     */
    @androidx.compose.runtime.Composable()
    public static final void VerPratoScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, @org.jetbrains.annotations.NotNull()
    java.lang.String productCategory, double productPrice, int productImageResId, @org.jetbrains.annotations.NotNull()
    java.lang.String productDescription, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ingredients, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddToCart) {
    }
    
    /**
     * TopBar padrão do app.
     */
    @androidx.compose.runtime.Composable()
    private static final void TopBar(kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, kotlin.jvm.functions.Function0<kotlin.Unit> onHomeClick, int cartItemCount, kotlin.jvm.functions.Function0<kotlin.Unit> onCartClick) {
    }
    
    /**
     * Chip de proteína selecionável (menor, mais sutil) com ícone check quando selecionado.
     */
    @androidx.compose.runtime.Composable()
    private static final void ProteinChip(java.lang.String text, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}