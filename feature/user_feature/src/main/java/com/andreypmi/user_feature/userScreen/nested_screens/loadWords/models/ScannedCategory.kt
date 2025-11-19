package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models

import com.andreypmi.core_domain.models.Word

data class ScannedCategory(
    val categoryId: String,
    val categoryName: String,
    val words: List<Word>,
    val wordsCount: Int = words.size
)