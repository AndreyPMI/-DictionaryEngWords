package com.andreypmi.core_domain.repository

import com.andreypmi.core_domain.models.settingsModel.NotificationSettings

interface NotificationScheduleRepository {
    suspend fun scheduleNotifications(settings: NotificationSettings)
    suspend fun cancelNotifications()
}