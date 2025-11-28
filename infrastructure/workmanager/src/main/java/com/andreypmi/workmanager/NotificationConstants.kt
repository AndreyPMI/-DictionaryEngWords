package com.andreypmi.workmanager

object NotificationConstants {
    const val DEEP_LINK_SCHEME = "myapp"
    const val DEEP_LINK_HOST = "notification"
    const val DEEP_LINK_URI = "$DEEP_LINK_SCHEME://$DEEP_LINK_HOST"

    const val PARAM_WORD_ID = "wordId"
    const val PARAM_NOTIFICATION_TYPE = "type"
}