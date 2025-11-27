package com.andreypmi.user_feature.userScreen.nested_screens.notifications.models

import androidx.compose.ui.autofill.ContentType
import com.andreypmi.core_domain.models.settingsModel.NotificationContentType
import com.andreypmi.core_domain.models.settingsModel.NotificationInterval
import com.andreypmi.core_domain.models.settingsModel.TimeRange
import java.time.DayOfWeek

sealed interface NotificationsEvent {
    data object NavigateBack : NotificationsEvent

    data object LoadSettings : NotificationsEvent

    data class SetEnabled(val enabled: Boolean) : NotificationsEvent
    data class SetInterval(val interval: NotificationInterval) : NotificationsEvent
    data class SetContentType(val contentType: NotificationContentType) : NotificationsEvent
    data class SetDaysOfWeek(val days: Set<DayOfWeek>) : NotificationsEvent

    data class SetTimeRange(val timeRange: TimeRange) : NotificationsEvent
    data object ShowStartTimePicker : NotificationsEvent
    data object ShowEndTimePicker : NotificationsEvent
    data object HideTimePicker : NotificationsEvent

    data object SaveSettings : NotificationsEvent

    data object ClearError : NotificationsEvent
}