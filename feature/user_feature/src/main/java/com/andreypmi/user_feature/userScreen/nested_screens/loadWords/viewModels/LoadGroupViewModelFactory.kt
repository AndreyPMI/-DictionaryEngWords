package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreypmi.core_domain.usecase.sharedUseCases.LoadSharedCategoryUseCase
import com.andreypmi.core_domain.usecase.sharedUseCases.PrepareCategoryShareUseCase
import com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.viewModels.QRCodeViewModel
import javax.inject.Inject

class LoadGroupViewModelFactory @Inject constructor(
    private val loadSharedCategoryUseCase: LoadSharedCategoryUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadGroupViewModel::class.java)) {
            return LoadGroupViewModel(loadSharedCategoryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}