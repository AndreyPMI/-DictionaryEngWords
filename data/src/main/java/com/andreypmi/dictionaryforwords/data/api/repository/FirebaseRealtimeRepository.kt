package com.andreypmi.dictionaryforwords.data.api.repository

import android.util.Log
import com.andreypmi.core_domain.models.SharedCategory
import com.andreypmi.core_domain.repository.ShareStorageRepository
import com.andreypmi.dictionaryforwords.data.api.FirebaseConfig
import com.andreypmi.core_domain.exception.ShareStorageException
import com.andreypmi.dictionaryforwords.data.api.http.HttpClientApi
import com.andreypmi.dictionaryforwords.data.api.mapper.SharedCategoryMapper
import com.andreypmi.dictionaryforwords.data.api.models.NetworkSharedCategory
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FirebaseRealtimeRepository @Inject constructor(
    private val httpClientApi: HttpClientApi,
    private val firebaseConfig: FirebaseConfig
) : ShareStorageRepository {

    private val baseUrl: String
        get() = "${firebaseConfig.databaseUrl}/shared_categories"

    override suspend fun uploadCategoryForSharing(sharedCategory: SharedCategory): Result<Unit> {
        return runCatching {
            cleanupAllExpiredShares()

            val networkCategory = SharedCategoryMapper.domainToNetwork(sharedCategory)
            val url = "$baseUrl/${sharedCategory.id}.json"

            httpClientApi.put(url, networkCategory)
        }.fold(
            onSuccess = {
                Log.d("AAA", "Upload SUCCESS")
                Result.success(Unit)
            },
            onFailure = {
                Log.d("AAA", "Upload FAILED: ${it.message}")
                Result.failure(ShareStorageException("Upload failed: ${it.message}"))
            }
        )
    }

    override suspend fun getSharedCategory(shareId: String): Result<SharedCategory> {
        return runCatching {
            val url = "$baseUrl/$shareId.json"
            val jsonString = httpClientApi.getString(url)

            val networkCategory = Json.decodeFromString<NetworkSharedCategory>(jsonString)

            if (System.currentTimeMillis() > networkCategory.expiresAt) {
                throw ShareStorageException("Share expired")
            }

            SharedCategoryMapper.networkToDomain(networkCategory)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = {
                when {
                    it is ShareStorageException -> Result.failure(it)
                    else -> Result.failure(ShareStorageException("Failed to load share: ${it.message}"))
                }
            }
        )
    }
    override fun getShareLink(shareId: String): String {
        return "$baseUrl/$shareId.json"
    }
    private suspend fun cleanupAllExpiredShares() {
        try {
            val allSharesJson = httpClientApi.getString("$baseUrl.json")
            val allShares = Json.decodeFromString<Map<String, NetworkSharedCategory>?>(allSharesJson)

            allShares?.forEach { (shareId, data) ->
                if (System.currentTimeMillis() > data.expiresAt) {
                    httpClientApi.delete("$baseUrl/$shareId.json")
                }
            }
        } catch (e: Exception) {
        }
    }
}