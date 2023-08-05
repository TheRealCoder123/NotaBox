package com.upnext.notabox.presentation.task_screen

import com.upnext.notabox.data.enitities.Task

sealed class TaskScreenEvent {
    data class CreateTask(val task: Task) : TaskScreenEvent()
    object GetTasks : TaskScreenEvent()
}