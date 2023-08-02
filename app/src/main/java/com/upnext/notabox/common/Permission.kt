package com.upnext.notabox.common

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object Permission {

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

}