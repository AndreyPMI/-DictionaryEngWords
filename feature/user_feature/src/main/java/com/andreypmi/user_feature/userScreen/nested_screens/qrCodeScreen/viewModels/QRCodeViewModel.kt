package com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.usecase.sharedUseCases.PrepareCategoryShareUseCase
import com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.models.QRCodeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class QRCodeViewModel @Inject constructor(
    private val prepareCategoryShareUseCase: PrepareCategoryShareUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(QRCodeState())
    val state: StateFlow<QRCodeState> = _state.asStateFlow()

    fun processIntent(intent: QRCodeIntent) {
        when (intent) {
            is QRCodeIntent.GenerateQRCode -> generateQRCode(intent.categoryId)
            QRCodeIntent.ShareQRCode -> shareQRCode()
            QRCodeIntent.ErrorShown -> onErrorShown()
            QRCodeIntent.RetryGeneration -> retryGeneration()
        }
    }

    private fun generateQRCode(categoryId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    categoryId = categoryId,
                    isLoading = true,
                    error = null,
                    qrImage = null,
                    shareLink = null
                )
            }

            try {
                val shareResult = prepareCategoryShareUseCase.execute(categoryId)

                if (shareResult.isSuccess) {
                    val result = shareResult.getOrThrow()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            qrImage = result.qrCodeData,
                            shareLink = result.shareLink,
                            categoryName = result.shareId
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Не удалось подготовить данные для шаринга: ${shareResult.exceptionOrNull()?.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Не удалось сгенерировать QR: ${e.message}"
                    )
                }
            }
        }
    }

    private fun shareQRCode() {
        val currentState = _state.value
        val shareLink = currentState.shareLink
        val qrImage = currentState.qrImage

        if (shareLink != null && qrImage != null) {
            _state.update { it.copy(isSharing = true) }
        } else {
            _state.update {
                it.copy(error = "Нет данных для шаринга")
            }
        }
    }

    private fun onErrorShown() {
        _state.update { it.copy(error = null) }
    }

    private fun retryGeneration() {
        _state.value.categoryId.takeIf { it.isNotEmpty() }?.let { categoryId ->
            generateQRCode(categoryId)
        }
    }
}