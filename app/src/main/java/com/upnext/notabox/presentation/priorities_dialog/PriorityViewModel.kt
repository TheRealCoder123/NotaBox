package com.upnext.notabox.presentation.priorities_dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.notabox.domain.repository.PriorityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PriorityViewModel @Inject constructor(
    private val priorityRepository: PriorityRepository
) : ViewModel() {

    private val _state = mutableStateOf(PrioritiesDialogState())
    val state : State<PrioritiesDialogState> = _state

    init {
        getAllPriorities()
    }

    fun onEvent(event: OnPriorityEvent){
        when(event){
            is OnPriorityEvent.CreatePriority -> {

            }
            is OnPriorityEvent.GetNoteFromId -> {

            }
        }
    }

    private fun getAllPriorities() {
        priorityRepository.getPriorities().onEach {
            _state.value = PrioritiesDialogState(priorities = it)
        }.launchIn(viewModelScope)
    }


}