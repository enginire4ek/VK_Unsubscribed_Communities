package com.project.giniatovia.data.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateManager {
    const val UNIX = 1000L

    fun formatDate(date1: Long, date2: Long, format: String = "d MMMM"): String {
        val locale = Locale("ru")
        val sdf = SimpleDateFormat(format, locale)
        return if (Date(date1) < Date(date2)) {
            sdf.format(Date(date2 * UNIX))
        } else {
            sdf.format(Date(date1 * UNIX))
        }
    }
}
