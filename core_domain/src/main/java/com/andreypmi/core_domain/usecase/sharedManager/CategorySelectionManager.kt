package com.andreypmi.core_domain.usecase.sharedManager

import com.andreypmi.core_domain.models.Category
import kotlinx.coroutines.flow.SharedFlow

interface CategorySelectionManager {
    val categorySelectedFlow: SharedFlow<Category>
    suspend fun notifyCategorySelected(category: Category)
}