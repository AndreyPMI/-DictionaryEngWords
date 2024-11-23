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

    override val uiState = _uiState.asStateFlow()

    override fun onClickAdd() {
        Log.d("clickAdd","1")
    }

    init {
        try {

        viewModelScope.launch {
            val words = wordUseCase.getAllWords()
            _uiState.update { currentState ->
                currentState.copy(words = words)
            }
        }

        }catch (e: Exception){
            Log.d("corrutine","$e")
        }
    }
}