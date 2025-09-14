package com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.word_list.presentation.category.CategoryDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import kotlinx.coroutines.flow.StateFlow

interface IWordsViewModel {
    val wordsState: StateFlow<WordsUiState?>
    val wordDialogState: StateFlow<WordDialogState>
    fun handleIntent(intent: WordsIntent)

    sealed class WordsIntent {
        data object OpenAddWordDialog : WordsIntent()
        data class OpenEditWordDialog(val word: WordState) : WordsIntent()
        data object CloseWordDialog : WordsIntent()
        data class AddNewWord(val word: WordState) : WordsIntent()
        data class DeleteWord(val word: WordState) : WordsIntent()
        data class UpdateWord(val word: WordState) : WordsIntent()
    }
}