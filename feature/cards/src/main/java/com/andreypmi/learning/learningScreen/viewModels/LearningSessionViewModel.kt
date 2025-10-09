package com.andreypmi.learning.learningScreen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.cards.R
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.usecase.categoryUseCases.GetCategoryByIdUseCase
import com.andreypmi.core_domain.usecase.wordsUseCases.GetAllWordsUseCase
import com.andreypmi.learning.learningScreen.sessionResultScreen.models.SessionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LearningSessionViewModel(
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
) : ViewModel() {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Idle)
    val sessionState: StateFlow<SessionState> = _sessionState

    private val _sessionResult = MutableStateFlow<SessionResult?>(null)
    val sessionResult: StateFlow<SessionResult?> = _sessionResult

    fun processIntent(intent: LearningSessionIntent) {
        when (intent) {
            is LearningSessionIntent.ResetSession -> resetSession()
            is LearningSessionIntent.StartSession -> startSession(intent.categoryId)
            is LearningSessionIntent.WordSwiped -> onWordSwiped(intent.isKnown, intent.word)
            is LearningSessionIntent.CardFlipped -> flipCard(intent.cardId)
            is LearningSessionIntent.RetryWithDifficultWords -> retryWithDifficultWords()
        }
    }

    private fun startSession(categoryId: Int) {
        _sessionState.value = SessionState.Loading
        viewModelScope.launch {
            try {
                val category = getCategoryByIdUseCase.execute(categoryId)
                    ?: throw IllegalArgumentException("Category not found")

                val words = getAllWordsUseCase.execute(category).first()

                if (words.isEmpty()) {
                    _sessionState.value = SessionState.EmptyCategory
                    return@launch
                }

                _sessionState.value = SessionState.Active(
                    words = words,
                    currentIndex = 0,
                    difficultWords = emptyList(),
                    flippedCardIds = emptySet()
                )
            } catch (e: Exception) {
                _sessionState.value = SessionState.Error(R.string.failed_to_load_words)
            }
        }
    }

    private fun onWordSwiped(isKnown: Boolean, word: Word) {
        val currentState = _sessionState.value as? SessionState.Active ?: return
        val updatedDifficultWords = if (!isKnown) {
            currentState.difficultWords + word
        } else {
            currentState.difficultWords
        }

        val isLastCard = currentState.currentIndex + 1 == currentState.words.size

        if (isLastCard) {
            val result = SessionResult(
                categoryId = word.idCategory,
                allWords = currentState.words,
                difficultWords = updatedDifficultWords,
                categoryName = word.idCategory.toString(),
            )
            _sessionResult.value = result
            _sessionState.value = SessionState.Completed
        } else {
            _sessionState.value = currentState.copy(
                currentIndex = currentState.currentIndex + 1,
                difficultWords = updatedDifficultWords
            )
        }
    }

    private fun flipCard(cardId: Int) {
        val currentState = _sessionState.value as? SessionState.Active ?: return

        val updatedFlipped: Set<Int> =
            if (cardId in currentState.flippedCardIds) {
                currentState.flippedCardIds - cardId
            } else {
                currentState.flippedCardIds + cardId
            }

        _sessionState.value = currentState.copy(flippedCardIds = updatedFlipped)
    }

    private fun resetSession() {
        _sessionState.value = SessionState.SessionFinished
        _sessionResult.value = null
    }

    private fun retryWithDifficultWords() {
        val currentResult = _sessionResult.value ?: return

        if (currentResult.difficultWords.isEmpty()) {
            resetSession()
            return
        }

        _sessionState.value = SessionState.Active(
            words = currentResult.difficultWords.shuffled(),
            currentIndex = 0,
            difficultWords = emptyList(),
            flippedCardIds = emptySet()
        )
    }
}