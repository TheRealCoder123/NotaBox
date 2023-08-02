package com.upnext.notabox.domain.repository

import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.domain.model.NoteData
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note)
    fun getNoteById(noteId: String): Flow<Note?>
    suspend fun updateNoteData(noteData: List<NoteData>, noteId: String)
    fun getNotes() : Flow<List<Note>>
    fun getNotesByFolderId(folderId: Int) : Flow<List<Note>>
    suspend fun updateNoteTitle(title: String, noteId: String)
    suspend fun deleteNote(noteId: String)
    suspend fun addNoteToFolder(folderId: Int, noteId: String)
    fun searchNotes(query: String) : Flow<List<Note>>
    fun searchNotesByFolder(query: String, folderId: Int) : Flow<List<Note>>
}