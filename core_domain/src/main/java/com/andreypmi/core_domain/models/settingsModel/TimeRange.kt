package com.andreypmi.core_domain.models.settingsModel

import java.time.LocalTime

data class TimeRange(
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int
) {
    companion object {
        val Default = TimeRange(
            startHour = 10,
            startMinute = 0,
            endHour = 22,
            endMinute = 0
        )
    }
    fun contains(time: LocalTime): Boolean {
        if (!startTime.isAfter(endTime)) {
            return !time.isBefore(startTime) && !time.isAfter(endTime)
        }
        else {
            return !time.isBefore(startTime) || !time.isAfter(endTime)
        }
    }
    val startTime: LocalTime get() = LocalTime.of(startHour, startMinute)
    val endTime: LocalTime get() = LocalTime.of(endHour, endMinute)
}