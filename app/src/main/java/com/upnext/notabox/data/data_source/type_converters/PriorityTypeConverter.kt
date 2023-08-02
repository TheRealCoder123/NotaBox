package com.upnext.notabox.data.data_source.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.upnext.notabox.data.enitities.Priority

class PriorityTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPriority(priority: Priority?): String? {
        return gson.toJson(priority)
    }

    @TypeConverter
    fun toPriority(priorityString: String?): Priority? {
        return gson.fromJson(priorityString, Priority::class.java)
    }
}