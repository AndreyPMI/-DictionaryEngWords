package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.categoryUseCases.GetAllCategoryUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.GetLastSelectedCategoryUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.SaveLastSelectedCategoryUseCase
import javax.inject.Inject

class CategoryUseCasesFacade@Inject constructor(
    repository: WordRepository
)   {
    private val getLastSelectedCategoryUseCase = GetLastSelectedCategoryUseCase(repository)
    private val saveLastSelectedCategoryUseCase = SaveLastSelectedCategoryUseCase(repository)
    private val getAllCategoryUseCase= GetAllCategoryUseCase(repository)

    suspend fun getLastSelectedCategory() = getLastSelectedCategoryUseCase.execute()
    suspend fun saveLastSelectedCategory(category: Category) = saveLastSelectedCategoryUseCase.execute(category)
    suspend fun getAllCategory() = getAllCategoryUseCase.execute()
}