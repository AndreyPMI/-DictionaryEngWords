package com.andreypmi.dictionaryforwords.word_list.presentation.models

import androidx.compose.runtime.Stable
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word

@Stable
data class WordsUiState(
    val category: Category?,
    val words: List<Word>,
    val error: String? = null
)