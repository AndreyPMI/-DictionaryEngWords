package com.andreypmi.learning.learningScreen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreypmi.core_domain.usecase.categoryUseCases.GetCategoryByIdUseCase
import com.andreypmi.core_domain.usecase.wordsUseCases.GetAllWordsUseCase
import javax.inject.Inject

class LearningSessionViewModelFactory @Inject constructor(
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearningSessionViewModel::class.java)) {
            return LearningSessionViewModel(getAllWordsUseCase, getCategoryByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}