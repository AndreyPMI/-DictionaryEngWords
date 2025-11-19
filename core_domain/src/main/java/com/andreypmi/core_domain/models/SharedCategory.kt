package com.andreypmi.core_domain.models

data class SharedCategory(
    val id: String,
    val categoryName: String,
    val words: List<Word>
)