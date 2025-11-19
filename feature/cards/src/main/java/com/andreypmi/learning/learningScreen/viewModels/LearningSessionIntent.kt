package com.andreypmi.learning.learningScreen.viewModels

import com.andreypmi.core_domain.models.Word

sealed class LearningSessionIntent {
    data object ResetSession : LearningSessionIntent()
    data class StartSession(val categoryId: String) : LearningSessionIntent()
    data class WordSwiped(val isKnown: Boolean, val word: Word) : LearningSessionIntent()
    data class CardFlipped(val cardId: String) : LearningSessionIntent()
    data object RetryWithDifficultWords : LearningSessionIntent()
}