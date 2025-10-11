package com.andreypmi.learning.categoryScreen.viewModel

sealed interface CategoriesIntent {
    data object LoadCategories : CategoriesIntent
    data class CategoryClicked(val categoryId: String) : CategoriesIntent
}
