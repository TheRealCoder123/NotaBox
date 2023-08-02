package com.upnext.notabox.data.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "priority"
)
data class Priority(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val color: Int,
    val title: String,
    val description: String,
    val priorityLevel: Int
)
