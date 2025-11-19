package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreypmi.core_domain.usecase.categoryUseCases.InsertCategoryWithWordsUseCase
import com.andreypmi.core_domain.usecase.sharedUseCases.LoadSharedCategoryUseCase
import javax.inject.Inject

class LoadGroupViewModelFactory @Inject constructor(
    private val loadSharedCategoryUseCase: LoadSharedCategoryUseCase,
    private val insertCategoryWithWordsUseCase: InsertCategoryWithWordsUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadGroupViewModel::class.java)) {
            return LoadGroupViewModel(
                loadSharedCategoryUseCase,
                insertCategoryWithWordsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}