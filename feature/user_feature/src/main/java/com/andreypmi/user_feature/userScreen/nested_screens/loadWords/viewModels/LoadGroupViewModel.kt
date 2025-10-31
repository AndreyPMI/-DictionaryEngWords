package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels

import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.usecase.sharedUseCases.LoadSharedCategoryUseCase
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models.LoadGroupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoadGroupViewModel(
    private val loadSharedCategoryUseCase: LoadSharedCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoadGroupState())
    val state = _state.asStateFlow()

    fun processIntent(intent: LoadGroupIntent) {
        when (intent) {
            is LoadGroupIntent.ProcessQRCode -> {
                processQRCode(intent.qrData)
            }
            LoadGroupIntent.ClearError -> {
                _state.value = _state.value.copy(error = null)
            }
            else -> {}
        }
    }

    private fun processQRCode(qrData: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val result = loadSharedCategoryUseCase.execute(qrData)
                if (result.isSuccess) {
                    val sharedCategory = result.getOrThrow()
                    _state.value = _state.value.copy(
                        isLoading = false,
                        successMessage = "Успешно загружено ${sharedCategory.words.size} слов из категории \"${sharedCategory.categoryName}\"",
                        scannedData = qrData
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "Ошибка загрузки данных"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Неизвестная ошибка при обработке QR кода"
                )
            }
        }
    }
}