package com.andreypmi.dictionaryforwords.domain.models

data class Word(
    val id: Int,
    val word: String,
    val translate : String,
    val description: String
)
