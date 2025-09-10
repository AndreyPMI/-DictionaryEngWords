package com.andreypmi.dictionaryforwords.word_list.presentation.category

import com.andreypmi.core_domain.models.Category

data class CategoryDialogState(
    val showAddDialog: Boolean = false,
    val showEditDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val selectedCategory: Category? = null
)