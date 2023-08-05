package com.upnext.notabox.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upnext.notabox.data.enitities.Priority
import com.upnext.notabox.data.enitities.Task
import com.upnext.notabox.domain.model.SubTask
import com.upnext.notabox.domain.model.TaskAttachment
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun createTask(task: Task)
    suspend fun updateTaskNote(note: String, taskId: Int)
    suspend fun setTaskPriority(priority: Priority, taskId: Int)
    suspend fun setTaskHasReminder(taskId: Int)
    suspend fun updateSubTasks(subTasks: List<SubTask>, taskId: Int)
    suspend fun updateAttachments(attachments: List<TaskAttachment>, taskId: Int)
    suspend fun markAsDoneMainTask(taskId: Int)
    fun getTaskById(taskId: Int) : Flow<Task>
    fun getAllTasks() : Flow<List<Task>>
}