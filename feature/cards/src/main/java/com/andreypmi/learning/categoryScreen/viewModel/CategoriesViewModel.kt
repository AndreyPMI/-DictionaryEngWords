package com.andreypmi.learning.categoryScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.usecase.categoryUseCases.GetAllCategoryUseCase
import com.andreypmi.learning.categoryScreen.models.CategoriesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val getAllCategoriesUseCase: GetAllCategoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state

    fun processIntent(intent: CategoriesIntent) {
        when (intent) {
            is CategoriesIntent.LoadCategories -> loadCategories()
            is CategoriesIntent.CategoryClicked -> onCategoryClicked(intent.categoryId)
        }
    }


    private fun loadCategories() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                getAllCategoriesUseCase.execute().collect { categories ->
                    _state.update {
                        it.copy(
                            categories = categories,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load categories: ${e.message}"
                    )
                }
            }
        }
    }

    private fun onCategoryClicked(categoryId: Int) {
    }
}