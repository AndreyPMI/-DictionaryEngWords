package com.andreypmi.user_feature.di

import com.andreypmi.core_domain.repository.NotificationScheduleRepository
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.repository.ShareStorageRepository
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.service.QrCodeService
import com.andreypmi.logger.Logger

interface UserDeps {
    val wordRepository: WordRepository
    val shareStorageRepository: ShareStorageRepository
    val notificationSettingsRepository : NotificationSettingsRepository
    val notificationScheduleRepository : NotificationScheduleRepository
    val qrCodeService: QrCodeService
    val logger : Logger
}