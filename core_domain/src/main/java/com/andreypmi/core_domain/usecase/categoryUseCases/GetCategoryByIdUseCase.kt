package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(private val repository: WordRepository) :
    UseCaseWithParam<Category?, Int> {
    override suspend fun execute(params: Int): Category? {
        return repository.getCategoryById(params)
    }
}