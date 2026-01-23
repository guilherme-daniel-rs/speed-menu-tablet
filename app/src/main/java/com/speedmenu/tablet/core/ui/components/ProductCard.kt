package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.core.utils.CurrencyFormatter

/**
 * Card minimalista de produto/prato.
 * Foco em imagem, nome, preço e ação simples.
 */
@Composable
fun ProductCard(
    name: String,
    price: Double,
    imageResId: Int,
    onClick: () -> Unit,
    onSelectClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeText: String? = null // Badge opcional para destaque emocional
) {
    // Interação para animações de hover/press
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animação de escala da imagem (leve aumento quando pressionado)
    val imageScale by animateFloatAsState(
        targetValue = if (isPressed) 1.03f else 1f,
        animationSpec = tween(200),
        label = "image_scale"
    )
    
    // Animação de sombra (mais intensa quando pressionado)
    val shadowElevation by animateFloatAsState(
        targetValue = if (isPressed) 12f else 4f,
        animationSpec = tween(200),
        label = "shadow_elevation"
    )
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = shadowElevation.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = SpeedMenuColors.Overlay.copy(alpha = if (isPressed) 0.4f else 0.3f),
                ambientColor = SpeedMenuColors.Overlay.copy(alpha = if (isPressed) 0.2f else 0.15f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        // ========== IMAGEM DO PRATO ==========
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(imageScale) // Animação de escala suave
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Overlay gradiente escuro sutil (de baixo para cima) - efeito cinematográfico
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                androidx.compose.ui.graphics.Color.Transparent, // Topo: totalmente transparente
                                SpeedMenuColors.Overlay.copy(alpha = 0.15f), // 25%: overlay muito sutil
                                SpeedMenuColors.Overlay.copy(alpha = 0.35f), // 50%: overlay médio
                                SpeedMenuColors.Overlay.copy(alpha = 0.55f), // 75%: overlay mais forte
                                SpeedMenuColors.Overlay.copy(alpha = 0.7f)   // Base: overlay forte para legibilidade
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            )
            
            // Badge emocional opcional (canto superior direito)
            badgeText?.let { text ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = SpeedMenuColors.Primary.copy(alpha = 0.85f), // Fundo com boa opacidade
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextOnPrimary,
                            fontSize = 11.sp,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }
        }
        
        // ========== INFORMAÇÕES DO PRATO ==========
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SpeedMenuColors.Surface,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Nome do prato - mais destaque
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpeedMenuColors.TextPrimary,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    letterSpacing = (-0.3).sp
                )
                
                // Preço e botão em linha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Preço - mais discreto
                    Text(
                        text = CurrencyFormatter.formatCurrencyBR(price),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = SpeedMenuColors.PrimaryLight.copy(alpha = 0.85f),
                        fontSize = 16.sp
                    )
                    
                    // Botão "Selecionar" - refinado para melhor contraste e área de toque
                    Box(
                        modifier = Modifier
                            .height(44.dp) // Altura aumentada para área de toque confortável em tablet
                            .background(
                                color = SpeedMenuColors.Primary.copy(alpha = 0.25f), // Contraste melhorado (0.2f -> 0.25f)
                                shape = RoundedCornerShape(10.dp) // Cantos ligeiramente mais arredondados
                            )
                            .clickable(onClick = onSelectClick)
                            .padding(horizontal = 20.dp, vertical = 0.dp) // Padding horizontal aumentado, vertical controlado pela altura
                    ) {
                        Text(
                            text = "Selecionar",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold, // Peso aumentado para melhor contraste
                            color = SpeedMenuColors.PrimaryLight,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.Center) // Centralização vertical explícita
                        )
                    }
                }
            }
        }
    }
}

