package com.andreypmi.user_feature.userScreen.nested_screens.notifications.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreypmi.core_domain.usecase.settings.GetSettingsUseCase
import com.andreypmi.core_domain.usecase.settings.ScheduleNotificationsUseCase
import javax.inject.Inject

class NotificationsViewModelFactory @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val scheduleNotificationsUseCase: ScheduleNotificationsUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            return NotificationsViewModel(
                getSettingsUseCase,
                scheduleNotificationsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
