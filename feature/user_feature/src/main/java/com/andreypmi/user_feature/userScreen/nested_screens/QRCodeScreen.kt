package com.andreypmi.user_feature.userScreen.nested_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.andreypmi.user_feature.userScreen.models.QRCodeState

@Composable
fun QRCodeScreen(
    qrState: QRCodeState,
    onBack: () -> Unit,
    onShare: (ImageBitmap) -> Unit
) {
    // TODO: Реализовать экран генерации QR кода
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("QRCodeScreen - в разработке")
    }
}
