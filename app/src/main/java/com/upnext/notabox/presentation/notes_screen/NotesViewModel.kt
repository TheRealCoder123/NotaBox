package com.upnext.notabox.presentation.notes_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.model.NoteListItemData.Companion.toNoteListItem
import com.upnext.notabox.domain.model.NotesUseCases
import com.upnext.notabox.domain.repository.FolderRepository
import com.upnext.notabox.domain.repository.NoteRepository
import com.upnext.notabox.domain.use_case.get_notes_by_folder.GetNotesByFolderUseCase
import com.upnext.notabox.domain.use_case.note_use_case.GetNotesUseCase
import com.upnext.notabox.presentation.notes_screen.components.filter_component.NoteOrder
import com.upnext.notabox.presentation.notes_screen.components.filter_component.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val useCases: NotesUseCases,
    private val folderRepository: FolderRepository
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    val searchTextFieldState = mutableStateOf("")
    var selectedFolder by mutableStateOf<Folder?>(null)



    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
        onEvent(NotesEvent.GetFolders)
    }


    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.DeleteNote -> {

            }
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                if (searchTextFieldState.value.isNotEmpty()){
                    searchNotes(searchTextFieldState.value, event.noteOrder)
                }else{
                    getNotes(event.noteOrder)
                }

            }
            NotesEvent.RestoreNote -> {

            }
            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            NotesEvent.GetFolders -> {
                folderRepository.getAllFolders().onEach {
                    _state.value = _state.value.copy(folders = it.sortedByDescending { folder -> folder.isPinned })
                }.launchIn(viewModelScope)
            }
            is NotesEvent.GetNotes -> {
                getNotes(event.noteOrder)
            }

            is NotesEvent.Search -> {
                searchNotes(event.query, event.noteOrder)
            }
        }
    }

    private fun searchNotes(q: String, noteOrder: NoteOrder){
        if (selectedFolder == null){
            useCases.searchNotes(noteOrder, q).onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes.map { it.toNoteListItem() },
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
        }else{
            useCases.searchNotesByFolder(noteOrder, q, selectedFolder?.id ?: 0).onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes.map { it.toNoteListItem() },
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        if (selectedFolder == null){
            useCases.getNotesUseCase(noteOrder)
                .onEach { notes ->
                    if (selectedFolder == null){
                        _state.value = state.value.copy(
                            notes = notes.map { it.toNoteListItem() },
                            noteOrder = noteOrder
                        )
                    }
                    Log.e("notes", "$notes")
                    Log.e("notes order", "$noteOrder")
                }.launchIn(viewModelScope)
        }else{
            useCases.getNotesByFolderUseCase(noteOrder, selectedFolder?.id ?: 0).onEach { notes ->
                _state.value = _state.value.copy(
                    notes = notes.map { it.toNoteListItem() },
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
        }
    }

}