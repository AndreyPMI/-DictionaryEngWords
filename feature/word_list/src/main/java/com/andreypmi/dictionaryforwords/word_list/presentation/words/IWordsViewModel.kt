package com.andreypmi.dictionaryforwords.word_list.presentation.words

import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import kotlinx.coroutines.flow.StateFlow

interface IWordsViewModel {
    val wordsState: StateFlow<WordsUiState?>
    val dialogState: StateFlow<DialogState>
    val categoryState: StateFlow<CategoryState>
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