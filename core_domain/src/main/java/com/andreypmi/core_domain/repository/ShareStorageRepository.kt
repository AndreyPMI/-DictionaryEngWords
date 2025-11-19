package com.andreypmi.core_domain.repository

import com.andreypmi.core_domain.models.SharedCategory

interface ShareStorageRepository {
    suspend fun uploadCategoryForSharing(sharedCategory: SharedCategory): Result<Unit>
    suspend fun getSharedCategory(shareId: String): Result<SharedCategory>
    fun getShareLink(shareId: String): String
}