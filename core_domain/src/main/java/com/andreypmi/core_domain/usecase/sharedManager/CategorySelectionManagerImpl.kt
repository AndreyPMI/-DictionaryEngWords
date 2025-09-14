package com.andreypmi.core_domain.usecase.sharedManager

import com.andreypmi.core_domain.models.Category
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object CategorySelectionManagerImpl : CategorySelectionManager {
    private val _categorySelectedFlow = MutableSharedFlow<Category>(
        replay = 1,
    )
    override val categorySelectedFlow: SharedFlow<Category> = _categorySelectedFlow

    override suspend fun notifyCategorySelected(category: Category) {
        _categorySelectedFlow.emit(category)
    }
}