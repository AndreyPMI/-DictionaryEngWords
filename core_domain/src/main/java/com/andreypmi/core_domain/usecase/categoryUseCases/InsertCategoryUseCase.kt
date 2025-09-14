package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam

class InsertCategoryUseCase (private val repository: WordRepository) :
    UseCaseWithParam<Category?, Category> {
    override suspend fun execute(params: Category): Category? {
        return repository.insertCategory(params)
    }
}