package com.andreypmi.core_domain.models.settingsModel

import java.time.DayOfWeek

data class NotificationSettings(
    val isEnabled: Boolean = false,
    val timeRange: TimeRange = TimeRange.Default,
    val interval: NotificationInterval = NotificationInterval.HOURS_2,
    val contentType: NotificationContentType = NotificationContentType.WORD_TRANSLATION,
    val daysOfWeek: Set<DayOfWeek> = DayOfWeek.entries.toSet()
) {
    companion object {
        val Default = NotificationSettings()
    }
}