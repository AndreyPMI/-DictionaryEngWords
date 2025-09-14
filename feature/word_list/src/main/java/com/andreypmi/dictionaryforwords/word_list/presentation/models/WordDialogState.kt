package com.andreypmi.dictionaryforwords.word_list.presentation.models


sealed class WordDialogState {
    data object Hidden : WordDialogState()
    data object Add : WordDialogState()
    data class Edit(val word: WordState) : WordDialogState()
}