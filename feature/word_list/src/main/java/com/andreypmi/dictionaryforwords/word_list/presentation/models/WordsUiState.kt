package com.andreypmi.dictionaryforwords.word_list.presentation.models

import androidx.compose.runtime.Immutable
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word

@Immutable
data class WordsUiState(
    var category: Category,
    var words: List<Word>
)