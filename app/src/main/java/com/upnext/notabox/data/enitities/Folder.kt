package com.upnext.notabox.data.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val folderName: String,
    val isPinned: Boolean
)
