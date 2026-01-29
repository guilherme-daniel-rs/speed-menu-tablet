package com.speedmenu.tablet.ui.screens.aiassistant

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Sugestão de pergunta para o chat.
 */
data class ChatSuggestion(
    val id: String,
    val text: String,
    val icon: ImageVector
)

/**
 * Lista de sugestões padrão.
 */
val defaultSuggestions = listOf(
    ChatSuggestion(
        id = "recommend",
        text = "O que você recomenda hoje?",
        icon = Icons.Default.Star
    ),
    ChatSuggestion(
        id = "wine",
        text = "Qual prato combina com vinho?",
        icon = Icons.Default.WineBar
    ),
    ChatSuggestion(
        id = "lactose",
        text = "Tem opções sem lactose?",
        icon = Icons.Default.Restaurant
    ),
    ChatSuggestion(
        id = "popular",
        text = "Qual é o prato mais pedido?",
        icon = Icons.Default.LocalDining
    ),
    ChatSuggestion(
        id = "light",
        text = "Quero algo leve",
        icon = Icons.Default.Lightbulb
    ),
    ChatSuggestion(
        id = "surprise",
        text = "Surpreenda-me 🍽️",
        icon = Icons.Default.AutoAwesome
    )
)

/**
 * Componente de sugestões de perguntas.
 */
@Composable
fun ChatSuggestions(
    suggestions: List<ChatSuggestion> = defaultSuggestions,
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(suggestions) { suggestion ->
            SuggestionChip(
                suggestion = suggestion,
                onClick = { onSuggestionClick(suggestion.text) }
            )
        }
    }
}

/**
 * Chip individual de sugestão.
 */
@Composable
private fun SuggestionChip(
    suggestion: ChatSuggestion,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150),
        label = "chip_scale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isPressed) {
            colorScheme.primary.copy(alpha = 0.8f)
        } else {
            colorScheme.primary.copy(alpha = 0.4f)
        },
        animationSpec = tween(200),
        label = "chip_border"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = colorScheme.surfaceVariant.copy(alpha = 0.6f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = suggestion.icon,
                contentDescription = null,
                tint = colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = suggestion.text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
    }
}

