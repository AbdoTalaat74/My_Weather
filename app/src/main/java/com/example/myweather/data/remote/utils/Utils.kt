package com.example.myweather.data.remote.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import java.util.Locale

object Utils {
    fun getWeekDayName(date: String): String {
        val localDate = LocalDate.Companion.parse(date)
        val dayOfWeek = localDate.dayOfWeek
        return dayOfWeek.name.lowercase(Locale.ROOT).replaceFirstChar { it.uppercaseChar() }
    }

    fun convertToTime(dateTimeString: String): String {
        val dateTime = LocalDateTime.parse(dateTimeString)
        return "%02d:%02d".format(dateTime.hour, dateTime.minute)
    }
}