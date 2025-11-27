package com.andreypmi.workmanager

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import javax.inject.Inject

class WordNotificationWorker @Inject constructor(
    private val context: Context,
    private val params: WorkerParameters,
    private val getNotificationContentUseCase: GetNotificationContentUseCase,
    private val notificationManager: NotificationManagerCompat
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val notificationContent = getNotificationContentUseCase()

            showNotification(notificationContent)

            Result.success()
        } catch (e: NotificationNotAllowedException) {
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(content: NotificationContent) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(content.title)
            .setContentText(content.content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content.content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "word_notifications"
        private const val NOTIFICATION_ID = 1
    }
}