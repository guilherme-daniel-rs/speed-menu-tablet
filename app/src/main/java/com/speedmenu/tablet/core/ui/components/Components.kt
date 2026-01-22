package com.speedmenu.tablet.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Componentes reutilizáveis da UI.
 * Esta classe será expandida com componentes customizados conforme necessário.
 */

/**
 * Componente de texto placeholder para desenvolvimento.
 */
@Composable
fun PlaceholderText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
    )
}

