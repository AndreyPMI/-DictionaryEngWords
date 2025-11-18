package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels

sealed class ScanIntent {
    object StartScanning : ScanIntent()
    data class QRCodeScanned(val qrData: String) : ScanIntent()
    object Retry : ScanIntent()
    object Reset : ScanIntent()
    object SaveScannedCategory : ScanIntent()
}