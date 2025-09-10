package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.repository.PreferencesDataSource
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam
import com.andreypmi.core_domain.usecase.UseCaseWithoutParam

const val CATEGORY_KEY = "category"

class GetLastSelectedCategoryUseCase(private val repository: WordRepository) :
    UseCaseWithoutParam<Int?> {
    override suspend fun execute(): Int? {
        kotlin.runCatching {
            return repository.loadLastSelectedCategory(key = CATEGORY_KEY)?.toInt()
        }.getOrElse {
            return null
        }
    }
}
