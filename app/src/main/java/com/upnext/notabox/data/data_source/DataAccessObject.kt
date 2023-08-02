package com.upnext.notabox.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.domain.model.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("Select * from note where id=:noteId")
    fun getNoteById(noteId: String) : Flow<Note>

    @Query("Update note set noteData=:noteData where id=:noteId")
    suspend fun updateNoteData(noteData: List<NoteData>, noteId: String)

    @Query("Update note set title=:title where id=:noteId")
    suspend fun updateNoteTitle(title: String, noteId: String)

    @Query("Update note set folderId=:folderId where id=:noteId")
    suspend fun addNoteToFolder(folderId: Int, noteId: String)

    @Query("Select * from note")
    fun getNotes() : Flow<List<Note>>

    @Query("Select * from note where folderId=:folderId")
    fun getNotesByFolder(folderId: Int) : Flow<List<Note>>

    @Query("delete from note where id=:noteId")
    suspend fun deleteNote(noteId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: Folder)

    @Query("Select * from folder")
    fun getFolders() : Flow<List<Folder>>

    @Query("Update folder set folderName=:name, isPinned=:pinned where id=:folderId")
    suspend fun updateFolder(name: String, pinned: Boolean, folderId: Int)

    @Query("delete from folder where id=:folderId")
    suspend fun deleteFolder(folderId: Int)

    @Query("SELECT * FROM note WHERE title LIKE '%' || :q || '%'")
    fun searchNotes(q: String): Flow<List<Note>>


    @Query("SELECT * FROM note WHERE title LIKE '%' || :q || '%' AND folderId = :folderId")
    fun searchNotesByFolder(q: String, folderId: Int): Flow<List<Note>>



}