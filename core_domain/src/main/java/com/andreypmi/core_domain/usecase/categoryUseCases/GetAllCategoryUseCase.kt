package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithoutParam
import kotlinx.coroutines.flow.Flow

class GetAllCategoryUseCase(private val repository: WordRepository) : UseCaseWithoutParam<Flow<List<Category>>> {
    override suspend fun execute(): Flow<List<Category>> {
       return repository.getAllCategory()
    }
}