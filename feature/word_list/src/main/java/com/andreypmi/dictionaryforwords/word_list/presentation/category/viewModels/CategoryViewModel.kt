package com.andreypmi.dictionaryforwords.word_list.presentation.category.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.usecase.CategoryUseCasesFacade
import com.andreypmi.core_domain.usecase.sharedManager.CategorySelectionManager
import com.andreypmi.dictionaryforwords.word_list.presentation.category.CategoryDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryUseCases: CategoryUseCasesFacade,
    private val categorySelectionManager: CategorySelectionManager
) : ViewModel(), ICategoryViewModel {

    private val _categoryState = MutableStateFlow<CategoryState>(CategoryState.Loading)
    override val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()

    private val _categoryDialogState =
        MutableStateFlow<CategoryDialogState>(CategoryDialogState.Hidden)
    override val categoryDialogState: StateFlow<CategoryDialogState> =
        _categoryDialogState.asStateFlow()

    override fun handleIntent(intent: ICategoryViewModel.CategoryIntent) {
        when (intent) {
            is ICategoryViewModel.CategoryIntent.AddCategory -> addCategory(intent.name)
            is ICategoryViewModel.CategoryIntent.DeleteCategory -> deleteCategory(intent.category)
            ICategoryViewModel.CategoryIntent.HideCategoryDialog -> hideCategoryDialog()
            ICategoryViewModel.CategoryIntent.LoadCategories -> loadCategories()
            is ICategoryViewModel.CategoryIntent.SelectCategory -> selectCategory(intent.category)
            ICategoryViewModel.CategoryIntent.ShowAddCategoryDialog -> showAddCategoryDialog()
            is ICategoryViewModel.CategoryIntent.ShowDeleteCategoryDialog -> showDeleteCategoryDialog(
                intent.category
            )

            is ICategoryViewModel.CategoryIntent.ShowEditCategoryDialog -> showEditCategoryDialog(
                intent.category
            )

            is ICategoryViewModel.CategoryIntent.UpdateCategory -> updateCategory(intent.category)
        }
    }

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoryState.value = CategoryState.Loading
            try {
                categoryUseCases.getAllCategory().collect { categories ->
                    when {
                        categories.isEmpty() -> _categoryState.value = CategoryState.Empty
                        else -> _categoryState.value = CategoryState.Success(categories)
                    }
                }
            } catch (e: Exception) {
                _categoryState.value =
                    CategoryState.Error("Failed to load categories: ${e.message}")
                Log.d("CategoryViewModel", "Error loading categories: $e")
            }
        }
    }

    private fun selectCategory(category: Category) {
        viewModelScope.launch {
            categoryUseCases.saveLastSelectedCategory(category)

            categorySelectionManager.notifyCategorySelected(category)
        }
    }

    private fun showAddCategoryDialog() {
        _categoryDialogState.value = CategoryDialogState.Add
    }

    private fun showEditCategoryDialog(category: Category) {
        _categoryDialogState.value = CategoryDialogState.Edit(category)
    }

    private fun showDeleteCategoryDialog(category: Category) {
        _categoryDialogState.value = CategoryDialogState.Delete(category)
    }

    private fun hideCategoryDialog() {
        _categoryDialogState.value = CategoryDialogState.Hidden
    }

    private fun addCategory(name: String) {
        viewModelScope.launch {
            val category = Category(0, name)
            categoryUseCases.insertCategory(category)
            hideCategoryDialog()
        }
    }

    private fun updateCategory(category: Category) {
        viewModelScope.launch {
            categoryUseCases.updateCategory(category)
            hideCategoryDialog()
        }
    }

    private fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryUseCases.deleteCategory(category)
            hideCategoryDialog()
        }
    }
}