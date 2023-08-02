package com.upnext.notabox.domain.model

import com.upnext.notabox.domain.use_case.SearchNotes.SearchNotes
import com.upnext.notabox.domain.use_case.SearchNotes.SearchNotesByFolder
import com.upnext.notabox.domain.use_case.get_notes_by_folder.GetNotesByFolderUseCase
import com.upnext.notabox.domain.use_case.note_use_case.GetNotesUseCase

data class NotesUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val getNotesByFolderUseCase: GetNotesByFolderUseCase,
    val searchNotes: SearchNotes,
    val searchNotesByFolder: SearchNotesByFolder
)