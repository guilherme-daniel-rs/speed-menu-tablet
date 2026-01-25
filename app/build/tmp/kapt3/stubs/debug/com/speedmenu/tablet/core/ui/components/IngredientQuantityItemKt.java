package com.speedmenu.tablet.core.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\\\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\n2\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u00a8\u0006\u000f"}, d2 = {"IngredientQuantityItem", "", "name", "", "quantity", "", "isBase", "", "maxQuantity", "onQuantityChange", "Lkotlin/Function1;", "onRemoveBaseIngredient", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class IngredientQuantityItemKt {
    
    /**
     * Item de ingrediente com controles de quantidade.
     *
     * @param name Nome do ingrediente
     * @param quantity Quantidade atual
     * @param isBase Se true, é ingrediente base (requer confirmação ao remover de 1 para 0)
     * @param maxQuantity Quantidade máxima permitida
     * @param onQuantityChange Callback quando a quantidade muda
     * @param onRemoveBaseIngredient Callback quando tenta remover ingrediente base (1 -> 0). Se null, remove direto.
     * @param modifier Modifier
     */
    @androidx.compose.runtime.Composable()
    public static final void IngredientQuantityItem(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int quantity, boolean isBase, int maxQuantity, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onQuantityChange, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemoveBaseIngredient, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}