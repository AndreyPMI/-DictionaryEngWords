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
import org.json.JSONObject
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
            contentType = NotificationContentType.fromName(
                preferences[PreferencesKeys.CONTENT_TYPE] ?: "WORD_TRANSLATION"
            ),
            daysOfWeek = parseDaysOfWeek(preferences[PreferencesKeys.DAYS_OF_WEEK]),
            selectedCategoryId = preferences[PreferencesKeys.SELECTED_CATEGORY_ID],
            lastShownWordIndex = parseLastShownIndex(preferences[PreferencesKeys.LAST_SHOWN_WORD_INDEX])
        )
    }

    override suspend fun saveSettings(settings: NotificationSettings) {
        if (settings.selectedCategoryId == null) return
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_ENABLED] = settings.isEnabled
            preferences[PreferencesKeys.START_HOUR] = settings.timeRange.startHour
            preferences[PreferencesKeys.START_MINUTE] = settings.timeRange.startMinute
            preferences[PreferencesKeys.END_HOUR] = settings.timeRange.endHour
            preferences[PreferencesKeys.END_MINUTE] = settings.timeRange.endMinute
            preferences[PreferencesKeys.INTERVAL_MINUTES] = settings.interval.minutes
            preferences[PreferencesKeys.CONTENT_TYPE] = settings.contentType.name
            preferences[PreferencesKeys.DAYS_OF_WEEK] = serializeDaysOfWeek(settings.daysOfWeek)
            preferences[PreferencesKeys.SELECTED_CATEGORY_ID] = settings.selectedCategoryId!!
            preferences[PreferencesKeys.LAST_SHOWN_WORD_INDEX] =
                serializeLastShownIndex(settings.lastShownWordIndex)
        }
    }

    override suspend fun getLastShownWordIndex(categoryId: String?): Int? {
        val key = categoryId ?: "all"
        val indexMap =
            parseLastShownIndex(dataStore.data.first()[PreferencesKeys.LAST_SHOWN_WORD_INDEX])
        return indexMap[key]
    }

    override suspend fun saveLastShownWordIndex(categoryId: String?, index: Int) {
        val key = categoryId ?: "all"
        dataStore.edit { preferences ->
            val currentJson = preferences[PreferencesKeys.LAST_SHOWN_WORD_INDEX] ?: "{}"
            val indexMap = parseLastShownIndex(currentJson).toMutableMap()
            indexMap[key] = index
            preferences[PreferencesKeys.LAST_SHOWN_WORD_INDEX] = serializeLastShownIndex(indexMap)
        }
    }

    override suspend fun clearAllSettings() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    private fun parseDaysOfWeek(daysString: String?): Set<DayOfWeek> {
        if (daysString.isNullOrEmpty()) return DayOfWeek.values().toSet()

        return try {
            daysString.split(",")
                .mapNotNull { dayName ->
                    try {
                        DayOfWeek.valueOf(dayName.trim())
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                }
                .toSet()
                .takeIf { it.isNotEmpty() }
                ?: DayOfWeek.values().toSet()
        } catch (e: Exception) {
            DayOfWeek.values().toSet()
        }
    }

    private fun serializeDaysOfWeek(days: Set<DayOfWeek>): String {
        return days.joinToString(",") { it.name }
    }

    private fun parseLastShownIndex(jsonString: String?): Map<String, Int> {
        if (jsonString.isNullOrEmpty()) return emptyMap()

        return try {
            val jsonObject = JSONObject(jsonString)
            val result = mutableMapOf<String, Int>()
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                result[key] = jsonObject.getInt(key)
            }
            result
        } catch (e: Exception) {
            emptyMap()
        }
    }

    private fun serializeLastShownIndex(indexMap: Map<String, Int>): String {
        return try {
            val jsonObject = JSONObject()
            indexMap.forEach { (key, value) ->
                jsonObject.put(key, value)
            }
            jsonObject.toString()
        } catch (e: Exception) {
            "{}"
        }
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
    val SELECTED_CATEGORY_ID = stringPreferencesKey("notifications_selected_category_id")
    val LAST_SHOWN_WORD_INDEX = stringPreferencesKey("notifications_last_shown_word_index")
}