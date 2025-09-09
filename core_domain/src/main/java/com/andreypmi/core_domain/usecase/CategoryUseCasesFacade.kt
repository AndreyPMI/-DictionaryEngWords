package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.categoryUseCases.GetLastSelectedCategoryUseCase
import javax.inject.Inject

class CategoryUseCasesFacade@Inject constructor(
    repository: WordRepository
)   {
    private val getLastSelectedCategoryUseCase : GetLastSelectedCategoryUseCase(repository)
}