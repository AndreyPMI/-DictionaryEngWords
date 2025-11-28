package com.andreypmi.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andreypmi.core_domain.models.settingsModel.NotificationContent
import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.settings.GenerateNotificationContentUseCase
import com.andreypmi.core_domain.usecase.settings.GetRandomWordUseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class WordNotificationWorker @Inject constructor(
    private val context: Context,
    private val params: WorkerParameters,
    private val getRandomWordUseCase: GetRandomWordUseCase,
    private val generateNotificationContentUseCase: GenerateNotificationContentUseCase,
    private val wordsRepository: WordRepository,
    private val settingsRepository: NotificationSettingsRepository,
    private val notificationManager: NotificationManagerCompat
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val settings = settingsRepository.getSettings()

            if (!shouldShowNotification(settings)) {
                return Result.success()
            }

            val word = getRandomWordUseCase()

            val notificationContent = generateNotificationContentUseCase(settings, word)

            showNotification(notificationContent)

            Result.success()

        } catch (e: IllegalStateException) {
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun shouldShowNotification(settings: NotificationSettings): Boolean {
        if (!settings.isEnabled) return false

        val currentTime = LocalTime.now()
        val currentDay = LocalDate.now().dayOfWeek

        val inTimeRange = settings.timeRange.contains(currentTime)
        val allowedDay = settings.daysOfWeek.contains(currentDay)

        return inTimeRange && allowedDay
    }

    private fun showNotification(content: NotificationContent) {
        if (!hasNotificationPermission()) {
            return
        }
        createNotificationChannel()

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(getNotificationIcon())
            .setContentTitle(content.title)
            .setContentText(content.content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content.content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(createPendingIntent(content))
            .build()
        try {
            notificationManager.notify(content.notificationId, notification)
        } catch (e: SecurityException) {
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationManager.areNotificationsEnabled()
        } else {
            true
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Уведомления для изучения слов",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления с новыми словами для изучения"
            }

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotificationIcon(): Int {
        return android.R.drawable.ic_dialog_info
    }

    private fun createPendingIntent(content: NotificationContent): PendingIntent {
        val deepLinkUri = Uri.parse(NotificationConstants.DEEP_LINK_URI).buildUpon()
            .appendQueryParameter(NotificationConstants.PARAM_WORD_ID, content.wordId)
            .appendQueryParameter(NotificationConstants.PARAM_NOTIFICATION_TYPE, content.type.name)
            .build()

        val deepLinkIntent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
            `package` = context.packageName
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return PendingIntent.getActivity(
            context,
            content.notificationId,
            deepLinkIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "word_notifications"
    }
}