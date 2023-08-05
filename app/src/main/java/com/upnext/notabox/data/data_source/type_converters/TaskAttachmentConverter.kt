package com.upnext.notabox.data.data_source.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upnext.notabox.domain.model.SubTask
import com.upnext.notabox.domain.model.TaskAttachment

class TaskAttachmentConverter {


    @TypeConverter
    fun fromTaskAttachments(noteData: List<TaskAttachment>): String? {
        val gson = Gson()
        return gson.toJson(noteData)
    }

    @TypeConverter
    fun toTaskAttachments(noteDataString: String?): List<TaskAttachment> {
        val gson = Gson()
        val type = object : TypeToken<List<TaskAttachment>>() {}.type
        return gson.fromJson(noteDataString, type)
    }


}