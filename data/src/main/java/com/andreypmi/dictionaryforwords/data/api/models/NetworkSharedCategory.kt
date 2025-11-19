package com.andreypmi.dictionaryforwords.data.api.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkSharedCategory(
    val id: String,
    val categoryName: String,
    val words: List<NetworkSharedWord>,
    val createdAt: Long,
    val expiresAt: Long
)