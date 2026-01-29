package com.speedmenu.tablet.ui.screens.qrscanner

import android.Manifest
import android.content.pm.ApplicationInfo
import android.os.Build
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.concurrent.atomic.AtomicBoolean
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.speedmenu.tablet.core.ui.components.OrderPlacedDialog
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.components.ConfirmBillAlertDialog
import com.speedmenu.tablet.core.ui.components.BillRequestedDialog
import com.speedmenu.tablet.ui.viewmodel.CartViewModel
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel
import com.speedmenu.tablet.ui.viewmodel.FinalizationState
import com.speedmenu.tablet.ui.viewmodel.QrScannerViewModel
import com.speedmenu.tablet.ui.viewmodel.ScanState
import com.speedmenu.tablet.ui.viewmodel.ScanState.Error
import com.speedmenu.tablet.ui.viewmodel.ScanState.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import android.util.Log
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Detecta se o app está rodando em um emulador Android.
 * Usa múltiplas verificações para maior confiabilidade.
 */
private fun isRunningOnEmulator(): Boolean {
    return (Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
            || "google_sdk" == Build.PRODUCT
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu"))
}

/**
 * Tela de scanner de QR Code para finalizar pedido.
 * Usa CameraX + ML Kit Barcode Scanning.
 * 
 * MODO MOCK (DEBUG + EMULADOR):
 * - Simula leitura de QR Code após 3 segundos OU
 * - Via gesto oculto: tocar 5x no retângulo de mira
 */
/**
 * Modo de uso do scanner de QR Code.
 */
enum class QrScannerMode {
    /** Modo para finalizar pedido (checkout): mostra carrinho e permite finalizar após escanear */
    CHECKOUT,
    /** Modo para ver pedido da comanda: mostra pedido em modo read-only após escanear */
    VIEW_ORDER
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrScannerScreen(
    onNavigateBack: () -> Unit,
    mode: QrScannerMode = QrScannerMode.VIEW_ORDER,
    onNavigateToViewOrder: (String) -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    qrScannerViewModel: QrScannerViewModel = hiltViewModel(),
    cartViewModel: CartViewModel // Recebe do NavGraph para garantir mesma instância compartilhada
) {
    val context = LocalContext.current
    val uiState by qrScannerViewModel.uiState.collectAsState()
    val cartState by cartViewModel.cartState.collectAsState()
    
    // WaiterViewModel centralizado para gerenciar chamadas de garçom
    val waiterViewModel: WaiterViewModel = hiltViewModel()
    val waiterUiState by waiterViewModel.uiState.collectAsState()
    
    // Inicializa ViewModel apenas com o modo (não precisa copiar itens)
    LaunchedEffect(mode) {
        qrScannerViewModel.initialize(
            mode = mode,
            cartItems = emptyList() // Não usa mais - lê diretamente do cartState
        )
    }
    
    // Estado para gesto oculto (5 toques no retângulo de mira)
    var tapCount by remember { mutableStateOf(0) }
    var lastTapTime by remember { mutableStateOf(0L) }
    var showOrderPlacedDialog by remember { mutableStateOf(false) }
    
    // Estados para pedir conta (VIEW_ORDER)
    var showConfirmBillDialog by remember { mutableStateOf(false) }
    var showBillRequestedDialog by remember { mutableStateOf(false) }
    
    // Detecta se está em modo MOCK (DEBUG + EMULADOR)
    val isMockMode = remember {
        val isDebug = try {
            (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        } catch (e: Exception) {
            false
        }
        isDebug && isRunningOnEmulator()
    }
    
    // Permissão de câmera
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val hasCameraPermission = when (cameraPermissionState.status) {
        is PermissionStatus.Granted -> true
        is PermissionStatus.Denied -> false
        else -> false
    }
    
    // Estados mockados
    val isConnected = true
    val tableNumber = "17"
    
    val colorScheme = MaterialTheme.colorScheme
    
    // Função para simular scan (usada no modo MOCK)
    val simulateScan = {
        if (uiState.scanState !is ScanState.Success) {
            val mockComandaCode = "COMANDA-TESTE-17"
            qrScannerViewModel.onQrCodeScanned(mockComandaCode)
            Log.d("QrScannerScreen", "📱 Scan simulado - comandaCode: $mockComandaCode, modo: $mode")
        }
    }
    
    // Auto-scan após 3 segundos no modo MOCK (apenas se ainda não escaneou)
    if (isMockMode && uiState.scanState !is ScanState.Success) {
        LaunchedEffect(Unit) {
            delay(3000) // 3 segundos
            if (uiState.scanState !is ScanState.Success) {
                simulateScan()
            }
        }
    }
    
    // Auto-finaliza após scan no CHECKOUT (apenas uma vez)
    LaunchedEffect(uiState.scanState, uiState.mode, uiState.finalizationState, cartState.items) {
        if (uiState.mode == QrScannerMode.CHECKOUT 
            && uiState.scanState is ScanState.Success 
            && uiState.finalizationState is FinalizationState.Idle
            && cartState.items.isNotEmpty()
            && uiState.comandaCode != null) {
            // Auto-finaliza quando QRCode é escaneado no CHECKOUT
            // Proteção: só finaliza se ainda está em Idle (não finalizou ainda)
            qrScannerViewModel.finalizeCheckout(cartState.items)
        }
    }
    
    // Mostra dialog de sucesso quando finalização é bem-sucedida
    LaunchedEffect(uiState.finalizationState) {
        if (uiState.mode == QrScannerMode.CHECKOUT 
            && uiState.finalizationState is FinalizationState.Success) {
            showOrderPlacedDialog = true
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        // Top Action Bar
        AppTopBar(
            showBackButton = true,
            onBackClick = onNavigateBack,
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {
                waiterViewModel.requestWaiter("QrScannerScreen")
            },
            screenName = "QrScannerScreen"
        )
        
        // Split View: 40% câmera + 60% carrinho/pedido
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Painel esquerdo (40%) - Câmera / QRCode
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
            ) {
                CameraPanel(
                    hasCameraPermission = hasCameraPermission,
                    scanState = uiState.scanState,
                    isMockMode = isMockMode,
                    onBarcodeDetected = { barcodeValue ->
                        // Proteção: só processa se ainda não escaneou E não está finalizando
                        if (uiState.scanState !is ScanState.Success 
                            && uiState.finalizationState !is FinalizationState.Finalizing
                            && uiState.finalizationState !is FinalizationState.Success
                            && barcodeValue.isNotBlank()) {
                            qrScannerViewModel.onQrCodeScanned(barcodeValue.trim())
                        }
                    },
                    onResetScan = {
                        qrScannerViewModel.resetScan()
                    },
                    onRequestPermission = {
                        cameraPermissionState.launchPermissionRequest()
                    },
                    onMockScan = {
                        simulateScan()
                    },
                    tapCount = tapCount,
                    lastTapTime = lastTapTime,
                    onTapCountChanged = { count, time ->
                        tapCount = count
                        lastTapTime = time
                    }
                )
            }
            
            // Painel direito (60%) - Carrinho / Pedido compacto
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
            ) {
                CompactOrderPanel(
                    mode = mode,
                    // CHECKOUT: usa diretamente cartState.items do CartViewModel compartilhado
                    // VIEW_ORDER: usa orderItems do QrScannerViewModel
                    items = if (mode == QrScannerMode.CHECKOUT) cartState.items else uiState.orderItems,
                    isLoading = uiState.isLoadingOrder,
                    error = uiState.orderError,
                    comandaCode = uiState.comandaCode,
                    // CHECKOUT: sempre passa finalizationState (mesmo que seja Idle inicialmente)
                    // VIEW_ORDER: passa null (não tem finalização)
                    finalizationState = if (mode == QrScannerMode.CHECKOUT) uiState.finalizationState else null,
                    onUpdateQuantity = { itemId, newQuantity ->
                        cartViewModel.updateItemQuantity(itemId, newQuantity)
                    },
                    onRemoveItem = { itemId ->
                        cartViewModel.removeItem(itemId)
                    },
                    onRetryFinalization = {
                        if (mode == QrScannerMode.CHECKOUT) {
                            qrScannerViewModel.retryFinalization(cartState.items)
                        }
                    },
                    // Estados de pedir conta (VIEW_ORDER apenas)
                    billRequested = uiState.billRequested,
                    isRequestingBill = uiState.isRequestingBill,
                    onRequestBill = {
                        showConfirmBillDialog = true
                    }
                )
            }
        }
    }
    
    // Dialog de garçom chamado (gerenciado pelo WaiterViewModel)
    // IMPORTANTE: Deve estar FORA de qualquer condicional para aparecer sempre
    WaiterCalledDialog(
        visible = waiterUiState.showDialog,
        onDismiss = { waiterViewModel.dismissDialog() },
        onConfirm = { waiterViewModel.confirmWaiterCall() }
    )
    
    // Dialog de pedido realizado (apenas no modo CHECKOUT após finalização bem-sucedida)
    if (mode == QrScannerMode.CHECKOUT 
        && showOrderPlacedDialog 
        && uiState.finalizationState is FinalizationState.Success) {
        OrderPlacedDialog(
            visible = showOrderPlacedDialog,
            comandaCode = uiState.comandaCode ?: "",
            onDismiss = {
                showOrderPlacedDialog = false
                onNavigateToHome()
            },
            onGoToHome = {
                showOrderPlacedDialog = false
                onNavigateToHome()
            }
        )
    }
    
    // Dialog de confirmação para pedir conta (VIEW_ORDER apenas)
    if (mode == QrScannerMode.VIEW_ORDER) {
        ConfirmBillAlertDialog(
            visible = showConfirmBillDialog,
            onDismiss = {
                showConfirmBillDialog = false
            },
            onConfirm = {
                showConfirmBillDialog = false
                qrScannerViewModel.requestBill()
            }
        )
    }
    
    // Dialog de conta solicitada (VIEW_ORDER apenas)
    // Mostra quando a conta foi solicitada com sucesso
    LaunchedEffect(uiState.billRequested) {
        if (mode == QrScannerMode.VIEW_ORDER && uiState.billRequested && !uiState.isRequestingBill) {
            showBillRequestedDialog = true
        }
    }
    
    if (mode == QrScannerMode.VIEW_ORDER && showBillRequestedDialog) {
        BillRequestedDialog(
            visible = showBillRequestedDialog,
            comandaCode = uiState.comandaCode,
            onDismiss = {
                showBillRequestedDialog = false
                // Navega para Home após fechar o dialog
                onNavigateToHome()
            },
            onGoToHome = {
                showBillRequestedDialog = false
                // Navega para Home e limpa a stack
                onNavigateToHome()
            }
        )
    }
}

/**
 * Painel esquerdo (40%) - Câmera / QRCode Scanner.
 */
@Composable
private fun CameraPanel(
    hasCameraPermission: Boolean,
    scanState: ScanState,
    isMockMode: Boolean,
    onBarcodeDetected: (String) -> Unit,
    onResetScan: () -> Unit,
    onRequestPermission: () -> Unit,
    onMockScan: () -> Unit,
    tapCount: Int,
    lastTapTime: Long,
    onTapCountChanged: (Int, Long) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { clip = true }
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(
                color = colorScheme.surface.copy(alpha = 0.3f),
                shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            )
    ) {
        when {
            !hasCameraPermission -> {
                // Sem permissão
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Permissão de câmera necessária",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colorScheme.onSurface,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "Para escanear o QRCode da comanda, é necessário permitir o acesso à câmera.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .background(
                                color = colorScheme.primary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(onClick = onRequestPermission)
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Permitir câmera",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            scanState is ScanState.Success -> {
                // Sucesso
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorScheme.tertiaryContainer.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "✓ Comanda detectada",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colorScheme.tertiary,
                            fontSize = 16.sp
                        )
                    }
                    
                    Text(
                        text = "Comanda: ${scanState.comandaCode}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .background(
                                color = colorScheme.primary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(onClick = onResetScan)
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Escanear novamente",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            scanState is ScanState.Error -> {
                // Erro
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorScheme.errorContainer.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "QRCode inválido",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colorScheme.error,
                            fontSize = 16.sp
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .background(
                                color = colorScheme.primary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(onClick = onResetScan)
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Tentar novamente",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            else -> {
                // Escaneando
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { clip = true }
                        .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                ) {
                    CameraPreview(
                        hasCameraPermission = hasCameraPermission,
                        hasScanned = scanState is ScanState.Success,
                        onBarcodeDetected = onBarcodeDetected,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Overlay de mira
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Retângulo de mira
                            Box(
                                modifier = Modifier
                                    .size(200.dp)
                                    .border(
                                        width = 2.dp,
                                        color = colorScheme.primary,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .then(
                                        // Gesto oculto: 5 toques no retângulo (apenas em modo MOCK)
                                        if (isMockMode && scanState !is ScanState.Success) {
                                            Modifier.clickable {
                                                val currentTime = System.currentTimeMillis()
                                                // Reset contador se passou mais de 2 segundos desde o último toque
                                                val newTapCount = if (currentTime - lastTapTime > 2000) {
                                                    1
                                                } else {
                                                    tapCount + 1
                                                }
                                                
                                                val newLastTapTime = currentTime
                                                onTapCountChanged(newTapCount, newLastTapTime)
                                                
                                                // Se tocou 5 vezes, simula scan
                                                if (newTapCount >= 5) {
                                                    onTapCountChanged(0, currentTime)
                                                    onMockScan()
                                                }
                                            }
                                        } else {
                                            Modifier
                                        }
                                    )
                            )
                            
                            // Texto de instrução
                            Text(
                                text = if (scanState is ScanState.Scanning) {
                                    "Lendo comanda..."
                                } else {
                                    "Aponte para o QRCode da comanda"
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = colorScheme.onSurface,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .background(
                                        color = colorScheme.background.copy(alpha = 0.8f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Preview da câmera com análise de barcode.
 * 
 * CORREÇÕES PARA TELA PRETA EM SAMSUNG (LANDSCAPE):
 * 1. PreviewView configurado com scaleType FILL_CENTER e implementationMode COMPATIBLE
 * 2. Bind apenas quando permissão concedida e previewView existe (LaunchedEffect com keys)
 * 3. unbindAll() antes de bind e cleanup adequado ao sair
 * 4. CameraSelector explícito com requireLensFacing(BACK)
 * 5. setTargetRotation para Preview e ImageAnalysis (landscape)
 * 6. Logs úteis para debug
 * 
 * OTIMIZAÇÕES DE PERFORMANCE:
 * - Executor único reutilizável para analyzer (não recriado a cada recomposition)
 * - Executor separado para ProcessCameraProvider (background thread)
 * - BarcodeScanner criado uma única vez
 * - LaunchedEffect para bind/unbind adequado da câmera
 * - Proteção contra múltiplos frames simultâneos
 * - Sempre fecha imageProxy (inclusive em erros)
 */
@Composable
private fun CameraPreview(
    hasCameraPermission: Boolean,
    hasScanned: Boolean,
    onBarcodeDetected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    // Executor único para analyzer (não recriado a cada recomposition)
    // IMPORTANTE: analyzer roda em background, mas PreviewView/surfaceProvider deve rodar na main
    val analyzerExecutor = remember {
        Executors.newSingleThreadExecutor()
    }
    
    // Main executor para operações que precisam rodar na main thread
    val mainExecutor = remember {
        ContextCompat.getMainExecutor(context)
    }
    
    // BarcodeScanner criado UMA ÚNICA VEZ
    val scanner = remember { BarcodeScanning.getClient() }
    
    // Flag para controlar se já escaneou (thread-safe)
    val hasScannedRef = remember { AtomicBoolean(false) }
    
    // Flag para proteger contra múltiplos frames simultâneos
    val isProcessingRef = remember { AtomicBoolean(false) }
    
    // Atualiza a referência quando hasScanned muda
    LaunchedEffect(hasScanned) {
        hasScannedRef.set(hasScanned)
    }
    
    // PreviewView criado e configurado UMA ÚNICA VEZ
    val previewViewState = remember { mutableStateOf<PreviewView?>(null) }
    
    // Referências para cleanup e controle
    val cameraProviderRef = remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val imageAnalysisRef = remember { mutableStateOf<ImageAnalysis?>(null) }
    val shouldStopRef = remember { AtomicBoolean(false) }
    val isBoundRef = remember { AtomicBoolean(false) }
    
    // AndroidView cria e configura o PreviewView
    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                // Configurações críticas para Samsung (evita tela preta)
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                
                // Clipping do PreviewView (garante que não vaze do container)
                clipToOutline = true
                outlineProvider = ViewOutlineProvider.BACKGROUND
                
                // LayoutParams para respeitar o container
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                
                // Salva a instância no estado
                previewViewState.value = this
                
                Log.d("CameraPreview", "✅ PreviewView criado e configurado com clipping")
            }
        },
        modifier = modifier,
        onRelease = {
            // Limpa referência ao sair
            previewViewState.value = null
            Log.d("CameraPreview", "🔴 PreviewView liberado")
        }
    )
    
    // LaunchedEffect para bind da câmera (apenas quando permissão concedida e previewView existe)
    LaunchedEffect(hasCameraPermission, previewViewState.value) {
        val previewView = previewViewState.value
        
        // Se permissão negada ou previewView não existe, faz unbind se necessário
        if (!hasCameraPermission || previewView == null) {
            if (isBoundRef.get()) {
                Log.d("CameraPreview", "🔴 Condições não satisfeitas: fazendo unbind...")
                mainExecutor.execute {
                    cameraProviderRef.value?.unbindAll()
                    imageAnalysisRef.value?.clearAnalyzer()
                    isBoundRef.set(false)
                    Log.d("CameraPreview", "✅ Unbind executado")
                }
            } else {
                Log.d("CameraPreview", "⏸️ Bind cancelado: permissão=${hasCameraPermission}, previewView=${previewView != null}")
            }
            return@LaunchedEffect
        }
        
        // Proteção: não bindar múltiplas vezes
        if (isBoundRef.get()) {
            Log.d("CameraPreview", "⏸️ Bind cancelado: já está bindado")
            return@LaunchedEffect
        }
        
        Log.d("CameraPreview", "🚀 Iniciando bind da câmera...")
        
        try {
            // Aguarda ProcessCameraProvider (pode rodar em background)
            val cameraProvider = suspendCancellableCoroutine<ProcessCameraProvider> { continuation ->
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                        val provider = cameraProviderFuture.get()
                        continuation.resume(provider)
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }, mainExecutor) // Listener roda na main thread
            }
            
                cameraProviderRef.value = cameraProvider
            Log.d("CameraPreview", "✅ ProcessCameraProvider obtido")
            
            // CRÍTICO: Toda lógica do PreviewView deve rodar na MAIN THREAD
            withContext(Dispatchers.Main.immediate) {
                Log.d("CameraPreview", "THREAD(surfaceProvider)=${Thread.currentThread().name}")
                
                // Obtém rotação do display (landscape = 90 ou 270)
                val displayRotation = previewView.display.rotation
                Log.d("CameraPreview", "📐 Display rotation: $displayRotation")
                
                // Obtém surfaceProvider na MAIN THREAD (CRÍTICO!)
                val surfaceProvider = previewView.surfaceProvider
                Log.d("CameraPreview", "✅ surfaceProvider obtido na main thread")
                
                // Preview com rotação configurada
                val preview = Preview.Builder()
                    .setTargetRotation(displayRotation)
                    .build()
                    .also {
                        // setSurfaceProvider também na MAIN THREAD
                        it.setSurfaceProvider(surfaceProvider)
                        Log.d("CameraPreview", "✅ Preview configurado com surfaceProvider e rotation=$displayRotation")
                    }
                
                // Image Analysis para barcode scanning com rotação
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setTargetRotation(displayRotation)
                    .build()
                    .also { analysis ->
                        imageAnalysisRef.value = analysis
                        
                        // Analyzer roda em background (executor dedicado)
                        analysis.setAnalyzer(analyzerExecutor) { imageProxy ->
                            try {
                                // Para análise se já escaneou ou deve parar (mas sempre fecha o imageProxy)
                                if (hasScannedRef.get() || shouldStopRef.get()) {
                                    imageProxy.close()
                                    return@setAnalyzer
                                }
                                
                                // Proteção contra múltiplos frames simultâneos
                                if (!isProcessingRef.compareAndSet(false, true)) {
                                    // Se já está processando outro frame, fecha este e ignora
                                    imageProxy.close()
                                    return@setAnalyzer
                                }
                                
                                val mediaImage = imageProxy.image
                                if (mediaImage != null) {
                                    val image = InputImage.fromMediaImage(
                                        mediaImage,
                                        imageProxy.imageInfo.rotationDegrees
                                    )
                                    
                                    scanner.process(image)
                                        .addOnSuccessListener { barcodes ->
                                            if (!hasScannedRef.get() && !shouldStopRef.get()) {
                                                for (barcode in barcodes) {
                                                    val rawValue = barcode.rawValue
                                                    if (rawValue != null && rawValue.isNotBlank()) {
                                                        hasScannedRef.set(true)
                                                        shouldStopRef.set(true)
                                                        
                                                        Log.d("CameraPreview", "✅ QRCode detectado: $rawValue")
                                                        
                                                        // Para a análise imediatamente
                                                        analysis.clearAnalyzer()
                                                        
                                                        // Unbind da câmera via mainExecutor (fora do callback do frame)
                                                        mainExecutor.execute {
                                                            cameraProvider.unbindAll()
                                                            isBoundRef.set(false)
                                                            Log.d("CameraPreview", "🧹 unbindAll() após scan bem-sucedido")
                                                        }
                                                        
                                                        // Dispara callback na main thread
                                                        mainExecutor.execute {
                                                            onBarcodeDetected(rawValue)
                                                        }
                                                        break
                                                    }
                                                }
                                            }
                                            isProcessingRef.set(false)
                                        }
                                        .addOnFailureListener { e ->
                                            // Log de erro (mas não bloqueia)
                                            Log.w("CameraPreview", "⚠️ Erro ao processar frame: ${e.message}")
                                            isProcessingRef.set(false)
                                        }
                                        .addOnCompleteListener {
                                            // Sempre fecha imageProxy
                                            imageProxy.close()
                                        }
                                } else {
                                    isProcessingRef.set(false)
                                    imageProxy.close()
                                }
                            } catch (e: Exception) {
                                // Garante que imageProxy seja fechado mesmo em caso de erro
                                Log.e("CameraPreview", "❌ Erro no analyzer: ${e.message}", e)
                                isProcessingRef.set(false)
                                imageProxy.close()
                            }
                        }
                        
                        Log.d("CameraPreview", "✅ ImageAnalysis configurado com analyzer")
                    }
                
                // Camera selector: tenta frontal primeiro, fallback para traseira
                val frontCameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                val backCameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                
                val cameraSelector = try {
                    // Verifica se câmera frontal está disponível
                    if (cameraProvider.hasCamera(frontCameraSelector)) {
                        Log.d("CameraPreview", "✅ CameraSelector configurado (FRONT)")
                        frontCameraSelector
                    } else {
                        Log.d("CameraPreview", "⚠️ Câmera frontal não disponível, usando traseira")
                        backCameraSelector
                    }
                } catch (e: Exception) {
                    Log.w("CameraPreview", "⚠️ Erro ao verificar câmera frontal, usando traseira: ${e.message}")
                    backCameraSelector
                }
                
                // Unbind antes de bind (evita câmera "presa" e tela preta)
                    cameraProvider.unbindAll()
                Log.d("CameraPreview", "🧹 unbindAll() executado antes do bind")
                
                // Bind da câmera ao lifecycle (na MAIN THREAD)
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                isBoundRef.set(true)
                Log.d("CameraPreview", "✅ bindToLifecycle executado com sucesso na main thread")
                }
            } catch (e: Exception) {
            Log.e("CameraPreview", "❌ Erro ao inicializar câmera: ${e.message}", e)
            isBoundRef.set(false)
        }
            }
        
    // DisposableEffect para cleanup ao sair da tela
    DisposableEffect(lifecycleOwner) {
        onDispose {
            Log.d("CameraPreview", "🔴 DisposableEffect onDispose: limpando recursos...")
            
            // Marca para parar processamento
            shouldStopRef.set(true)
            isBoundRef.set(false)
            
            // Unbind da câmera ao sair da tela (na main thread)
            mainExecutor.execute {
                try {
                cameraProviderRef.value?.unbindAll()
                imageAnalysisRef.value?.clearAnalyzer()
                    Log.d("CameraPreview", "✅ Cleanup executado: unbindAll() e clearAnalyzer()")
                } catch (e: Exception) {
                    Log.e("CameraPreview", "❌ Erro no cleanup: ${e.message}", e)
                }
            }
            
            // Shutdown do executor do analyzer
            try {
            analyzerExecutor.shutdown()
                Log.d("CameraPreview", "✅ Analyzer executor shutdown")
            } catch (e: Exception) {
                Log.e("CameraPreview", "❌ Erro ao fazer shutdown do executor: ${e.message}", e)
            }
        }
    }
}

