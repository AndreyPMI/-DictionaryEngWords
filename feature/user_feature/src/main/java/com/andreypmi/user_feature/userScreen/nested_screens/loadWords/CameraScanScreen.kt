package com.andreypmi.user_feature.userScreen.nested_screens.loadWords

import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlin.math.abs

@Composable
fun CameraScanScreen(
    onQRCodeScanned: (String) -> Unit,
) {
    var frameColor by remember { mutableStateOf(Color.Red) }
    var previewSize by remember { mutableStateOf(IntSize.Zero) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        }
    }
    var isScanning by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            cameraController = cameraController,
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    previewSize = coordinates.size
                }
        )

        ScannerFrame(
            color = frameColor,
        )

        if (previewSize.width > 0 && previewSize.height > 0) {
            CenterQRCodeAnalyzer(
                cameraController = cameraController,
                onQRFound = { isInCenter ->
                    frameColor = if (isInCenter) Color.Green else Color.Red
                },
                onQRCodeScanned = { qr ->
                    if (isScanning) {
                        isScanning = false
                        Handler(Looper.getMainLooper()).postDelayed({
                            onQRCodeScanned(qr)
                        }, 100)
                    }
                }
            )
        }
    }
}

@Composable
fun CameraPreview(
    cameraController: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                this.controller = cameraController
            }
        },
        modifier = modifier
    )
}

@Composable
fun ScannerFrame(
    color: Color,
    sizeDp: Dp = 280.dp,
    borderWidth: Dp = 4.dp
) {
    val density = LocalDensity.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val widthPx = size.width
            val heightPx = size.height

            val boxSizePx = with(density) { sizeDp.toPx() }

            val left = (widthPx - boxSizePx) / 2
            val top = (heightPx - boxSizePx) / 2

            drawRect(
                color = Color.Black.copy(alpha = 0.6f),
                size = Size(widthPx, heightPx)
            )

            drawRect(
                color = Color.Transparent,
                topLeft = Offset(left, top),
                size = Size(boxSizePx, boxSizePx),
                blendMode = BlendMode.Clear
            )

            drawRoundRect(
                color = color,
                topLeft = Offset(left, top),
                size = Size(boxSizePx, boxSizePx),
                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                style = Stroke(width = borderWidth.toPx())
            )
        }
    }
}


@OptIn(ExperimentalGetImage::class)
@Composable
fun CenterQRCodeAnalyzer(
    cameraController: LifecycleCameraController,
    onQRFound: (Boolean) -> Unit,
    onQRCodeScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    val barcodeScanner = remember { BarcodeScanning.getClient(options) }

    LaunchedEffect(cameraController) {
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context)
        ) { imageProxy ->
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )

                barcodeScanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        val firstBarcode = barcodes.firstOrNull()
                        if (firstBarcode != null) {
                            val inCenter = isBarcodeInCenter(firstBarcode, image)
                            onQRFound(inCenter)

                            if (inCenter) {
                                firstBarcode.rawValue?.let(onQRCodeScanned)
                            }
                        } else {
                            onQRFound(false)
                        }
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }
    }
}

private fun isBarcodeInCenter(barcode: Barcode, image: InputImage): Boolean {
    val box = barcode.boundingBox ?: return false

    val centerX = image.width / 2f
    val centerY = image.height / 2f

    val barcodeCenterX = (box.left + box.right) / 2f
    val barcodeCenterY = (box.top + box.bottom) / 2f

    val thresholdX = image.width * 0.25f
    val thresholdY = image.height * 0.25f

    val inCenterX = abs(barcodeCenterX - centerX) <= thresholdX
    val inCenterY = abs(barcodeCenterY - centerY) <= thresholdY

    return inCenterX && inCenterY
}