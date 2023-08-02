package com.upnext.notabox.domain.model

import com.upnext.notabox.domain.enums.FileNoteDataType

data class FileNoteData(
    val fileUri: String,
    val mimeType: String,
    val fileName: String,
    val type: FileNoteDataType
)
