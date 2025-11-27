package com.andreypmi.user_feature.userScreen.nested_screens.notifications.models

import com.andreypmi.core_domain.models.settingsModel.NotificationSettings

data class NotificationsState(
    val settings: NotificationSettings = NotificationSettings.Default,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showTimePicker: TimePickerType? = null
) {
    val isEnabled: Boolean get() = settings.isEnabled
}