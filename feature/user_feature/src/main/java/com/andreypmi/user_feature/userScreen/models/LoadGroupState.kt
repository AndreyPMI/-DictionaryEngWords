package com.andreypmi.user_feature.userScreen.models

data class LoadGroupState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)