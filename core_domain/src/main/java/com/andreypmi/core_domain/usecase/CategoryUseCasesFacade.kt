package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.categoryUseCases.DeleteCategoryUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.GetAllCategoryUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.GetCategoryByIdUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.GetLastSelectedCategoryUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.InsertCategoryUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.SaveLastSelectedCategoryUseCase
import com.andreypmi.core_domain.usecase.categoryUseCases.UpdateCategoryUseCase
import com.andreypmi.logger.Logger
import javax.inject.Inject

class CategoryUseCasesFacade @Inject constructor(
    repository: WordRepository,
    logger: Logger
) {
    private val getLastSelectedCategoryUseCase = GetLastSelectedCategoryUseCase(repository,logger)
    private val saveLastSelectedCategoryUseCase = SaveLastSelectedCategoryUseCase(repository,logger)
    private val getAllCategoryUseCase = GetAllCategoryUseCase(repository)
    private val deleteCategoryUseCase = DeleteCategoryUseCase(repository)
    private val insertCategoryUseCase = InsertCategoryUseCase(repository)
    private val updateCategoryUseCase = UpdateCategoryUseCase(repository)
    private val getCategoryByIdUseCase = GetCategoryByIdUseCase(repository)

    suspend fun getLastSelectedCategory() = getLastSelectedCategoryUseCase.execute()
    suspend fun saveLastSelectedCategory(category: Category) =
        saveLastSelectedCategoryUseCase.execute(category)

    suspend fun getAllCategory() = getAllCategoryUseCase.execute()
    suspend fun deleteCategory(category: Category) = deleteCategoryUseCase.execute(category)
    suspend fun insertCategory(category: Category) = insertCategoryUseCase.execute(category)
    suspend fun updateCategory(category: Category) = updateCategoryUseCase.execute(category)
    suspend fun getCategoryById(id: Int) = getCategoryByIdUseCase.execute(id)
}