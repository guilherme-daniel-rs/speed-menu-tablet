package com.speedmenu.tablet.ui.screens.about

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel

/**
 * Tela institucional do SpeedMenu.
 * Mini landing page de marca: elegante, leve e memorável.
 * Design clean com muito espaço negativo e tipografia como elemento principal.
 */
@Composable
fun AboutSpeedMenuScreen(
    onNavigateBack: () -> Unit = {}
) {
    // WaiterViewModel para o AppTopBar
    val waiterViewModel: WaiterViewModel = hiltViewModel()
    val waiterUiState by waiterViewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            AppTopBar(
                showBackButton = true,
                onBackClick = onNavigateBack,
                isConnected = true,
                tableNumber = "17",
                onCallWaiterClick = {
                    waiterViewModel.requestWaiter("AboutSpeedMenuScreen")
                },
                screenName = "AboutSpeedMenuScreen"
            )
        }
    ) { innerPadding ->
        // Container raiz - fundo liso
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // QR Code do Instagram no canto superior direito
                InstagramQRCode(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                )
                
                // Conteúdo principal centralizado verticalmente
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Bloco de conteúdo como unidade vertical
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // ========== 1. HERO DE MARCA (clean) ==========
                        HeroSection()
                        
                        // ========== 2. BENEFÍCIOS COMO DESTAQUES VISUAIS ==========
                        BenefitsSection()
                        
                        // ========== 3. FRASE DE IMPACTO ==========
                        ImpactStatement()
                    }
                }
            }
        }
    }
}

/**
 * Hero de marca: logo + tipografia forte, sem cards.
 */
@Composable
private fun HeroSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Logo centralizada
        Image(
            painter = painterResource(id = R.drawable.logo_1),
            contentDescription = "Logo SpeedMenu",
            modifier = Modifier.size(180.dp),
            contentScale = ContentScale.Fit
        )
        
        // Texto grande e forte
        Text(
            text = "O jeito mais rápido de pedir no restaurante.",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        // Subtítulo menor
        Text(
            text = "Tecnologia que conecta cliente, cozinha e atendimento.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

/**
 * Benefícios como destaques visuais: ícone + texto, sem cards.
 */
@Composable
private fun BenefitsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Benefício 1: Pedido sem espera
        BenefitItem(
            icon = Icons.Default.Speed,
            title = "Pedido sem espera",
            description = "O cliente pede direto da mesa, sem chamar o garçom",
            modifier = Modifier.weight(1f)
        )
        
        // Benefício 2: Mais eficiência
        BenefitItem(
            icon = Icons.Default.CheckCircle,
            title = "Mais eficiência",
            description = "Menos erros, mais agilidade para a cozinha",
            modifier = Modifier.weight(1f)
        )
        
        // Benefício 3: Experiência moderna
        BenefitItem(
            icon = Icons.Default.Restaurant,
            title = "Experiência moderna",
            description = "Restaurante mais tecnológico e organizado",
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Item de benefício: ícone grande + título + texto pequeno.
 * Sem card, apenas elementos visuais separados por espaço.
 */
@Composable
private fun BenefitItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Ícone grande
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(56.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
        )
        
        // Título
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        
        // Descrição pequena
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f),
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

/**
 * Frase de impacto: texto forte centralizado, sem container.
 */
@Composable
private fun ImpactStatement() {
    Text(
        text = "Menos espera. Menos erro. Mais experiência.",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 30.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    )
}

/**
 * QR Code do Instagram no canto superior direito.
 * Elemento discreto e elegante, não rouba foco do hero.
 */
@Composable
private fun InstagramQRCode(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/speed_menu/"))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    android.util.Log.e("AboutSpeedMenu", "Erro ao abrir URL do Instagram", e)
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Ícone do Instagram + texto pequeno
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                text = "Instagram",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontSize = 11.sp
            )
        }
        
        // QR Code em container claro e discreto
        Surface(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qr_instagram),
                    contentDescription = "QR Code Instagram",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
