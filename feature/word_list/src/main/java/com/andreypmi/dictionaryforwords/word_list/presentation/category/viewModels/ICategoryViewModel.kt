package com.andreypmi.dictionaryforwords.word_list.presentation.category.viewModels

import com.andreypmi.core_domain.models.Category
import com.andreypmi.dictionaryforwords.word_list.presentation.category.CategoryDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import kotlinx.coroutines.flow.StateFlow

interface ICategoryViewModel {
    val categoryState: StateFlow<CategoryState>
    val categoryDialogState: StateFlow<CategoryDialogState>
    fun handleIntent(intent: CategoryIntent)

    sealed class CategoryIntent {
        data object LoadCategories : CategoryIntent()
        data object ShowAddCategoryDialog : CategoryIntent()
        data class ShowEditCategoryDialog(val category: Category) : CategoryIntent()
        data class ShowDeleteCategoryDialog(val category: Category) : CategoryIntent()
        data object HideCategoryDialog : CategoryIntent()
        data class AddCategory(val name: String) : CategoryIntent()
        data class UpdateCategory(val category: Category) : CategoryIntent()
        data class DeleteCategory(val category: Category) : CategoryIntent()
        data class SelectCategory(val category: Category) : CategoryIntent()
    }
}