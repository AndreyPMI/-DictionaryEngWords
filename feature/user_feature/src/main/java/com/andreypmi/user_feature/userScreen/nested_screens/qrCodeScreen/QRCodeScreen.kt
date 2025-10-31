package com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.QrCodeData
import com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.models.QRCodeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScreen(
    qrState: QRCodeState,
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        qrState.categoryName ?: "QR Code"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                qrState.isLoading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(16.dp))
                        Text("Generating QR Code...")
                    }
                }

                qrState.error != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Build,
                            contentDescription = "Error",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Error",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = qrState.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(24.dp))
                        Button(onClick = { }) {
                            Text("Try Again")
                        }
                    }
                }

                qrState.qrImage != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        QrCodeImage(
                            qrData = qrState.qrImage,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1f)
                        )

                        Spacer(Modifier.height(32.dp))

                        if (qrState.wordsCount != null) {
                            Text(
                                text = "${qrState.wordsCount} words",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        ExtendedFloatingActionButton(
                            onClick = onShare,
                            icon = { Icon(Icons.Default.Share, contentDescription = null) },
                            text = { Text("Share") },
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "Scan QR code to get words",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                else -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "No data loaded",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { }) {
                            Text("Load")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun QrCodeImage(
    qrData: QrCodeData,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(qrData) {
        createQrBitmap(qrData.pixels)
    }

    Image(
        bitmap = bitmap,
        contentDescription = "QR код",
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

private fun createQrBitmap(pngBytes: ByteArray): ImageBitmap {
    return try {
        BitmapFactory.decodeByteArray(pngBytes, 0, pngBytes.size).asImageBitmap()
    } catch (_: Exception) {
        createFallbackBitmap()
    }
}
private fun createFallbackBitmap(): ImageBitmap {
    val size = 100
    val bitmap = ImageBitmap(size, size)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    paint.color = Color.Gray
    canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paint)

    paint.color = Color.Red
    canvas.drawLine(Offset(0f, 0f), Offset(size.toFloat(), size.toFloat()), paint)
    canvas.drawLine(Offset(size.toFloat(), 0f), Offset(0f, size.toFloat()), paint)

    return bitmap
}