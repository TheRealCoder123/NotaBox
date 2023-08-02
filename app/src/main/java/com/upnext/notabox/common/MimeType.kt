package com.upnext.notabox.common

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object MimeType {
    fun getFileNameFromUri(contentResolver: ContentResolver, fileUri: Uri): String? {
        var fileName: String? = null
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        val cursor = contentResolver.query(fileUri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                fileName = it.getString(columnIndex)
            }
        }
        return fileName
    }

    fun getMimeTypeFromUri(contentResolver: ContentResolver, fileUri: Uri): String? {
        var mimeType: String? = null

        // First, try to get the MIME type from ContentResolver
        mimeType = contentResolver.getType(fileUri)

        // If ContentResolver didn't return the MIME type, try to get it from the file extension
        if (mimeType == null) {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString())
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        }

        return mimeType
    }
}