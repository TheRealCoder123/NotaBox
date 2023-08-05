package com.upnext.notabox.di

import android.app.Application
import androidx.room.Room
import com.upnext.notabox.data.data_source.NotaBoxDatabase
import com.upnext.notabox.data.repository.IFolderRepository
import com.upnext.notabox.data.repository.INoteRepository
import com.upnext.notabox.data.repository.ITaskRepository
import com.upnext.notabox.domain.model.NotesUseCases
import com.upnext.notabox.domain.repository.FolderRepository
import com.upnext.notabox.domain.repository.NoteRepository
import com.upnext.notabox.domain.repository.TaskRepository
import com.upnext.notabox.domain.use_case.SearchNotes.SearchNotes
import com.upnext.notabox.domain.use_case.SearchNotes.SearchNotesByFolder
import com.upnext.notabox.domain.use_case.get_notes_by_folder.GetNotesByFolderUseCase
import com.upnext.notabox.domain.use_case.note_use_case.GetNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NotaBoxDatabase {
        return Room.databaseBuilder(
            app,
            NotaBoxDatabase::class.java,
            NotaBoxDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NotaBoxDatabase) : NoteRepository {
        return INoteRepository(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideFolderRepository(db: NotaBoxDatabase) : FolderRepository {
        return IFolderRepository(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: NotaBoxDatabase) : TaskRepository {
        return ITaskRepository(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository) = NotesUseCases(
        GetNotesUseCase(noteRepository),
        GetNotesByFolderUseCase(noteRepository),
        SearchNotes(noteRepository),
        SearchNotesByFolder(noteRepository)
    )



}