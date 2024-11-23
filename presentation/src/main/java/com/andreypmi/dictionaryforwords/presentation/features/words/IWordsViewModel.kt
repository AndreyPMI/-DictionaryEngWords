package com.andreypmi.dictionaryforwords.presentation.features.words

import kotlinx.coroutines.flow.StateFlow

interface IWordsViewModel {
    val uiState : StateFlow<WordsUiState>
    fun onClickAdd()
}