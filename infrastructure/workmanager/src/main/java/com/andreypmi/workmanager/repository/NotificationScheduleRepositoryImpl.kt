package com.andreypmi.workmanager.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import com.andreypmi.core_domain.repository.NotificationScheduleRepository
import com.andreypmi.workmanager.WordNotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationScheduleRepositoryImpl @Inject constructor(
    private val workManager: WorkManager,
) : NotificationScheduleRepository {

    override suspend fun scheduleNotifications(settings: NotificationSettings) {
        cancelNotifications()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<WordNotificationWorker>(
            repeatInterval = settings.interval.minutes.toLong(),
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setInitialDelay(calculateInitialDelay(settings), TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "word_notifications",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun cancelNotifications() {
        workManager.cancelUniqueWork("word_notifications")
    }

    private fun calculateInitialDelay(settings: NotificationSettings): Long {
        return 0L
    }
}