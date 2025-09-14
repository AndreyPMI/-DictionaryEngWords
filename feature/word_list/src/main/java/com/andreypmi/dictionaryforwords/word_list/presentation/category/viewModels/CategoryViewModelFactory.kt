package com.andreypmi.dictionaryforwords.word_list.presentation.category.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.andreypmi.core_domain.usecase.CategoryUseCasesFacade
import com.andreypmi.core_domain.usecase.sharedManager.CategorySelectionManager
import com.andreypmi.core_domain.usecase.sharedManager.CategorySelectionManagerImpl
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory @Inject constructor(
    private val categoryUseCasesFacade: CategoryUseCasesFacade,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CategoryViewModel(
            categoryUseCases = categoryUseCasesFacade,
            categorySelectionManager = CategorySelectionManagerImpl
        ) as T
    }
}