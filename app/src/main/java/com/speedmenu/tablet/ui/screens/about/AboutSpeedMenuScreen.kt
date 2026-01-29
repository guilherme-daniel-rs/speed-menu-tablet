package com.speedmenu.tablet.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Restaurant
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
            // Conteúdo principal centralizado verticalmente com padding mínimo garantido
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Bloco de conteúdo como unidade vertical
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // ========== 1. HERO DE MARCA (clean) ==========
                    HeroSection()
                    
                    // ========== 2. BENEFÍCIOS COMO DESTAQUES VISUAIS ==========
                    BenefitsSection()
                    
                    // ========== 3. FRASE DE IMPACTO ==========
                    ImpactStatement()
                    
                    // ========== 4. ENCONTRE A GENTE (DESTAQUE) ==========
                    FindUsSection()
                }
                
                // Padding mínimo garantido no final (sempre visível)
                Spacer(modifier = Modifier.height(32.dp))
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
 * Seção "Encontre a gente" com destaque para @ e site.
 * Visual premium, sem interação clicável.
 * Layout responsivo: duas linhas quando há espaço, uma linha quando não há.
 */
@Composable
private fun FindUsSection() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Detectar se há espaço suficiente (altura disponível)
        val hasEnoughSpace = maxHeight > 600.dp
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Título pequeno (label)
            Text(
                text = "Encontre a gente",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
            
            // Bloco de destaque com @ e site
            Surface(
                modifier = Modifier.fillMaxWidth(0.85f),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                tonalElevation = 1.dp
            ) {
                if (hasEnoughSpace) {
                    // Layout em duas linhas (quando há espaço)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // @ do Instagram
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(
                                text = "@speed_menu",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        }
                        
                        // Separador discreto
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            fontSize = 16.sp
                        )
                        
                        // Website
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(
                                text = "www.speedmenu.com.br",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        }
                    }
                } else {
                    // Layout em uma linha (quando não há espaço)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // @ do Instagram
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(
                                text = "@speed_menu",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                        
                        // Separador "/"
                        Text(
                            text = " / ",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            fontSize = 16.sp
                        )
                        
                        // Website
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(
                                text = "www.speedmenu.com.br",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
