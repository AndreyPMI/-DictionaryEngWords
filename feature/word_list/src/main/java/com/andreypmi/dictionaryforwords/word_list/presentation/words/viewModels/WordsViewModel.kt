package com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.usecase.CategoryUseCasesFacade
import com.andreypmi.core_domain.usecase.WordUseCasesFacade
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogType
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import com.andreypmi.dictionaryforwords.word_list.presentation.words.IWordsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WordsViewModel(
    private val wordUseCase: WordUseCasesFacade,
    private val categoryUseCases: CategoryUseCasesFacade
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
            is IWordsViewModel.WordsIntent.SelectCategory -> selectCategory(intent.category)
        }
    }

    init {
        loadCategories()
        getWords()
    }

    private fun getWords() {
        viewModelScope.launch {
            _categoryState
                .filter { it is CategoryState.Success }
                .map { (it as CategoryState.Success).categories }
                .collect { categories ->
                    loadWords(categories)
                }
        }
    }

    private fun loadWords(categories: List<Category>) {
        viewModelScope.launch {
            try {
                val categoryId = categoryUseCases.getLastSelectedCategory()
                val selectedCategory = categories.find { it.id == categoryId } ?: categories.first()

                wordUseCase.getAllWords(selectedCategory)
                    .catch { e ->
                        _wordsState.value = WordsUiState(
                            error = "Ошибка загрузки слов: ${e.message}",
                            category = selectedCategory,
                            words = emptyList()
                        )
                    }
                    .collect { words ->
                        _wordsState.value = WordsUiState(
                            words = words,
                            category = selectedCategory,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _wordsState.value = WordsUiState(
                    error = "Ошибка: ${e.message}",
                    category = null,
                    words = emptyList()
                )
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoryState.value = CategoryState.Loading
            try {
                categoryUseCases.getAllCategory().collect { categories ->
                    when {
                        categories.isEmpty() -> _categoryState.value = CategoryState.Empty
                        else -> _categoryState.value = CategoryState.Success(categories)
                    }
                }
            } catch (e: Exception) {
                _categoryState.value =
                    CategoryState.Error("Failed to load categories: ${e.message}")
                Log.d("WordsViewModel", "Error loading categories: $e")
            }
        }
    }

    private fun selectCategory(category: Category) {
        Log.d("AAA12", "${_wordsState.value}")
        _wordsState.value = _wordsState.value?.copy(category = category)
    }

    private fun openAddWordDialog() {
        Log.d("AAAdialogopen", "+")
        _dialogState.update { it.copy(dialogType = DialogType.ADD) }
    }

    private fun openEditWordDialog(word: Word) {
        _dialogState.update { it.copy(dialogType = DialogType.EDIT, editWord = word) }
    }

    private fun closeWordDialog() {
        _dialogState.update { it.copy(dialogType = DialogType.NONE, editWord = null) }
    }

    private fun addNewWord(word: Word) {
        viewModelScope.launch {
            wordUseCase.insertWord(word)
        }
    }

    private fun deleteWord(word: Word) {
        Log.d("deleteWord", "$word")
        viewModelScope.launch {
            wordUseCase.deleteWord(word)
        }
    }

    private fun updateWord(word: Word) {
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