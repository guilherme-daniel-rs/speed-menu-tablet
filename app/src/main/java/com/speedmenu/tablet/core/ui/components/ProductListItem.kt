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
import androidx.compose.foundation.layout.fillMaxHeight
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
 * Item de lista vertical de produto/prato.
 * Layout horizontal otimizado para leitura, com descrição longa e decisão rápida.
 * Foco em 1 prato por linha, imagem à esquerda e informações à direita.
 */
@Composable
fun ProductListItem(
    name: String,
    description: String,
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
        targetValue = if (isPressed) 1.02f else 1f,
        animationSpec = tween(200),
        label = "image_scale"
    )
    
    // Animação de sombra (mais intensa quando pressionado)
    val shadowElevation by animateFloatAsState(
        targetValue = if (isPressed) 8f else 2f,
        animationSpec = tween(200),
        label = "shadow_elevation"
    )
    
    // Altura fixa da foto define a altura do card
    // Altura confortável para card premium com respiração visual
    val photoHeight = 180.dp
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(photoHeight) // Altura fixa definida pela foto
            .shadow(
                elevation = shadowElevation.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = SpeedMenuColors.Overlay.copy(alpha = if (isPressed) 0.3f else 0.2f),
                ambientColor = SpeedMenuColors.Overlay.copy(alpha = if (isPressed) 0.15f else 0.1f)
            )
            .background(
                color = SpeedMenuColors.Surface,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 24.dp), // Padding generoso para card premium
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.Top
    ) {
        // ========== 1) FOTO (LADO ESQUERDO) - DEFINE ALTURA DO CARD ==========
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(photoHeight) // Altura fixa que define o card
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(imageScale)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Overlay gradiente escuro sutil (de baixo para cima)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                androidx.compose.ui.graphics.Color.Transparent,
                                SpeedMenuColors.Overlay.copy(alpha = 0.2f),
                                SpeedMenuColors.Overlay.copy(alpha = 0.4f),
                                SpeedMenuColors.Overlay.copy(alpha = 0.6f)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            )
            
            // Badge emocional opcional (canto superior direito)
            badgeText?.let { text ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = SpeedMenuColors.Primary.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextOnPrimary,
                            fontSize = 10.sp,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }
        }
        
        // ========== 2) CONTEÚDO (CENTRO) - MESMA ALTURA DA FOTO ==========
        // Box com altura fixa igual à foto para ancorar o preço à base
        Box(
            modifier = Modifier
                .weight(1f)
                .height(photoHeight) // EXATAMENTE a mesma altura da foto
        ) {
            // Nome e descrição em fluxo normal (podem crescer, mas limitados pela altura do Box)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 1. Nome → sempre no topo à direita da foto
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpeedMenuColors.TextPrimary,
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    letterSpacing = (-0.3).sp
                )
                
                // 2. Descrição → logo abaixo do nome (pode quebrar linha)
                // Pode crescer, mas não empurra o preço (que está ancorado)
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    letterSpacing = 0.1.sp
                )
            }
            
            // 3. Preço → SEMPRE abaixo da descrição, mas:
            // - alinhado à BASE da foto (posição fixa)
            // - nunca sobe, nunca desce
            // - ancorado ao bottom do Box (que tem a mesma altura da foto)
            Text(
                text = CurrencyFormatter.formatCurrencyBR(price),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SpeedMenuColors.PrimaryLight,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.BottomStart) // Ancorado à base (alinhado à base da foto)
            )
        }
        
        // ========== 3) AÇÃO (LADO DIREITO) - BOTÃO FIXO NO CANTO INFERIOR DIREITO ==========
        Box(
            modifier = Modifier
                .height(photoHeight) // Mesma altura da foto
                .fillMaxHeight()
        ) {
            // 4. Botão "Selecionar" → canto inferior direito do card
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Ancorado ao canto inferior direito
                    .height(48.dp)
                    .background(
                        color = SpeedMenuColors.Primary.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(onClick = onSelectClick)
                    .padding(horizontal = 24.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Selecionar",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = SpeedMenuColors.PrimaryLight,
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

