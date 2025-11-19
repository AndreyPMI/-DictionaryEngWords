package com.andreypmi.dictionaryforwords.data.api.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkSharedWord(
    val original: String,
    val translation: String,
    val description: String? = null
)