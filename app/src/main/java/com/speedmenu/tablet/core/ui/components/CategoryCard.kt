package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Card de categoria do cardápio.
 * Card grande com imagem de fundo, overlay, ícone, textos e botão secundário.
 */
@Composable
fun CategoryCard(
    title: String,
    subtitle: String,
    description: String,
    icon: ImageVector,
    imageResId: Int,
    onClick: () -> Unit,
    onViewItemsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .clickable(onClick = onClick)
    ) {
        // ========== CAMADA 1: Imagem de fundo ==========
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        
        // ========== CAMADA 2: Overlay escuro para contraste ==========
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SpeedMenuColors.Overlay.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                )
        )
        
        // ========== CAMADA 3: Conteúdo do card ==========
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Topo: Subtítulo e ícone
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = SpeedMenuColors.PrimaryLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
                
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SpeedMenuColors.PrimaryLight.copy(alpha = 0.8f),
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Base: Título, descrição e botão
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = SpeedMenuColors.TextPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp
                )
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Botão secundário "Ver pratos →"
                SpeedMenuSecondaryButton(
                    text = "Ver pratos →",
                    onClick = onViewItemsClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

