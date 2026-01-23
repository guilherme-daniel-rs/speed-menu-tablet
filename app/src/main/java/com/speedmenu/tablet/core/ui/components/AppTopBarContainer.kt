package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp

/**
 * Container de topo padrão para todas as telas.
 * Garante altura fixa de 72dp e posicionamento consistente do status pill.
 * Estrutura Row para evitar que o conteúdo avance por trás do pill.
 * 
 * @param content Conteúdo da topbar (ex: menu de categorias) - deve usar Modifier.weight(1f)
 * @param statusPill Componente de status no topo direito (TopRightStatusPill)
 */
@Composable
fun AppTopBarContainer(
    content: @Composable () -> Unit,
    statusPill: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Área esquerda: categorias (scroll horizontal) - nunca avança por trás do pill
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clipToBounds()
        ) {
            content()
        }
        
        // Espaçamento entre categorias e pill
        Spacer(modifier = Modifier.width(16.dp))
        
        // Área direita: TopRightStatusPill (fixo, largura medida)
        statusPill()
    }
}

