package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam

class DeleteCategoryUseCase(private val repository: WordRepository) :
    UseCaseWithParam<Boolean, Category> {
    override suspend fun execute(params: Category): Boolean {
        return repository.deleteCategory(params)
    }
}