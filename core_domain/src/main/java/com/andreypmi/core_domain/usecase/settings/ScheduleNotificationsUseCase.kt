package com.andreypmi.core_domain.usecase.settings

import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import com.andreypmi.core_domain.repository.NotificationScheduleRepository
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam
import javax.inject.Inject

class ScheduleNotificationsUseCase @Inject constructor(
    private val settingsRepository: NotificationSettingsRepository,
    private val scheduleRepository: NotificationScheduleRepository
) : UseCaseWithParam<Unit, NotificationSettings> {
    override suspend fun execute(params: NotificationSettings) {
        settingsRepository.saveSettings(params)

        if (params.isEnabled) {
            scheduleRepository.scheduleNotifications(params)
        } else {
            scheduleRepository.cancelNotifications()
        }
    }
}