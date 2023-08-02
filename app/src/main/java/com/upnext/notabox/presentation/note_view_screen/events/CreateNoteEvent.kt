package com.upnext.notabox.presentation.note_view_screen.events

import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.domain.model.FileNoteData
import com.upnext.notabox.domain.model.NoteCheckBox
import com.upnext.notabox.domain.model.NoteData
import com.upnext.notabox.domain.model.TextNoteData

sealed class CreateNoteEvent {
    data class CreateNote(val note: Note) : CreateNoteEvent()
    data class DeleteNote(val noteId: String) : CreateNoteEvent()
    data class PrintNote(val note: Note) : CreateNoteEvent()
    data class GetNoteEvent(val noteId: String) : CreateNoteEvent()
    data class AddImage(val imageUri: String) : CreateNoteEvent()
    data class AddNoteToFolder(val folderId: Int) : CreateNoteEvent()
    object AddText : CreateNoteEvent()
    data class AttachAFile(val file: FileNoteData) : CreateNoteEvent()
    object AddACheckList : CreateNoteEvent()
    data class AddAudioFile(val audioFileUri: String) : CreateNoteEvent()
    data class UpdateNoteTitle(val title: String) : CreateNoteEvent()
    data class DeleteNoteText(val data: TextNoteData) : CreateNoteEvent()
    data class UpdateNoteText(val data: TextNoteData) : CreateNoteEvent()
    data class UpdateCheckBox(val data: NoteCheckBox) : CreateNoteEvent()
    data class DeleteNoteData(val noteData: NoteData) : CreateNoteEvent()
}