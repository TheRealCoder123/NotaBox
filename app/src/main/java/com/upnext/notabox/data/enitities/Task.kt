package com.upnext.notabox.data.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.upnext.notabox.domain.model.SubTask
import com.upnext.notabox.domain.model.TaskAttachment

@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val taskId: Int,
    val taskTitle: String,
    val done: Boolean,
    val order: String,
    val priority: Priority,
    val hasReminder: Boolean,
    val note: String,
    val subTasks: List<SubTask>,
    val attachments: List<TaskAttachment>,
    val timeStamp: Long
        )