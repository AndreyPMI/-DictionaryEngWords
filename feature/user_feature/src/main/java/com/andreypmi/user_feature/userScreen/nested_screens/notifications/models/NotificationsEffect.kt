package com.andreypmi.user_feature.userScreen.nested_screens.notifications.models

sealed interface NotificationsEffect {
    data object NavigateBack : NotificationsEffect
    data class ShowMessage(val message: String) : NotificationsEffect
    data class ShowError(val error: String) : NotificationsEffect
}