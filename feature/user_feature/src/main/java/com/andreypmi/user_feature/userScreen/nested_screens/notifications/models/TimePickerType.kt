package com.andreypmi.user_feature.userScreen.nested_screens.notifications.models

sealed interface TimePickerType {
    data object StartTime : TimePickerType
    data object EndTime : TimePickerType
}