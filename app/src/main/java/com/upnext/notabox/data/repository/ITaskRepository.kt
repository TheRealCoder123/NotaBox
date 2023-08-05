package com.upnext.notabox.data.repository

import com.upnext.notabox.data.data_source.DataAccessObject
import com.upnext.notabox.data.enitities.Priority
import com.upnext.notabox.data.enitities.Task
import com.upnext.notabox.domain.model.SubTask
import com.upnext.notabox.domain.model.TaskAttachment
import com.upnext.notabox.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ITaskRepository(private val dao: DataAccessObject) : TaskRepository {
    override suspend fun createTask(task: Task) {
        dao.createTask(task)
    }

    override suspend fun updateTaskNote(note: String, taskId: Int) {
        dao.updateTaskNote(note, taskId)
    }

    override suspend fun setTaskPriority(priority: Priority, taskId: Int) {
        dao.setTaskPriority(priority, taskId)
    }

    override suspend fun setTaskHasReminder(taskId: Int) {
        dao.setTaskHasReminder(true, taskId)
    }

    override suspend fun updateSubTasks(subTasks: List<SubTask>, taskId: Int) {
        dao.updateSubTasks(subTasks, taskId)
    }

    override suspend fun updateAttachments(attachments: List<TaskAttachment>, taskId: Int) {
        dao.updateAttachments(attachments, taskId)
    }

    override suspend fun markAsDoneMainTask(taskId: Int) {
        dao.markAsDoneMainTask(taskId, true)
    }

    override fun getTaskById(taskId: Int): Flow<Task> {
        return dao.getTaskById(taskId)
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return dao.getAllTasks()
    }
}