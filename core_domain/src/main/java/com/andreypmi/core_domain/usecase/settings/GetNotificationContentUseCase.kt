package com.andreypmi.core_domain.usecase.settings

import com.andreypmi.core_domain.models.settingsModel.NotificationContentType
import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.repository.WordRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class GetNotificationContentUseCase @Inject constructor(
    private val settingsRepository: NotificationSettingsRepository,
    private val wordsRepository: WordRepository
) {
    suspend operator fun invoke(): NotificationContent {
        val settings = settingsRepository.getSettings()

        if (!shouldShowNotification(settings)) {
            throw NotificationNotAllowedException()
        }

        return generateNotificationContent(settings)
    }

    private fun shouldShowNotification(settings: NotificationSettings): Boolean {
        val currentTime = LocalTime.now()
        val currentDay = LocalDate.now().dayOfWeek

        val inTimeRange = !currentTime.isBefore(settings.timeRange.startTime) &&
                !currentTime.isAfter(settings.timeRange.endTime)

        val allowedDay = settings.daysOfWeek.contains(currentDay)

        return inTimeRange && allowedDay
    }

    private suspend fun generateNotificationContent(settings: NotificationSettings): NotificationContent {
        val word = wordsRepository.getWordsByCategoryId()

        return when (settings.contentType) {
            NotificationContentType.WORD_TRANSLATION -> NotificationContent(
                title = word.original,
                content = word.translation,
                type = settings.contentType
            )
            NotificationContentType.WORD_EXAMPLE -> NotificationContent(
                title = word.original,
                content = word.example ?: word.translation,
                type = settings.contentType
            )
            NotificationContentType.PHRASE_OF_DAY -> NotificationContent(
                title = "Фраза дня",
                content = word.phrase ?: "${word.original} - ${word.translation}",
                type = settings.contentType
            )
            NotificationContentType.MINI_QUIZ -> NotificationContent(
                title = "Мини-тест",
                content = "Выбери перевод: ${word.original}",
                type = settings.contentType
            )
        }
    }

}