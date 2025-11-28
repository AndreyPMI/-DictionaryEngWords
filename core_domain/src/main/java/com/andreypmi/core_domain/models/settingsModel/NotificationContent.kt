package com.andreypmi.core_domain.models.settingsModel

data class NotificationContent(
    val title: String,
    val content: String,
    val type: NotificationContentType,
    val wordId: String? = null,
    val actionType: NotificationActionType = NotificationActionType.SHOW_WORD
) {
    val notificationId: Int
        get() = (wordId?.hashCode() ?: 0) + type.ordinal + NOTIFICATION_BASE_ID

    companion object {
        private const val NOTIFICATION_BASE_ID = 1000
    }
}