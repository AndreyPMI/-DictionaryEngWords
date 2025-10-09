package com.andreypmi.learning.learningScreen.viewModels

import com.andreypmi.core_domain.models.Word

sealed class SessionState {
    data object Idle : SessionState()
    data object Loading : SessionState()
    data class Active(
        val words: List<Word>,
        val currentIndex: Int,
        val difficultWords: List<Word>,
        val flippedCardIds: Set<Int> = emptySet()
    ) : SessionState()

    data object Completed : SessionState()
    data class Error(val message: Int) : SessionState()
    data object EmptyCategory : SessionState()
    data object SessionFinished : SessionState()
}
