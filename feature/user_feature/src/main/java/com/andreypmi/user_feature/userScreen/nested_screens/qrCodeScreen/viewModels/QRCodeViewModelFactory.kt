package com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreypmi.core_domain.usecase.categoryUseCases.GetCategoriesWithWordCountUseCase
import com.andreypmi.core_domain.usecase.sharedUseCases.PrepareCategoryShareUseCase
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels.ShareGroupViewModel
import javax.inject.Inject

class QRCodeViewModelFactory @Inject constructor(
    private val prepareCategoryShareUseCase: PrepareCategoryShareUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QRCodeViewModel::class.java)) {
            return QRCodeViewModel(prepareCategoryShareUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}