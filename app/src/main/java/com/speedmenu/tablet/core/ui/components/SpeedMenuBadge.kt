package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Badge / Chip do Design System.
 * Usado para status, informações discretas (ex: Mesa 12, tempo médio).
 */
@Composable
fun SpeedMenuBadge(
    text: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
) {
    Box(
        modifier = modifier
            .background(
                color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(contentPadding)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = SpeedMenuColors.TextSecondary,
            fontSize = 13.sp
        )
    }
}

