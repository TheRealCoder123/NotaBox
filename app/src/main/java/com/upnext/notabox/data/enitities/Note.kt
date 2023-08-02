package com.upnext.notabox.data.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.upnext.notabox.domain.model.NoteData
import java.util.UUID

@Entity(tableName = "note")
data class Note(
    @PrimaryKey
    val id: String,
    val title: String,
    val timestamp: Long,
    val priority: Priority?,
    val folderId: Int,
    val noteData: List<NoteData>
)
