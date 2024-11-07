package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WordsUiState(
    var words: List<com.andreypmi.dictionaryforwords.domain.models.Word>
)

class WordsViewModel(repository: com.andreypmi.dictionaryforwords.domain.repository.WordRepository) : ViewModel(), IWordsViewModel {
    private val _uiState = MutableStateFlow(WordsUiState(emptyList()))

    override val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            uiState.value.words = repository.getAllWords()
        }
    }
}