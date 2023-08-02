package com.upnext.notabox.presentation.note_view_screen

import com.upnext.notabox.data.enitities.Note


data class CreateNoteState(
    val newNote: Note? = null,
    val noteDeleted: Boolean = false
)
