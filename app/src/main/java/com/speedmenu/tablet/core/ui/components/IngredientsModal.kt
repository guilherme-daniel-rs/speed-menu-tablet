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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
 * Modal minimalista para exibir lista completa de ingredientes.
 */
@Composable
fun IngredientsModal(
    visible: Boolean,
    ingredients: List<String>,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.75f)) // Scrim escuro
        ) {
            // Card do modal (centralizado)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 560.dp)
                    .fillMaxWidth(0.75f)
                    .heightIn(max = 500.dp)
                    .padding(horizontal = 24.dp)
            ) {
                // Background do card com borda
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.98f),
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
                        .padding(28.dp)
                ) {
                    // Título
                    Text(
                        text = "Ingredientes",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // Lista de ingredientes (scrollável se necessário)
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(ingredients) { ingredient ->
                            IngredientItem(text = ingredient)
                        }
                    }

                    // Botão Fechar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
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
                            .clickable(onClick = onDismiss),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Fechar",
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

/**
 * Item de ingrediente na lista.
 */
@Composable
private fun IngredientItem(
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bullet point discreto
        Box(
            modifier = Modifier
                .width(6.dp)
                .height(6.dp)
                .background(
                    color = SpeedMenuColors.PrimaryLight.copy(alpha = 0.6f),
                    shape = CircleShape
                )
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Texto do ingrediente
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = SpeedMenuColors.TextSecondary,
            fontSize = 15.sp
        )
    }
}

