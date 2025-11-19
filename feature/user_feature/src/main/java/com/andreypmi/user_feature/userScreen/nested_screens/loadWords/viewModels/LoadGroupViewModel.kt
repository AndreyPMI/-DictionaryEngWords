package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.usecase.categoryUseCases.InsertCategoryWithWordsUseCase
import com.andreypmi.core_domain.usecase.sharedUseCases.LoadSharedCategoryUseCase
import com.andreypmi.user_feature.R
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models.ScanState
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models.ScannedCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class LoadGroupViewModel(
    private val loadSharedCategoryUseCase: LoadSharedCategoryUseCase,
    private val insertCategoryWithWordsUseCase: InsertCategoryWithWordsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ScanState>(ScanState.Idle)
    val state = _state.asStateFlow()

    private val _scannedCategory = MutableStateFlow<ScannedCategory?>(null)
    val scannedCategory = _scannedCategory.asStateFlow()

    fun processIntent(intent: ScanIntent) {
        when (intent) {
            ScanIntent.StartScanning -> {
                _state.value = ScanState.Scanning
            }

            is ScanIntent.QRCodeScanned -> {
                handleQRScanned(intent.qrData)
            }

            ScanIntent.Retry -> {
                _state.value = ScanState.Scanning
            }

            ScanIntent.Reset -> {
                _state.value = ScanState.Idle
                _scannedCategory.value = null
            }

            ScanIntent.SaveScannedCategory -> {
                saveScannedCategory()
            }
        }
    }

    private fun handleQRScanned(qrData: String) {
        val shareId = extractShareIdFromQrData(qrData)
        if (shareId == null) {
            _state.value = ScanState.Error(R.string.invalid_qr_format)
            return
        }
        _state.value = ScanState.Loading
        viewModelScope.launch {
            val result = loadSharedCategoryUseCase.execute(shareId)

            result
                .onSuccess { sharedCategory ->
                    val scannedCategory = ScannedCategory(
                        categoryId = sharedCategory.id,
                        categoryName = sharedCategory.categoryName,
                        words = sharedCategory.words,
                        wordsCount = sharedCategory.words.size
                    )
                    _scannedCategory.value = scannedCategory
                    _state.value = ScanState.Success(scannedCategory)
                }
                .onFailure { exception ->
                    _state.value = ScanState.Error(
                        R.string.unknown_category_load_error
                    )
                }
        }
    }

    private fun extractShareIdFromQrData(qrData: String): String? {
        val pattern = Regex("https://dictionaryforwords\\.web\\.app/scanner/([a-fA-F0-9-]{36})")
        return pattern.find(qrData)?.groups?.get(1)?.value
    }

    private fun saveScannedCategory() {
        val scannedCategory = _scannedCategory.value ?: run {
            _state.value = ScanState.Error(R.string.no_data_to_save)
            return
        }

        _state.value = ScanState.Saving
        viewModelScope.launch {
            try {
                val category = Category(
                    id = UUID.randomUUID().toString(),
                    category = scannedCategory.categoryName
                )

                val words = scannedCategory.words.map { word ->
                    Word(
                        id = null,
                        idCategory = category.id,
                        word = word.word,
                        translate = word.translate,
                        description = word.description
                    )
                }

                val success = insertCategoryWithWordsUseCase.execute(category, words)

                if (success) {
                    _state.value = ScanState.SavedSuccessfully
                } else {
                    _state.value = ScanState.Error(R.string.category_save_error)
                }
            } catch (_: Exception) {
                _state.value = ScanState.Error(R.string.unknown_category_load_error)
            }
        }
    }
}