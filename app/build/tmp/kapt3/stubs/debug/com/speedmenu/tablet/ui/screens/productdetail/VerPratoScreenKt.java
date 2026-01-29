package com.speedmenu.tablet.ui.screens.productdetail;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.window.DialogProperties;
import com.speedmenu.tablet.domain.model.CartItem;
import com.speedmenu.tablet.domain.model.CartItemOptions;
import com.speedmenu.tablet.ui.viewmodel.CartViewModel;
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;
import com.speedmenu.tablet.core.utils.CurrencyFormatter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aR\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a2\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\u001a\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0003\u001a\u00a0\u0001\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u000e2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0014\b\u0002\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u00a8\u0006$"}, d2 = {"IngredientsModal", "", "ingredients", "", "Lcom/speedmenu/tablet/ui/screens/productdetail/IngredientQuantity;", "onDismiss", "Lkotlin/Function0;", "onQuantityChange", "Lkotlin/Function2;", "", "onRemoveBaseIngredient", "Lkotlin/Function1;", "ObservationsModal", "observations", "", "onSave", "ProductImage", "imageResId", "modifier", "Landroidx/compose/ui/Modifier;", "VerPratoScreen", "productId", "productName", "productCategory", "productPrice", "", "productImageResId", "productDescription", "cartViewModel", "Lcom/speedmenu/tablet/ui/viewmodel/CartViewModel;", "navController", "Landroidx/navigation/NavController;", "onNavigateBack", "onNavigateToHome", "onNavigateToCart", "onAddToCart", "app_debug"})
public final class VerPratoScreenKt {
    
    /**
     * Tela de detalhes do prato (VerPratoScreen).
     * Layout minimalista com 2 colunas: imagem à esquerda, personalização à direita.
     */
    @kotlin.Suppress(names = {"UNUSED_PARAMETER"})
    @androidx.compose.runtime.Composable()
    public static final void VerPratoScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, @org.jetbrains.annotations.NotNull()
    java.lang.String productCategory, double productPrice, int productImageResId, @org.jetbrains.annotations.NotNull()
    java.lang.String productDescription, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ingredients, @org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.ui.viewmodel.CartViewModel cartViewModel, @org.jetbrains.annotations.Nullable()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAddToCart) {
    }
    
    /**
     * Modal com lista completa de ingredientes (scrollável dentro do modal).
     */
    @androidx.compose.runtime.Composable()
    private static final void IngredientsModal(java.util.List<com.speedmenu.tablet.ui.screens.productdetail.IngredientQuantity> ingredients, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> onQuantityChange, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onRemoveBaseIngredient) {
    }
    
    /**
     * Modal para adicionar observações.
     */
    @androidx.compose.runtime.Composable()
    private static final void ObservationsModal(java.lang.String observations, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSave) {
    }
    
    /**
     * Imagem do prato com altura fixa e rounded corners.
     */
    @androidx.compose.runtime.Composable()
    private static final void ProductImage(int imageResId, androidx.compose.ui.Modifier modifier) {
    }
}