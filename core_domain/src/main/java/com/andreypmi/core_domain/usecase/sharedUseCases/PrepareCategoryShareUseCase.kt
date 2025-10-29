package com.andreypmi.core_domain.usecase.sharedUseCases

import com.andreypmi.core_domain.exception.ShareStorageException
import com.andreypmi.core_domain.models.QrCodeData
import com.andreypmi.core_domain.models.ShareResult
import com.andreypmi.core_domain.models.SharedCategory
import com.andreypmi.core_domain.repository.ShareStorageRepository
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.service.QrCodeService
import com.andreypmi.core_domain.usecase.UseCaseWithParam
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class PrepareCategoryShareUseCase @Inject constructor(
    private val wordRepository: WordRepository,
    private val shareStorageRepository: ShareStorageRepository,
    private val qrCodeRepository: QrCodeService
) : UseCaseWithParam<Result<ShareResult>, String> {


    override suspend fun execute(params: String): Result<ShareResult> {
        return runCatching {
            val category = wordRepository.getCategoryById(params)
                ?: throw ShareStorageException("Category not found")

            val words = wordRepository.getWordsByCategoryId(params).first()

            val sharedCategory = SharedCategory(
                id = UUID.randomUUID().toString(),
                categoryName = category.category,
                words = words
            )

            shareStorageRepository.uploadCategoryForSharing(sharedCategory)

            val shareLink = shareStorageRepository.getShareLink(sharedCategory.id)

            val qrCodeData = qrCodeRepository.generateQrCode(shareLink)
            ShareResult(
                shareId = sharedCategory.id,
                shareLink = shareLink,
                qrCodeData = QrCodeData(qrCodeData)
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = {
                println(it.message)
                Result.failure(it)
            }
        )
    }
}
