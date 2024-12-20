package com.andreypmi.dictionaryforwords.presentation.features.words

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.dictionaryforwords.domain.models.Category
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.usecase.WordUseCasesFacade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
data class WordsUiState(
    var category: Category,
    var words: List<Word>
)
enum class DialogState{
    NONE, ADD, EDIT
}

class WordsViewModel(
    private val wordUseCase: WordUseCasesFacade
) : ViewModel(), IWordsViewModel {
    private  var wordsList: MutableList<Word> = mutableListOf()
    private val _uiState = MutableStateFlow(
        WordsUiState(
            category = Category(1, category = ""),//TODO id=0
            words = emptyList()
        )
    )
    private val _dialogState = MutableStateFlow(DialogState.NONE)
    private var _editWord : Word? = null
    override val editWord: Word?
        get() = _editWord
    init {
        try {
            viewModelScope.launch {
                wordsList = wordUseCase.getAllWords().toMutableList()
                updateWordsList()
            }

        } catch (e: Exception) {
            Log.d("corrutine", "$e")
        }
    }

    override val dialogState = _dialogState.asStateFlow()
    override val uiState = _uiState.asStateFlow()

    override fun openAddWordDialog() {
        _dialogState.value = DialogState.ADD
    }
    override fun openEditWordDialog(word: Word){
        _editWord = word
        _dialogState.value = DialogState.EDIT
    }
    override fun closeWordDialog() {
        _dialogState.value = DialogState.NONE
    }

    override fun addNewWord(word: Word) {
        viewModelScope.launch {
            wordUseCase.insertWord(word)?.let {
                wordsList += it
                Log.d("addWord", wordsList.toString())
                updateWordsList()
            }
        }
    }

    override fun deleteWord(word: Word) {
        Log.d("deleteWord","$word")
        viewModelScope.launch {
            if (wordUseCase.deleteWord(word)) {
                wordsList -= word
                updateWordsList()
            }
    }}

    override fun editWord(word: Word) {
        Log.d("openEditWordDialog","${word.id}, ${word.word}")
        if (_editWord == null){return}
        viewModelScope.launch {
            if(wordUseCase.updateWord(word)){
                Log.d("openEditWordDialog","${word.id}")
                val index = wordsList.indexOfFirst { it.id == word.id }
                    if (index != -1) {
                    wordsList[index] = word
                }
                updateWordsList()
            }
        }
    }


    private fun updateWordsList() {
        _uiState.update { currentState ->
            currentState.copy(words = wordsList.toList())
        }
    }
}