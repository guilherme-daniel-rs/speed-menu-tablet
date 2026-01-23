package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Imagem hero grande com rounded corners e leve scrim para leitura.
 */
@Composable
fun HeroImage(
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    var imageLoaded by remember { mutableStateOf(false) }
    val imageAlpha by animateFloatAsState(
        targetValue = if (imageLoaded) 1f else 0f,
        animationSpec = tween(300, easing = LinearEasing),
        label = "image_alpha"
    )
    
    // Shimmer discreto enquanto carrega
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_offset"
    )
    
    LaunchedEffect(imageResId) {
        // Simula carregamento da imagem
        kotlinx.coroutines.delay(100)
        imageLoaded = true
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        // Placeholder com shimmer discreto
        if (!imageLoaded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF1A1F26), // Surface escuro
                                Color(0xFF232830).copy(alpha = 0.3f + shimmerOffset * 0.2f), // Shimmer sutil
                                Color(0xFF1A1F26)
                            ),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(400f * shimmerOffset, 400f * shimmerOffset)
                        )
                    )
            )
        }
        
        // Imagem principal com crossfade
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .alpha(imageAlpha),
            contentScale = ContentScale.Crop
        )
        
        // Scrim leve para leitura (gradiente de baixo para cima)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x40000000) // Preto 25% opacidade na base
                        )
                    )
                )
        )
    }
}

