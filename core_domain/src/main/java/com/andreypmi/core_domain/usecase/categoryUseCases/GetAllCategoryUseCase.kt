package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithoutParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val repository: WordRepository) : UseCaseWithoutParam<Flow<List<Category>>> {
    override suspend fun execute(): Flow<List<Category>> {
       return repository.getAllCategories()
    }
}