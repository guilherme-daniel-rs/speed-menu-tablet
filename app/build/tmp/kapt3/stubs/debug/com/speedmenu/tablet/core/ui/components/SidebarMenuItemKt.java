package com.speedmenu.tablet.core.ui.components;

import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001a<\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a:\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\fH\u0003\u001aN\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\fH\u0007\u00a8\u0006\u0012"}, d2 = {"AiBorderedMenuItem", "", "text", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "PrimaryMenuItem", "enabled", "", "SecondaryMenuItem", "isActive", "SidebarMenuItem", "style", "Lcom/speedmenu/tablet/core/ui/components/SidebarMenuItemStyle;", "app_debug"})
public final class SidebarMenuItemKt {
    
    /**
     * Componente base unificado para itens do menu lateral.
     * Garante alinhamento, espaçamento e comportamento consistentes.
     *
     * @param text Texto do item
     * @param icon Ícone do item (obrigatório para SECONDARY, opcional para PRIMARY)
     * @param onClick Ação ao clicar
     * @param style Estilo do item (PRIMARY ou SECONDARY)
     * @param modifier Modifier customizado
     * @param enabled Se o item está habilitado (apenas para PRIMARY)
     * @param isActive Se o item está ativo (apenas para SECONDARY)
     */
    @androidx.compose.runtime.Composable()
    public static final void SidebarMenuItem(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.Nullable()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle style, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean enabled, boolean isActive) {
    }
    
    /**
     * Item primário do menu lateral (CTA principal).
     * Visual forte com sombras, gradientes e animações.
     */
    @androidx.compose.runtime.Composable()
    private static final void PrimaryMenuItem(java.lang.String text, androidx.compose.ui.graphics.vector.ImageVector icon, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier, boolean enabled) {
    }
    
    /**
     * Item secundário do menu lateral (navegação).
     * Estilo editorial discreto e premium.
     */
    @androidx.compose.runtime.Composable()
    private static final void SecondaryMenuItem(java.lang.String text, androidx.compose.ui.graphics.vector.ImageVector icon, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier, boolean isActive) {
    }
    
    /**
     * Item de menu para IA.
     * Design clean e discreto, integrado ao menu sem destaque visual extra.
     */
    @androidx.compose.runtime.Composable()
    private static final void AiBorderedMenuItem(java.lang.String text, androidx.compose.ui.graphics.vector.ImageVector icon, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
}