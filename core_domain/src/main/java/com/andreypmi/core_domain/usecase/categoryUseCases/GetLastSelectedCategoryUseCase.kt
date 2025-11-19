package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithoutParam
import com.andreypmi.logger.Logger

const val CATEGORY_KEY = "category"

class GetLastSelectedCategoryUseCase(
    private val repository: WordRepository,
    private val logger: Logger
) :
    UseCaseWithoutParam<String?> {
    override suspend fun execute(): String? {
        return try {
            repository.loadLastSelectedCategory(key = CATEGORY_KEY)
        } catch (e: Exception) {
            logger.error("Failed to load last category", e.message.toString())
            null
        }
    }
}
