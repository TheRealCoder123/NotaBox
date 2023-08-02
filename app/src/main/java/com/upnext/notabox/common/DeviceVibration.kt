package com.upnext.notabox.common

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator


object DeviceVibration {
    fun vibrateDevice(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val duration = 100L // Vibration duration in milliseconds.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(duration)
        }
    }
}