package com.upnext.notabox.data.repository

import com.upnext.notabox.data.data_source.DataAccessObject
import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.domain.model.NoteData
import com.upnext.notabox.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class INoteRepository(
    private val noteDao: DataAccessObject
) : NoteRepository {

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override fun getNoteById(noteId: String): Flow<Note?> {
        return noteDao.getNoteById(noteId)
    }

    override suspend fun updateNoteData(noteData: List<NoteData>, noteId: String) {
        noteDao.updateNoteData(noteData, noteId)
    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override fun getNotesByFolderId(folderId: Int): Flow<List<Note>> {
        return noteDao.getNotesByFolder(folderId)
    }

    override suspend fun updateNoteTitle(title: String, noteId: String) {
        return noteDao.updateNoteTitle(title, noteId)
    }

    override suspend fun deleteNote(noteId: String) {
        noteDao.deleteNote(noteId)
    }

    override suspend fun addNoteToFolder(folderId: Int, noteId: String) {
        noteDao.addNoteToFolder(folderId, noteId)
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes(query)
    }

    override fun searchNotesByFolder(query: String, folderId: Int): Flow<List<Note>> {
        return noteDao.searchNotesByFolder(query, folderId)
    }

}