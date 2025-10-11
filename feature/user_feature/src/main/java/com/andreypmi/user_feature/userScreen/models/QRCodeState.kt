package com.andreypmi.user_feature.userScreen.models

import android.graphics.Bitmap

data class QRCodeState(
    val qrBitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)