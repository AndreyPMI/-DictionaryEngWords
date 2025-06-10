package com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.andreypmi.core_domain.usecase.WordUseCasesFacade
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class WordsViewModelFactory @Inject constructor(
    private val wordUseCasesFacade: WordUseCasesFacade
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return WordsViewModel(wordUseCase = wordUseCasesFacade) as T
    }
}