package com.speedmenu.tablet.ui.screens.aiassistant

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.focusable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Campo de input do chat fixo no rodapé.
 */
@Composable
fun ChatInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) {
            SpeedMenuColors.Primary.copy(alpha = 0.6f)
        } else {
            SpeedMenuColors.BorderSubtle.copy(alpha = 0.4f)
        },
        animationSpec = tween(200),
        label = "input_border"
    )

    val glowIntensity by animateFloatAsState(
        targetValue = if (isFocused && text.isNotEmpty()) 0.3f else 0f,
        animationSpec = tween(300),
        label = "glow_intensity"
    )

    val hasText = text.isNotBlank()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SpeedMenuColors.BackgroundPrimary)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Campo de texto
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .shadow(
                        elevation = if (isFocused) 8.dp else 4.dp,
                        shape = RoundedCornerShape(28.dp),
                        spotColor = SpeedMenuColors.Primary.copy(alpha = glowIntensity),
                        ambientColor = SpeedMenuColors.Primary.copy(alpha = glowIntensity * 0.5f)
                    )
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .border(
                        width = if (isFocused) 1.5.dp else 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(28.dp)
                    )
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .focusRequester(focusRequester)
                        .focusable(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (hasText && enabled) {
                                onSend()
                            }
                        }
                    ),
                    interactionSource = interactionSource,
                    enabled = enabled,
                    maxLines = 3,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = "Pergunte sobre pratos, bebidas, combinações...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = SpeedMenuColors.TextTertiary,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            // Botão de enviar
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        brush = if (hasText && enabled) {
                            Brush.verticalGradient(
                                colors = listOf(
                                    SpeedMenuColors.PrimaryLight,
                                    SpeedMenuColors.Primary
                                )
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(
                                    SpeedMenuColors.Disabled,
                                    SpeedMenuColors.Disabled.copy(alpha = 0.8f)
                                )
                            )
                        },
                        shape = CircleShape
                    )
                    .clickable(
                        enabled = hasText && enabled,
                        onClick = onSend
                    )
                    .shadow(
                        elevation = if (hasText && enabled) 6.dp else 0.dp,
                        shape = CircleShape,
                        spotColor = SpeedMenuColors.Primary.copy(alpha = 0.3f),
                        ambientColor = SpeedMenuColors.Primary.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar",
                    tint = if (hasText && enabled) {
                        SpeedMenuColors.TextOnPrimary
                    } else {
                        SpeedMenuColors.TextTertiary.copy(alpha = 0.5f)
                    },
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

