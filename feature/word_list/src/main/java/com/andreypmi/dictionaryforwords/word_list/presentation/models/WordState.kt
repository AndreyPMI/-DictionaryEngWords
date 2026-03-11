package com.andreypmi.dictionaryforwords.word_list.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class WordState(
    val id : String?,
    val idCategory : String,
    val word: String,
    val translation : String,
    val description: String
)