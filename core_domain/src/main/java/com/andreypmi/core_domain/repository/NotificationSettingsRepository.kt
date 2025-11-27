package com.andreypmi.core_domain.repository

import com.andreypmi.core_domain.models.settingsModel.NotificationSettings

interface NotificationSettingsRepository {
    suspend fun getSettings(): NotificationSettings
    suspend fun saveSettings(settings: NotificationSettings)
}