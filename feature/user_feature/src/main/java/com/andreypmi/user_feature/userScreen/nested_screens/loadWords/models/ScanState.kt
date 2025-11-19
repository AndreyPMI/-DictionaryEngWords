package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models

sealed class ScanState {
    object Idle : ScanState()
    object Scanning : ScanState()
    object Loading : ScanState()
    object Saving : ScanState()
    object SavedSuccessfully : ScanState()
    data class Success(val category: ScannedCategory) : ScanState()
    data class Error(val message: Int) : ScanState()
}