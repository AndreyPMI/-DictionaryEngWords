package com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.viewModels

sealed interface QRCodeIntent {
    data class GenerateQRCode(val categoryId: String) : QRCodeIntent
    object ErrorShown : QRCodeIntent
    object RetryGeneration : QRCodeIntent
}