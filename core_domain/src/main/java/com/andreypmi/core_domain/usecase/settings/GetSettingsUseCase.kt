package com.andreypmi.core_domain.usecase.settings

import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.usecase.UseCaseWithoutParam
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: NotificationSettingsRepository
) : UseCaseWithoutParam<NotificationSettings> {
    override suspend fun execute(): NotificationSettings {
        return settingsRepository.getSettings()
    }
}
