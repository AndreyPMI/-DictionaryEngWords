package com.andreypmi.learning.categoryScreen.models

import com.andreypmi.core_domain.models.Category

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)