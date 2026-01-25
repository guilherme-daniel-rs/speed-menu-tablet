package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Dialog de confirmação para remover ingrediente base (de 1 para 0).
 */
@Composable
fun RemoveBaseIngredientDialog(
    visible: Boolean,
    ingredientName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        // Scrim escuro
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.82f))
        ) {
            // Card do dialog (centralizado)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 560.dp)
                    .fillMaxWidth(0.75f)
                    .heightIn(max = 320.dp)
                    .padding(horizontal = 24.dp)
            ) {
                // Background do card com borda
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.95f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .drawBehind {
                            // Borda branca sutil
                            drawRoundRect(
                                color = Color.White.copy(alpha = 0.12f),
                                style = Stroke(width = 1.dp.toPx()),
                                cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx())
                            )
                        }
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = Color.Black.copy(alpha = 0.35f),
                            ambientColor = Color.Black.copy(alpha = 0.18f)
                        )
                )

                // Conteúdo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Título
                    Text(
                        text = "Remover ingrediente?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 22.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    // Mensagem
                    Text(
                        text = "Tem certeza que deseja remover '$ingredientName' do prato?",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        color = SpeedMenuColors.TextSecondary.copy(alpha = 0.75f),
                        fontSize = 15.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Botões
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botão Cancelar (secundário)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .background(
                                    color = SpeedMenuColors.Surface.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .clickable(onClick = onDismiss),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cancelar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SpeedMenuColors.TextSecondary,
                                fontSize = 16.sp
                            )
                        }

                        // Botão Confirmar (primário)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            SpeedMenuColors.Primary,
                                            SpeedMenuColors.PrimaryDark
                                        )
                                    ),
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .clickable(onClick = onConfirm),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Remover",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SpeedMenuColors.TextOnPrimary,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

