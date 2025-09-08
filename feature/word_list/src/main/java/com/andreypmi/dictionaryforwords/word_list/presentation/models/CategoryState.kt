package com.andreypmi.dictionaryforwords.word_list.presentation.models

import com.andreypmi.core_domain.models.Category

sealed class CategoryState {
    data object Loading : CategoryState()
    data class Error(val message: String) : CategoryState()
    data class Success(val categories: List<Category>) : CategoryState()
    data object Empty : CategoryState()
}