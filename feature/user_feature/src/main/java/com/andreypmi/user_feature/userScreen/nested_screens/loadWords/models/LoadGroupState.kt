package com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models

import com.andreypmi.core_domain.models.SharedCategory

data class LoadGroupState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val scannedData: String? = null,
    val loadedCategory: SharedCategory? = null
)