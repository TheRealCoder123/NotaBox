package com.upnext.notabox.common

object AudioFormatter {
    fun formatAudioDuration(duration: Long): String {
        val minutes = (duration / 1000 / 60).toInt()
        val seconds = (duration / 1000 % 60).toInt()
        return "%02d:%02d".format(minutes, seconds)
    }
}