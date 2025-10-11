package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.CategoryWithWordCount
import com.andreypmi.core_domain.repository.WordRepository
import javax.inject.Inject

class GetPaginatedCategoriesUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke(page: Int, limit: Int = 50): List<CategoryWithWordCount> {
        val offset = page * limit
        return repository.getCategoriesWithWordCount(offset, limit)
    }

    suspend fun hasMoreCategories(currentPage: Int, limit: Int = 50): Boolean {
        val totalCount = repository.getCategoriesCount()
        val loadedCount = (currentPage + 1) * limit
        return totalCount > loadedCount
    }
}