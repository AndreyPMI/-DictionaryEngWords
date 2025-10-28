package com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreypmi.core_domain.usecase.categoryUseCases.GetCategoriesWithWordCountUseCase
import javax.inject.Inject

class ShareGroupViewModelFactory @Inject constructor(
    private val getCategoriesWithWordCountUseCase: GetCategoriesWithWordCountUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShareGroupViewModel::class.java)) {
            return ShareGroupViewModel(getCategoriesWithWordCountUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}