package com.upnext.notabox.presentation.folders_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.notabox.common.Constants
import com.upnext.notabox.domain.enums.decodeOnFolderResultEnum
import com.upnext.notabox.domain.repository.FolderRepository
import com.upnext.notabox.presentation.folders_screen.event_and_state.FolderEvent
import com.upnext.notabox.presentation.folders_screen.event_and_state.FolderScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val repo: FolderRepository
) : ViewModel() {


    private val _state = mutableStateOf(FolderScreenState())
    val state : State<FolderScreenState> = _state

    init {
        onEvent(FolderEvent.GetFolders)
    }

    fun onEvent(event: FolderEvent) {
        when(event){
            is FolderEvent.CreateFolderEvent -> {
                viewModelScope.launch {
                    repo.createFolder(event.params)
                }
            }
            FolderEvent.GetFolders -> {
                repo.getAllFolders().onEach {
                    _state.value = FolderScreenState(it)
                }.launchIn(viewModelScope)
            }

            is FolderEvent.SetCurrentlyEditingFolder -> {
                _state.value = _state.value.copy(currentlyEditingFolder = event.folder)
            }

            is FolderEvent.DeleteFolder -> {
                viewModelScope.launch {
                    repo.deleteFolder(event.folderId)
                }
            }
            is FolderEvent.UpdateFolder -> {
                viewModelScope.launch {
                    repo.updateFolder(event.updateFolder)
                }
            }
        }
    }

}