package com.andreypmi.user_feature.userScreen.nested_screens.notifications.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.core_domain.usecase.settings.GetSettingsUseCase
import com.andreypmi.core_domain.usecase.settings.ScheduleNotificationsUseCase
import com.andreypmi.core_domain.models.settingsModel.NotificationContentType
import com.andreypmi.core_domain.models.settingsModel.NotificationInterval
import com.andreypmi.user_feature.userScreen.nested_screens.notifications.models.NotificationsEffect
import com.andreypmi.user_feature.userScreen.nested_screens.notifications.models.NotificationsEvent
import com.andreypmi.user_feature.userScreen.nested_screens.notifications.models.NotificationsState
import com.andreypmi.user_feature.userScreen.nested_screens.notifications.models.TimePickerType
import com.andreypmi.core_domain.models.settingsModel.TimeRange
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class NotificationsViewModel (
    private val getSettingsUseCase: GetSettingsUseCase,
    private val scheduleNotificationsUseCase: ScheduleNotificationsUseCase,
  //  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationsState())
    val state: StateFlow<NotificationsState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<NotificationsEffect>()
    val effects: SharedFlow<NotificationsEffect> = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            // TODO: Load initial settings
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.LoadSettings -> loadSettings()
            is NotificationsEvent.SetEnabled -> setEnabled(event.enabled)
            is NotificationsEvent.SetInterval -> setInterval(event.interval)
            is NotificationsEvent.SetContentType -> setContentType(event.contentType)
            is NotificationsEvent.SetDaysOfWeek -> setDaysOfWeek(event.days)
            is NotificationsEvent.SetTimeRange -> setTimeRange(event.timeRange)
            is NotificationsEvent.ShowStartTimePicker -> showStartTimePicker()
            is NotificationsEvent.ShowEndTimePicker -> showEndTimePicker()
            is NotificationsEvent.HideTimePicker -> hideTimePicker()
            is NotificationsEvent.SaveSettings -> saveSettings()
            is NotificationsEvent.ClearError -> clearError()
            NotificationsEvent.NavigateBack -> TODO()
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                // TODO: Implement getSettingsUseCase
                val settings = getSettingsUseCase.execute()
                _state.update { it.copy(settings = settings, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Ошибка загрузки настроек") }
            }
        }
    }

    private fun setEnabled(enabled: Boolean) {
        _state.update { it.copy(settings = it.settings.copy(isEnabled = enabled)) }
        if (!enabled) {
            saveSettings()
        }
    }

    private fun setInterval(interval: NotificationInterval) {
        _state.update { it.copy(settings = it.settings.copy(interval = interval)) }
        saveSettings()
    }

    private fun setContentType(contentType: NotificationContentType) {
        _state.update { it.copy(settings = it.settings.copy(contentType = contentType)) }
        saveSettings()
    }

    private fun setDaysOfWeek(days: Set<DayOfWeek>) {
        _state.update { it.copy(settings = it.settings.copy(daysOfWeek = days)) }
        saveSettings()
    }

    private fun setTimeRange(timeRange: TimeRange) {
        _state.update { it.copy(settings = it.settings.copy(timeRange = timeRange)) }
        saveSettings()
    }

    private fun showStartTimePicker() {
        _state.update { it.copy(showTimePicker = TimePickerType.StartTime) }
    }

    private fun showEndTimePicker() {
        _state.update { it.copy(showTimePicker = TimePickerType.EndTime) }
    }

    private fun hideTimePicker() {
        _state.update { it.copy(showTimePicker = null) }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            try {
                // TODO: Implement scheduleNotificationsUseCase
                scheduleNotificationsUseCase.execute(_state.value.settings)
                _effects.emit(NotificationsEffect.ShowMessage("Настройки сохранены"))
            } catch (e: Exception) {
                _effects.emit(NotificationsEffect.ShowError("Ошибка сохранения настроек"))
            }
        }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }
}