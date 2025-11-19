package com.andreypmi.dictionaryforwords.word_list.presentation.words

import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import kotlinx.coroutines.flow.StateFlow

interface IWordsViewModel {
    val uiState: StateFlow<WordsUiState>
    val dialogState: StateFlow<DialogState>

    fun handleIntent(intent: WordsIntent)

    sealed class WordsIntent {
        data object OpenAddWordDialog : WordsIntent()
        data class OpenEditWordDialog(val word: Word) : WordsIntent()
        data object CloseWordDialog : WordsIntent()
        data class AddNewWord(val word: Word) : WordsIntent()
        data class DeleteWord(val word: Word) : WordsIntent()
        data class UpdateWord(val word: Word) : WordsIntent()
    }
}