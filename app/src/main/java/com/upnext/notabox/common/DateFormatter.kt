package com.upnext.notabox.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    fun formatDateFromMillis(millis: Long, format: String): String {
        val date = Date(millis)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }
}