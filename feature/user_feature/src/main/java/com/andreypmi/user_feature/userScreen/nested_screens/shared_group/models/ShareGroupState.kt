package com.andreypmi.user_feature.userScreen.nested_screens.shared_group.models

data class ShareGroupState(
    val categories: List<ShareGroupCategory> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    val error: String? = null,
    val isPreparingShare: Boolean = false,
)
