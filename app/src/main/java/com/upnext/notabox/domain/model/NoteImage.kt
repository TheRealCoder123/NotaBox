package com.upnext.notabox.domain.model

data class NoteImage(
    val imageID: Int,
    val imageUri: String,
    val comment: String,
    val size: NoteImageSize
)
