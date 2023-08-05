package com.upnext.notabox.presentation.task_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.upnext.notabox.data.enitities.Task
import com.upnext.notabox.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {


    var selectedTask by mutableStateOf<Task?>(null)

    init {



    }

    fun onEvent(event: TaskScreenEvent) {
        when(event){

            is TaskScreenEvent.CreateTask -> {

            }

            TaskScreenEvent.GetTasks -> {

            }

        }
    }

    private fun getTasks() {

    }


}