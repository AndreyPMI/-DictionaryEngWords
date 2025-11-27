package com.andreypmi.dictionaryforwords.data.bd.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.andreypmi.core_domain.models.settingsModel.NotificationContentType
import com.andreypmi.core_domain.models.settingsModel.NotificationInterval
import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import com.andreypmi.core_domain.models.settingsModel.TimeRange
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import javax.inject.Inject

class NotificationSettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : NotificationSettingsRepository {

    override suspend fun getSettings(): NotificationSettings {
        val preferences = dataStore.data.first()
        return NotificationSettings(
            isEnabled = preferences[PreferencesKeys.IS_ENABLED] ?: false,
            timeRange = TimeRange(
                startHour = preferences[PreferencesKeys.START_HOUR] ?: 10,
                startMinute = preferences[PreferencesKeys.START_MINUTE] ?: 0,
                endHour = preferences[PreferencesKeys.END_HOUR] ?: 22,
                endMinute = preferences[PreferencesKeys.END_MINUTE] ?: 0
            ),
            interval = NotificationInterval.fromMinutes(
                preferences[PreferencesKeys.INTERVAL_MINUTES] ?: 120
            ),
            contentType = when (preferences[PreferencesKeys.CONTENT_TYPE]) {
                "WORD_EXAMPLE" -> NotificationContentType.WORD_EXAMPLE
                "PHRASE_OF_DAY" -> NotificationContentType.PHRASE_OF_DAY
                "MINI_QUIZ" -> NotificationContentType.MINI_QUIZ
                else -> NotificationContentType.WORD_TRANSLATION
            },
            daysOfWeek = parseDaysOfWeek(preferences[PreferencesKeys.DAYS_OF_WEEK])
        )
    }

    override suspend fun saveSettings(settings: NotificationSettings) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_ENABLED] = settings.isEnabled
            preferences[PreferencesKeys.START_HOUR] = settings.timeRange.startHour
            preferences[PreferencesKeys.START_MINUTE] = settings.timeRange.startMinute
            preferences[PreferencesKeys.END_HOUR] = settings.timeRange.endHour
            preferences[PreferencesKeys.END_MINUTE] = settings.timeRange.endMinute
            preferences[PreferencesKeys.INTERVAL_MINUTES] = settings.interval.minutes
            preferences[PreferencesKeys.CONTENT_TYPE] = when (settings.contentType) {
                NotificationContentType.WORD_EXAMPLE -> "WORD_EXAMPLE"
                NotificationContentType.PHRASE_OF_DAY -> "PHRASE_OF_DAY"
                NotificationContentType.MINI_QUIZ -> "MINI_QUIZ"
                else -> "WORD_TRANSLATION"
            }
            preferences[PreferencesKeys.DAYS_OF_WEEK] = serializeDaysOfWeek(settings.daysOfWeek)
        }
    }

    private fun parseDaysOfWeek(daysString: String?): Set<DayOfWeek> {
        if (daysString.isNullOrEmpty()) return DayOfWeek.entries.toSet()

        val days = mutableSetOf<DayOfWeek>()
        val daysMap = mapOf(
            "MONDAY" to DayOfWeek.MONDAY,
            "TUESDAY" to DayOfWeek.TUESDAY,
            "WEDNESDAY" to DayOfWeek.WEDNESDAY,
            "THURSDAY" to DayOfWeek.THURSDAY,
            "FRIDAY" to DayOfWeek.FRIDAY,
            "SATURDAY" to DayOfWeek.SATURDAY,
            "SUNDAY" to DayOfWeek.SUNDAY
        )

        daysString.split(",").forEach { dayName ->
            daysMap[dayName.trim()]?.let { days.add(it) }
        }

        return if (days.isEmpty()) DayOfWeek.entries.toSet() else days
    }

    private fun serializeDaysOfWeek(days: Set<DayOfWeek>): String {
        return days.joinToString(",") { it.name }
    }
}

private object PreferencesKeys {
    val IS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val START_HOUR = intPreferencesKey("notifications_start_hour")
    val START_MINUTE = intPreferencesKey("notifications_start_minute")
    val END_HOUR = intPreferencesKey("notifications_end_hour")
    val END_MINUTE = intPreferencesKey("notifications_end_minute")
    val INTERVAL_MINUTES = intPreferencesKey("notifications_interval_minutes")
    val CONTENT_TYPE = stringPreferencesKey("notifications_content_type")
    val DAYS_OF_WEEK = stringPreferencesKey("notifications_days_of_week")
}