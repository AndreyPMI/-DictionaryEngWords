package com.andreypmi.dictionaryforwords.word_list.presentation.models

import androidx.compose.runtime.Stable

@Stable
data class WordState(
    val id : Int?,
    val idCategory : Int,
    val word: String,
    val translation : String,
    val description: String
)