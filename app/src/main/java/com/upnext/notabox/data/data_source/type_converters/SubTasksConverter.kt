package com.upnext.notabox.data.data_source.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upnext.notabox.domain.model.NoteData
import com.upnext.notabox.domain.model.SubTask

class SubTasksConverter {

    @TypeConverter
    fun fromSubTasks(noteData: List<SubTask>): String? {
        val gson = Gson()
        return gson.toJson(noteData)
    }

    @TypeConverter
    fun toSubTasks(noteDataString: String?): List<SubTask> {
        val gson = Gson()
        val type = object : TypeToken<List<SubTask>>() {}.type
        return gson.fromJson(noteDataString, type)
    }

}