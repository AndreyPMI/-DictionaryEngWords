package com.andreypmi.dictionaryforwords.presentation.features.words

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var words: List<Word>
)

class WordsViewModel(
    private val wordUseCase: WordUseCasesFacade
) : ViewModel(), IWordsViewModel {

    private val _uiState = MutableStateFlow(WordsUiState(emptyList()))
    private val _dialogState = MutableStateFlow(false)
    override val dialogState: StateFlow<Boolean>
        get() = _dialogState.asStateFlow()

    override val uiState = _uiState.asStateFlow()
    override fun openAddWordDialog() {
        _dialogState.value = true
    }

    override fun closeAddWordDialog() {
        _dialogState.value = false
    }

    override fun addNewWord(word: Word) {
        viewModelScope.launch {
            wordUseCase.insertWord(word)
        }
    }

    init {
        try {
            viewModelScope.launch {
                val words = wordUseCase.getAllWords()
                _uiState.update { currentState ->
                    currentState.copy(words = words)
                }
            }

        } catch (e: Exception) {
            Log.d("corrutine", "$e")
        }
    }
}