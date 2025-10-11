package com.andreypmi.user_feature.userScreen.models

import com.andreypmi.core_domain.models.Category

data class ShareGroupState(
    val groups: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)