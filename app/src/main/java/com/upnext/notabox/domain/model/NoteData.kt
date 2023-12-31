package com.upnext.notabox.domain.model

import com.upnext.notabox.domain.enums.NoteDataType

data class NoteData(
    val order: Int,
    val type: NoteDataType,
    val image: NoteImage? = null,
    val text: TextNoteData? = null,
    val file: FileNoteData? = null,
    val audioUri: String? = null,
    val checkBoxList: NoteCheckBox? = null,
)
