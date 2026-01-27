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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.concurrent.atomic.AtomicBoolean
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.speedmenu.tablet.core.ui.components.OrderPlacedDialog
import com.speedmenu.tablet.core.ui.components.TopActionBar
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import kotlinx.coroutines.delay
import java.util.concurrent.Executors

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
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrScannerScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current
    
    // Estado para controlar se já escaneou
    var hasScanned by remember { mutableStateOf(false) }
    var comandaCode by remember { mutableStateOf("") }
    var showOrderPlacedDialog by remember { mutableStateOf(false) }
    
    // Estado para gesto oculto (5 toques no retângulo de mira)
    var tapCount by remember { mutableStateOf(0) }
    var lastTapTime by remember { mutableStateOf(0L) }
    
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
    val hasCameraPermission = when (val status = cameraPermissionState.status) {
        is PermissionStatus.Granted -> true
        is PermissionStatus.Denied -> false
        else -> false
    }
    
    // Estados mockados
    val isConnected = true
    val tableNumber = "17"
    
    // Função para simular scan (usada no modo MOCK)
    val simulateScan = {
        if (!hasScanned) {
            hasScanned = true
            comandaCode = "COMANDA-TESTE-17"
            showOrderPlacedDialog = true
        }
    }
    
    // Auto-scan após 3 segundos no modo MOCK (apenas se ainda não escaneou)
    if (isMockMode && !hasScanned) {
        LaunchedEffect(Unit) {
            delay(3000) // 3 segundos
            if (!hasScanned) {
                simulateScan()
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Top Action Bar
        TopActionBar(
            onBackClick = onNavigateBack,
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {}
        )
        
        // Conteúdo principal
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            if (hasCameraPermission) {
                // Preview da câmera
                CameraPreview(
                    hasScanned = hasScanned,
                    onBarcodeDetected = { barcodeValue ->
                        if (!hasScanned && barcodeValue.isNotBlank()) {
                            hasScanned = true
                            comandaCode = barcodeValue
                            showOrderPlacedDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                // Overlay de mira
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Retângulo de mira
                        Box(
                            modifier = Modifier
                                .size(250.dp)
                                .border(
                                    width = 2.dp,
                                    color = SpeedMenuColors.PrimaryLight,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .then(
                                    // Gesto oculto: 5 toques no retângulo (apenas em modo MOCK)
                                    if (isMockMode && !hasScanned) {
                                        Modifier.clickable {
                                            val currentTime = System.currentTimeMillis()
                                            // Reset contador se passou mais de 2 segundos desde o último toque
                                            if (currentTime - lastTapTime > 2000) {
                                                tapCount = 0
                                            }
                                            
                                            tapCount++
                                            lastTapTime = currentTime
                                            
                                            // Se tocou 5 vezes, simula scan
                                            if (tapCount >= 5) {
                                                tapCount = 0
                                                simulateScan()
                                            }
                                        }
                                    } else {
                                        Modifier
                                    }
                                )
                        )
                        
                        // Texto de instrução
                        Text(
                            text = "Aponte para o QRCode da comanda",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(
                                    color = SpeedMenuColors.BackgroundPrimary.copy(alpha = 0.8f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 12.dp)
                        )
                    }
                }
            } else {
                // Placeholder quando não tem permissão
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Permissão de câmera necessária",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "Para escanear o QRCode da comanda, é necessário permitir o acesso à câmera.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                
                // Solicita permissão automaticamente
                LaunchedEffect(Unit) {
                    cameraPermissionState.launchPermissionRequest()
                }
            }
        }
    }
    
    // Dialog de pedido realizado
    if (showOrderPlacedDialog) {
        OrderPlacedDialog(
            visible = showOrderPlacedDialog,
            comandaCode = comandaCode,
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
}

/**
 * Preview da câmera com análise de barcode.
 * 
 * CAUSA RAIZ DA LENTIDÃO (CORRIGIDA):
 * 1. ContextCompat.getMainExecutor() fazia toda inicialização da câmera rodar na MAIN THREAD
 *    → Corrigido: usando executor de background para ProcessCameraProvider
 * 
 * 2. Toda lógica de bind estava dentro do AndroidView(factory), causando múltiplas inicializações
 *    → Corrigido: PreviewView criado com remember, bind/unbind movido para DisposableEffect
 * 
 * 3. cameraProvider.unbindAll() chamado dentro do callback do analyzer (thread do analyzer)
 *    → Corrigido: unbindAll disparado via mainExecutor fora do callback do frame
 * 
 * OTIMIZAÇÕES DE PERFORMANCE:
 * - Executor único reutilizável para analyzer (não recriado a cada recomposition)
 * - Executor separado para ProcessCameraProvider (background thread)
 * - BarcodeScanner criado uma única vez
 * - DisposableEffect para bind/unbind adequado da câmera
 * - Proteção contra múltiplos frames simultâneos
 * - Sempre fecha imageProxy (inclusive em erros)
 */
@Composable
private fun CameraPreview(
    hasScanned: Boolean,
    onBarcodeDetected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    // Executor único para analyzer (não recriado a cada recomposition)
    val analyzerExecutor = remember {
        Executors.newSingleThreadExecutor()
    }
    
    // Executor de background para ProcessCameraProvider (evita bloquear main thread)
    val cameraExecutor = remember {
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
    
    // PreviewView criado UMA ÚNICA VEZ (não recriado a cada recomposition)
    val previewView = remember { PreviewView(context) }
    
    // Referências para cleanup e controle
    val cameraProviderRef = remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val imageAnalysisRef = remember { mutableStateOf<ImageAnalysis?>(null) }
    val shouldStopRef = remember { AtomicBoolean(false) }
    
    // DisposableEffect para bind/unbind da câmera (executa apenas uma vez por entrada/saída)
    DisposableEffect(lifecycleOwner, previewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                cameraProviderRef.value = cameraProvider
                
                // Preview
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                
                // Image Analysis para barcode scanning
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { analysis ->
                        imageAnalysisRef.value = analysis
                        
                        analysis.setAnalyzer(analyzerExecutor) { imageProxy ->
                            try {
                                // Para análise se já escaneou (mas sempre fecha o imageProxy)
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
                                                        
                                                        // Para a análise imediatamente
                                                        analysis.clearAnalyzer()
                                                        
                                                        // Unbind da câmera via mainExecutor (fora do callback do frame)
                                                        mainExecutor.execute {
                                                            cameraProvider.unbindAll()
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
                                        .addOnFailureListener {
                                            // Ignora erros silenciosamente
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
                                isProcessingRef.set(false)
                                imageProxy.close()
                            }
                        }
                    }
                
                // Camera selector
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                
                // Bind da câmera ao lifecycle (apenas uma vez, na main thread)
                mainExecutor.execute {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                }
            } catch (e: Exception) {
                // Erro ao inicializar câmera
            }
        }, cameraExecutor) // Usa executor de background, não main thread
        
        onDispose {
            // Marca para parar processamento
            shouldStopRef.set(true)
            
            // Unbind da câmera ao sair da tela (na main thread)
            mainExecutor.execute {
                cameraProviderRef.value?.unbindAll()
                imageAnalysisRef.value?.clearAnalyzer()
            }
            
            // Shutdown dos executors
            analyzerExecutor.shutdown()
            cameraExecutor.shutdown()
        }
    }
    
    // AndroidView apenas retorna o PreviewView já criado
    AndroidView(
        factory = { previewView },
        modifier = modifier
    )
}

