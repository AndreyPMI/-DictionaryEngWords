package com.andreypmi.core_domain.usecase.sharedUseCases

import com.andreypmi.core_domain.models.SharedCategory
import com.andreypmi.core_domain.repository.ShareStorageRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam
import javax.inject.Inject

class LoadSharedCategoryUseCase @Inject constructor(
    private val shareStorageRepository: ShareStorageRepository
) : UseCaseWithParam<Result<SharedCategory>, String> {

    override suspend fun execute(params: String): Result<SharedCategory> {
        return shareStorageRepository.getSharedCategory(params)
    }
}