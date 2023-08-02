package com.upnext.notabox.domain.model

import androidx.compose.ui.unit.Dp
import com.upnext.notabox.domain.enums.NoteTextAlignment
import com.upnext.notabox.domain.enums.NoteTextFontSize

data class TextNoteData(
    val textId: Int,
    val textColor: Int,
    val text: String,
    val size: NoteTextFontSize,
    val alignment: NoteTextAlignment
)
