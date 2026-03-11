package com.andreypmi.dictionaryforwords.word_list.presentation.models

import androidx.compose.runtime.Immutable
import com.andreypmi.core_domain.models.Category

@Immutable
data class WordsUiState(
    val category: Category?,
    val words: List<WordState>,
    val error: String? = null
)