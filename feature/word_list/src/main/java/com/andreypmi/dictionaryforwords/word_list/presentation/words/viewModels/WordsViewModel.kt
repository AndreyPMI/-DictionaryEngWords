package com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.usecase.WordUseCasesFacade
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogType
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import com.andreypmi.dictionaryforwords.word_list.presentation.words.IWordsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WordsViewModel(
    private val wordUseCase: WordUseCasesFacade
    private val categoryUseCasesFacade: WordUseCasesFacade
) : ViewModel(), IWordsViewModel {
    private val _wordsState = MutableStateFlow<WordsUiState?>(null)
    private val _dialogState = MutableStateFlow(DialogState(null, DialogType.NONE))
    override val dialogState = _dialogState.asStateFlow()
    override val wordsState: StateFlow<WordsUiState?> = _wordsState.asStateFlow()
    private val _categoryState = MutableStateFlow<CategoryState>(CategoryState.Loading)
    override val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()

    override fun handleIntent(intent: IWordsViewModel.WordsIntent) {
        when (intent) {
            is IWordsViewModel.WordsIntent.AddNewWord -> addNewWord(intent.word)
            IWordsViewModel.WordsIntent.CloseWordDialog -> closeWordDialog()
            is IWordsViewModel.WordsIntent.DeleteWord -> deleteWord(intent.word)
            IWordsViewModel.WordsIntent.OpenAddWordDialog -> openAddWordDialog()
            is IWordsViewModel.WordsIntent.OpenEditWordDialog -> openEditWordDialog(intent.word)
            is IWordsViewModel.WordsIntent.UpdateWord -> updateWord(intent.word)
        }
    }

    init {
        loadWords()
        loadCategories()
    }

    private fun loadWords() {
        try {
            viewModelScope.launch {
                wordUseCase.getAllWords().collect { words ->
                    _wordsState.value = _wordsState.value?.copy(words = words) ?: WordsUiState(words)
                }
            }
        } catch (e: Exception) {
            Log.d("WordsViewModel", "Error loading words: $e")
        }
    }
    private fun loadCategories() {
        viewModelScope.launch {
            _categoryState.value = CategoryState.Loading
            try {
                categoryUseCase.getAllCategories().collect { categories ->
                    when {
                        categories.isEmpty() -> _categoryState.value = CategoryState.Empty
                        else -> _categoryState.value = CategoryState.Success(categories)
                    }
                }
            } catch (e: Exception) {
                _categoryState.value = CategoryState.Error("Failed to load categories: ${e.message}")
                Log.d("WordsViewModel", "Error loading categories: $e")
            }
        }
    }
    fun openAddWordDialog() {
        _dialogState.update { it.copy(dialogType = DialogType.ADD) }
    }

    fun openEditWordDialog(word: Word) {
        _dialogState.update { it.copy(dialogType = DialogType.EDIT, editWord = word) }
    }

    fun closeWordDialog() {
        _dialogState.update { it.copy(dialogType = DialogType.NONE, editWord = null) }
    }

    fun addNewWord(word: Word) {
        viewModelScope.launch {
            wordUseCase.insertWord(word)
        }
    }

    fun deleteWord(word: Word) {
        Log.d("deleteWord", "$word")
        viewModelScope.launch {
            wordUseCase.deleteWord(word)
        }
    }

    fun updateWord(word: Word) {
        Log.d("openEditWordDialog", "${word.id}, ${word.word}")
        if (_dialogState.value.editWord == null) {
            return
        }
        viewModelScope.launch {
            if (wordUseCase.updateWord(word)) {
                Log.d("openEditWordDialog", "+")
            }
        }
    }
}