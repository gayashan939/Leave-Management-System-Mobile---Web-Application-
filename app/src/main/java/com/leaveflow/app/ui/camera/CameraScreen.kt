package com.leaveflow.app.ui.camera

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.leaveflow.app.ui.theme.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    onPhotoSaved: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }
    }

    when {
        cameraPermission.status.isGranted -> {
            CameraContent(
                context     = context,
                onPhotoSaved = onPhotoSaved,
                onBack      = onBack
            )
        }
        else -> {
            // Permission denied UI
            Box(
                modifier         = Modifier.fillMaxSize().background(Navy900),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier            = Modifier.padding(32.dp)
                ) {
                    Icon(Icons.Default.NoPhotography, null, tint = ErrorRed, modifier = Modifier.size(72.dp))
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "Camera Permission Denied",
                        style     = MaterialTheme.typography.headlineSmall,
                        color     = TextPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Camera access is required to capture supporting documents for leave requests. Please grant permission in Settings.",
                        style     = MaterialTheme.typography.bodyMedium,
                        color     = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { cameraPermission.launchPermissionRequest() },
                        colors  = ButtonDefaults.buttonColors(containerColor = Teal60, contentColor = Navy900)
                    ) { Text("Request Permission") }
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onBack,
                        colors  = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
                    ) { Text("Go Back") }
                }
            }
        }
    }
}

@Composable
private fun CameraContent(
    context: Context,
    onPhotoSaved: (String) -> Unit,
    onBack: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    val previewView = remember { PreviewView(context) }
    var imageCaptureUseCase: ImageCapture? by remember { mutableStateOf(null) }
    var capturedPhotoPath by remember { mutableStateOf<String?>(null) }
    var isCapturing by remember { mutableStateOf(false) }

    // Set up CameraX
    LaunchedEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            imageCaptureUseCase = imageCapture

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e("CameraScreen", "Camera bind failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    if (capturedPhotoPath != null) {
        // ── Photo Preview ─────────────────────────────────────────────────────
        Box(modifier = Modifier.fillMaxSize().background(Navy900)) {
            AsyncImage(
                model             = capturedPhotoPath,
                contentDescription = "Captured document",
                contentScale      = ContentScale.Fit,
                modifier          = Modifier.fillMaxSize()
            )
            // Top bar
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .background(Navy900.copy(alpha = 0.7f))
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text("Preview", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
            }
            // Action buttons
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Navy900.copy(alpha = 0.85f))
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick  = { capturedPhotoPath = null },
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    modifier = Modifier.weight(1f).height(52.dp)
                ) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(Modifier.width(6.dp))
                    Text("Retake", fontWeight = FontWeight.SemiBold)
                }
                Button(
                    onClick  = { capturedPhotoPath?.let { onPhotoSaved(it) } },
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = Teal60, contentColor = Navy900),
                    modifier = Modifier.weight(1f).height(52.dp)
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(Modifier.width(6.dp))
                    Text("Use Photo", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    } else {
        // ── Camera Viewfinder ─────────────────────────────────────────────────
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            AndroidView(
                factory  = { previewView },
                modifier = Modifier.fillMaxSize()
            )

            // Top bar overlay
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                }
                Spacer(Modifier.width(8.dp))
                Text("Capture Document", style = MaterialTheme.typography.titleLarge, color = Color.White)
            }

            // Instruction overlay
            Box(
                modifier         = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Teal60.copy(0.6f), RoundedCornerShape(16.dp))
                    .size(width = 300.dp, height = 200.dp)
            )

            // Bottom control strip
            Column(
                modifier            = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(24.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Position document inside the frame",
                    style     = MaterialTheme.typography.bodySmall,
                    color     = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                // Shutter button
                Box(
                    modifier         = Modifier
                        .size(72.dp)
                        .border(3.dp, Color.White, CircleShape)
                        .padding(5.dp)
                        .clip(CircleShape)
                        .background(if (isCapturing) Teal60 else Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick  = {
                            isCapturing = true
                            capturePhoto(context, imageCaptureUseCase, executor) { path ->
                                capturedPhotoPath = path
                                isCapturing       = false
                            }
                        },
                        enabled  = !isCapturing && imageCaptureUseCase != null
                    ) {
                        if (isCapturing) {
                            CircularProgressIndicator(color = Navy900, modifier = Modifier.size(28.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.Camera, null, tint = Navy900, modifier = Modifier.size(32.dp))
                        }
                    }
                }
            }
        }
    }
}

private fun capturePhoto(
    context: Context,
    imageCapture: ImageCapture?,
    executor: ExecutorService,
    onResult: (String) -> Unit
) {
    if (imageCapture == null) return

    val photoDir = File(context.filesDir, "leave_documents").apply { mkdirs() }
    val fileName = "LEAVE_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
    val outputFile = File(photoDir, fileName)

    val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()
    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                onResult(outputFile.absolutePath)
            }
            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraScreen", "Capture failed: ${exception.message}")
                onResult("")
            }
        }
    )
}
