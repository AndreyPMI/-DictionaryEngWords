package com.andreypmi.dictionaryforwords.data.storage.dto

data class CategoryWithWordCountModel(
    val id: String,
    val categoryName: String,
    val wordCount: Int
)