package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam
import com.andreypmi.logger.Logger

class SaveLastSelectedCategoryUseCase(
    private val repository: WordRepository,
    private val logger: Logger
) :
    UseCaseWithParam<Unit, Category> {
    override suspend fun execute(params: Category) {
        repository.saveLastSelectedCategory(CATEGORY_KEY, params)
    }
}