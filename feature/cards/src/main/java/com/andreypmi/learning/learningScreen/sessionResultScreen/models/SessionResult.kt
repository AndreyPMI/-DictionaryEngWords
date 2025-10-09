package com.andreypmi.learning.learningScreen.sessionResultScreen.models

import com.andreypmi.core_domain.models.Word

data class SessionResult(
    val categoryId: Int,
    val categoryName: String,
    val allWords: List<Word>,
    val difficultWords: List<Word>
)