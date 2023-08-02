package com.upnext.notabox.data.data_source.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upnext.notabox.domain.model.NoteData

class NoteDataConverter {
    @TypeConverter
    fun fromNoteData(noteData: List<NoteData>): String? {
        val gson = Gson()
        return gson.toJson(noteData)
    }

    @TypeConverter
    fun toNoteData(noteDataString: String?): List<NoteData> {
        val gson = Gson()
        val type = object : TypeToken<List<NoteData>>() {}.type
        return gson.fromJson(noteDataString, type)
    }
}