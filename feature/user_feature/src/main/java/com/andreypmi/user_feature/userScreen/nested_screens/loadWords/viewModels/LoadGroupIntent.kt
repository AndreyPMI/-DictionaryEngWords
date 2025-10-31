package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels

sealed class LoadGroupIntent {
    data object LoadInitialData : LoadGroupIntent()
    data class ProcessQRCode(val qrData: String) : LoadGroupIntent()
    data object ClearError : LoadGroupIntent()
}
