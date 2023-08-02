package com.upnext.notabox.presentation.notes_screen

import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.presentation.notes_screen.components.filter_component.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    data class GetNotes(val noteOrder: NoteOrder): NotesEvent()
    data class Search(val noteOrder: NoteOrder, val query: String): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
    object GetFolders : NotesEvent()
}