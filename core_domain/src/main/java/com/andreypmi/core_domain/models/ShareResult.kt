package com.andreypmi.core_domain.models

data class ShareResult(
    val shareId: String,
    val shareLink: String,
    val qrCodeData: QrCodeData
)