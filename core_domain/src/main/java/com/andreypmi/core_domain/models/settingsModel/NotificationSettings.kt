package com.andreypmi.core_domain.models.settingsModel

import java.time.DayOfWeek

data class NotificationSettings(
    val isEnabled: Boolean = false,
    val timeRange: TimeRange = TimeRange.Default,
    val interval: NotificationInterval = NotificationInterval.HOURS_2,
    val contentType: NotificationContentType = NotificationContentType.WORD_TRANSLATION,
    val daysOfWeek: Set<DayOfWeek> = DayOfWeek.entries.toSet(),
    val selectedCategoryId: String? = null,
    val lastShownWordIndex: Map<String, Int> = emptyMap()
) {
    fun getLastShownIndexForCategory(categoryId: String?): Int {
        val key = categoryId ?: "all"
        return lastShownWordIndex[key] ?: -1
    }

    fun withUpdatedIndex(categoryId: String?, newIndex: Int): NotificationSettings {
        val key = categoryId ?: "all"
        val updatedMap = lastShownWordIndex.toMutableMap().apply {
            this[key] = newIndex
        }
        return this.copy(lastShownWordIndex = updatedMap)
    }
    companion object {
        val Default = NotificationSettings()
    }
}