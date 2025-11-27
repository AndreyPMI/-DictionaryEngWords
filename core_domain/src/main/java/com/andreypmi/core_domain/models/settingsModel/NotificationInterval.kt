package com.andreypmi.core_domain.models.settingsModel

enum class NotificationInterval(val minutes: Int) {
    HOURS_1(60),
    HOURS_2(120),
    HOURS_5(300);

    companion object {
        fun fromMinutes(minutes: Int): NotificationInterval {
            return values().find { it.minutes == minutes } ?: HOURS_2
        }
    }
}