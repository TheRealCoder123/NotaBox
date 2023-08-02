package com.upnext.notabox.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.upnext.notabox.data.data_source.type_converters.NoteDataConverter
import com.upnext.notabox.data.data_source.type_converters.PriorityTypeConverter
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.data.enitities.Priority

@Database(
    entities = [Note::class, Priority::class, Folder::class],
    version = 7
)
@TypeConverters(
    PriorityTypeConverter::class,
    NoteDataConverter::class
)
abstract class NotaBoxDatabase: RoomDatabase() {

    abstract val noteDao: DataAccessObject

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}