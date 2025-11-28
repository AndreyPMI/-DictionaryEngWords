package com.andreypmi.core_domain.models

data class Word(
    val id : String?,
    val idCategory : String,
    val word: String,
    val translate : String,
    val description: String?
)
