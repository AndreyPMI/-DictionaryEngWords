package com.andreypmi.user_feature.userScreen.nested_screens.notifications.models

import com.andreypmi.core_domain.models.settingsModel.NotificationSettings

data class NotificationSettingsState(
    val settings: NotificationSettings = NotificationSettings(),
    val isLoading: Boolean = false,
    val error: String? = null
)