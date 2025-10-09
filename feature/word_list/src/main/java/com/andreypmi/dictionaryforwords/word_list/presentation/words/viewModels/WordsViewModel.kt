package com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.usecase.CategoryUseCasesFacade
import com.andreypmi.core_domain.usecase.WordUseCasesFacade
import com.andreypmi.core_domain.usecase.sharedManager.CategorySelectionManager
import com.andreypmi.dictionaryforwords.word_list.presentation.models.Mapper
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WordsViewModel(
    private val wordUseCase: WordUseCasesFacade,
    private val categoryUseCases: CategoryUseCasesFacade,
    private val categorySelectionManager: CategorySelectionManager
) : ViewModel(), IWordsViewModel {

    private val _wordsState = MutableStateFlow<WordsUiState?>(null)
    override val wordsState: StateFlow<WordsUiState?> = _wordsState.asStateFlow()

    private val _wordDialogState = MutableStateFlow<WordDialogState>(WordDialogState.Hidden)
    override val wordDialogState: StateFlow<WordDialogState> = _wordDialogState.asStateFlow()

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
        viewModelScope.launch {
            categorySelectionManager.categorySelectedFlow.collect { category ->
                loadWords(category.id)
            }
        }
        loadInitialWords()
    }
    private fun loadInitialWords() {
        viewModelScope.launch {
            val lastCategoryId = categoryUseCases.getLastSelectedCategory()
            if (lastCategoryId != -1) {
                if (lastCategoryId != null) {
                    loadWords(lastCategoryId)
                }else{
                    loadWords(1)
                }
            }
        }
    }
    private fun loadWords(categoryId: Int) {
        viewModelScope.launch {
            try {
                val selectedCategory =
                    categoryUseCases.getCategoryById(categoryId)?: return@launch

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
                            words = words.map { Mapper.fromDomainModel(it) },
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

    private suspend fun getDefaultCategory(): Category {
        val categoryId = categoryUseCases.getLastSelectedCategory()
        if (categoryId != null) {
            return categoryUseCases.getCategoryById(categoryId)?: Category(1, "Default")
        }
        return Category(1, "Default")
    }

    private fun openAddWordDialog() {
       _wordDialogState.value = WordDialogState.Add
    }

    private fun openEditWordDialog(word: WordState) {
        _wordDialogState.value = WordDialogState.Edit(word)
    }

    private fun closeWordDialog() {
        _wordDialogState.value = WordDialogState.Hidden
    }

    private fun addNewWord(word: WordState) {
        viewModelScope.launch {
            wordUseCase.insertWord(Mapper.toDomainModel(word))
            closeWordDialog()
            loadWords(word.idCategory)
        }
    }

    private fun deleteWord(word: WordState) {
        Log.d("deleteWord", "$word")
        viewModelScope.launch {
            wordUseCase.deleteWord(Mapper.toDomainModel(word))
            _wordsState.value?.category?.id?.let { loadWords(it) }
        }
    }

    private fun updateWord(word: WordState) {
        viewModelScope.launch {
            if (wordUseCase.updateWord(Mapper.toDomainModel(word))) {
                closeWordDialog()
                _wordsState.value?.category?.id?.let { loadWords(it) }
            }
        }
    }
}