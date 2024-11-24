package com.andreypmi.dictionaryforwords.presentation.features.words

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.dictionaryforwords.domain.models.Category
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.domain.usecase.DeleteWordUseCase
import com.andreypmi.dictionaryforwords.domain.usecase.WordUseCasesFacade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
data class WordsUiState(
    var category: Category,
    var words: List<Word>
)

class WordsViewModel(
    private val wordUseCase: WordUseCasesFacade
) : ViewModel(), IWordsViewModel {
    private lateinit var wordsList: List<Word>
    private val _uiState = MutableStateFlow(
        WordsUiState(
            category = Category(1, category = ""),//TODO id=0
            words = emptyList()
        )
    )
    private val _dialogState = MutableStateFlow(false)

    init {
        try {
            viewModelScope.launch {
                wordsList = wordUseCase.getAllWords()
                _uiState.update { currentState ->
                    currentState.copy(words = wordsList)
                }
            }

        } catch (e: Exception) {
            Log.d("corrutine", "$e")
        }
    }

    override val dialogState = _dialogState.asStateFlow()
    override val uiState = _uiState.asStateFlow()

    override fun openAddWordDialog() {
        _dialogState.value = true
    }

    override fun closeAddWordDialog() {
        _dialogState.value = false
    }

    override fun addNewWord(word: Word) {
        viewModelScope.launch {
            if (wordUseCase.insertWord(word)) {
                wordsList += word
                updateWordsList()
            }
        }
    }

    override fun deleteWord(word: Word) {
        viewModelScope.launch {
            if (wordUseCase.insertWord(word)) {
                wordsList -= word
                updateWordsList()
            }
    }}

    private fun updateWordsList() {
        _uiState.update { currentState ->
            currentState.copy(words = wordsList)
        }
    }
}