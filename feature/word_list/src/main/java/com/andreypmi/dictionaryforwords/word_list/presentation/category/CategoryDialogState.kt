package com.andreypmi.dictionaryforwords.word_list.presentation.category

import com.andreypmi.core_domain.models.Category

sealed class CategoryDialogState {
    data object Hidden : CategoryDialogState()
    data object Add : CategoryDialogState()
    data class Edit(val category: Category) : CategoryDialogState()
    data class Delete(val category: Category) : CategoryDialogState()
}