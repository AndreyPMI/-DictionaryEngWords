package com.andreypmi.dictionaryforwords.presentation.features.words

import com.andreypmi.dictionaryforwords.domain.models.Category
import com.andreypmi.dictionaryforwords.domain.models.Word
import kotlinx.coroutines.flow.StateFlow

interface IWordsViewModel {
    val uiState : StateFlow<WordsUiState>
    val dialogState : StateFlow<Boolean>
    fun openAddWordDialog()
    fun closeAddWordDialog()
    fun addNewWord(word: Word)
    fun deleteWord(word: Word)
}