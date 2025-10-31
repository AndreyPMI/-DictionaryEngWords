package com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.models

import com.andreypmi.core_domain.models.QrCodeData

data class QRCodeState(
    val categoryId: String = "",
    val categoryName: String? = null,
    val wordsCount: Int? = null,
    val qrImage: QrCodeData? = null,
    val shareLink: String? = null,
    val isLoading: Boolean = false,
    val isSharing: Boolean = false,
    val error: String? = null
)