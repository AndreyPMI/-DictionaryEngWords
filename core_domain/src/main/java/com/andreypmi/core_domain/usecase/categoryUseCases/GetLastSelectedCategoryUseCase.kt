package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithoutParam
import com.andreypmi.logger.Logger

const val CATEGORY_KEY = "category"

class GetLastSelectedCategoryUseCase(private val repository: WordRepository,private val logger: Logger) :
    UseCaseWithoutParam<Int?> {
    override suspend fun execute(): Int? {
        kotlin.runCatching {
            logger.debug("AAA","+_+")
            val result = repository.loadLastSelectedCategory(key = CATEGORY_KEY)
            logger.debug("AAA","$result")
            return result
        }.getOrElse {
            logger.debug("AAA","null")
            return null
        }
    }
}
