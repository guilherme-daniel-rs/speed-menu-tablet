package com.speedmenu.tablet.core.ui.components;

import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a8\u0010\u0000\u001a\u00020\u00012\u0011\u0010\u0002\u001a\r\u0012\u0004\u0012\u00020\u00010\u0003\u00a2\u0006\u0002\b\u00042\u0011\u0010\u0005\u001a\r\u0012\u0004\u0012\u00020\u00010\u0003\u00a2\u0006\u0002\b\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u00a8\u0006\b"}, d2 = {"AppTopBarContainer", "", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "statusPill", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class AppTopBarContainerKt {
    
    /**
     * Container de topo padrão para todas as telas.
     * Garante altura fixa de 72dp e posicionamento consistente do status pill.
     * Estrutura Row para evitar que o conteúdo avance por trás do pill.
     *
     * @param content Conteúdo da topbar (ex: menu de categorias) - deve usar Modifier.weight(1f)
     * @param statusPill Componente de status no topo direito (TopRightStatusPill)
     */
    @androidx.compose.runtime.Composable()
    public static final void AppTopBarContainer(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> statusPill, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}