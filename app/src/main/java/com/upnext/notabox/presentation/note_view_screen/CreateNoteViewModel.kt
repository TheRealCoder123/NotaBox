package com.upnext.notabox.presentation.note_view_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.notabox.common.Constants
import com.upnext.notabox.common.IdentifierGenerator
import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.domain.enums.CurrentNoteOptionShown
import com.upnext.notabox.domain.enums.NoteTextAlignment
import com.upnext.notabox.domain.enums.NoteTextFontSize
import com.upnext.notabox.domain.model.NoteCheckBox
import com.upnext.notabox.domain.model.NoteData
import com.upnext.notabox.domain.model.TextNoteData
import com.upnext.notabox.domain.repository.NoteRepository
import com.upnext.notabox.domain.enums.NoteDataType
import com.upnext.notabox.domain.model.NoteImage
import com.upnext.notabox.domain.model.NoteImageSize
import com.upnext.notabox.presentation.note_view_screen.events.CreateNoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val _state = mutableStateOf(CreateNoteState())
    val state : State<CreateNoteState> = _state

    private val MOVE_UP = -1
    private val MOVE_DOWN = 1

    private val noteDataList = mutableListOf<NoteData>()

    val titleTextState = mutableStateOf("")
    val textNoteDataState = mutableStateOf("")
    val noteOptions = mutableStateOf(CurrentNoteOptionShown.Default)
    val currentlyEditingNoteData = mutableStateOf<NoteData?>(null)

    val currentlyEditingText = mutableStateOf<TextNoteData?>(null)
    val isRecordingAudio = mutableStateOf(false)
    val isPriorityDialogOpen = mutableStateOf(false)
    var isSelectFolderDialogVisible by mutableStateOf(false)

    val currentlyPlayingAudioFile = mutableStateOf<String?>("")

    fun createNoteIfNoteCreated(
        noteId: String?,
        folderId: Int
    ) {
        if (noteId != null && noteId != Constants.CREATE_NOTE_PARAM_PASSED_TO_CREATE_NOTE_SCREEN){
            onEvent(CreateNoteEvent.GetNoteEvent(noteId))
            Log.e("note creating", "note alr exists")
        }else{
            val note = Note(
                IdentifierGenerator.generateUUID(),
                titleTextState.value,
                System.currentTimeMillis(),
                null,
                folderId ?: -1,
                listOf(
                    NoteData(
                        1,
                        NoteDataType.Text,
                        text = TextNoteData(
                            IdentifierGenerator.generateNumberId(),
                            Color.Gray.toArgb(),
                            "Start Typing",
                            NoteTextFontSize.Normal,
                            NoteTextAlignment.Left
                        )
                    )
                )
            )
            onEvent(CreateNoteEvent.CreateNote(note))
        }
    }

    fun onEvent(event: CreateNoteEvent) {
        when(event){
            is CreateNoteEvent.CreateNote -> {
                viewModelScope.launch {
                    noteRepository.insertNote(event.note)
                    onEvent(CreateNoteEvent.GetNoteEvent(event.note.id))
                }
            }
            is CreateNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteRepository.deleteNote(event.noteId)
                }
            }
            is CreateNoteEvent.PrintNote -> {

            }
            is CreateNoteEvent.GetNoteEvent -> {
                val noteFlow = noteRepository.getNoteById(event.noteId)
                viewModelScope.launch {
                    noteFlow.collectLatest { note ->
                        if (note != null){
                            titleTextState.value = note.title
                            _state.value = CreateNoteState(newNote = note)
                            noteDataList.clear()
                            noteDataList.addAll(note.noteData.distinct())
                        }
                    }
                }
            }

            is CreateNoteEvent.AddACheckList -> {
                val noteData = NoteData(
                    noteDataList.size+1,
                    NoteDataType.CheckBoxList,
                    checkBoxList = NoteCheckBox("Empty Check Box", false, IdentifierGenerator.generateNumberId())
                )
                noteDataList.add(noteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }
            is CreateNoteEvent.AddAudioFile -> {
                val noteData = NoteData(
                    noteDataList.size+1,
                    NoteDataType.Audio,
                    audioUri = event.audioFileUri
                )
                noteDataList.add(noteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }
            is CreateNoteEvent.AddImage -> {
                val noteData = NoteData(
                    noteDataList.size+1,
                    NoteDataType.Image,
                    NoteImage(
                        IdentifierGenerator.generateNumberId(),
                        event.imageUri,
                        "",
                        NoteImageSize.MATCH_SCREEN
                    )
                )
                noteDataList.add(noteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }
            is CreateNoteEvent.AddText -> {
                val textNoteData = TextNoteData(
                    IdentifierGenerator.generateNumberId(),
                    Color.Gray.toArgb(),
                    "Start Typing",
                    NoteTextFontSize.Normal,
                    NoteTextAlignment.Left
                )
                val noteData = NoteData(
                    noteDataList.size+1,
                    NoteDataType.Text,
                    text = textNoteData
                )
                noteDataList.add(noteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }
            is CreateNoteEvent.AttachAFile -> {
                val noteData = NoteData(
                    noteDataList.size+1,
                    NoteDataType.File,
                    file = event.file
                )
                noteDataList.add(noteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }
            is CreateNoteEvent.UpdateNoteTitle -> {
                viewModelScope.launch {
                    noteRepository.updateNoteTitle(event.title, _state.value.newNote?.id!!)
                }
            }

            is CreateNoteEvent.DeleteNoteText -> {
                val textNoteData = noteDataList.find { it.text == event.data }
                noteDataList.remove(textNoteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }
            is CreateNoteEvent.UpdateNoteText -> {
                val textNoteData = noteDataList.find { it.text?.textId == event.data.textId }
                noteDataList.remove(textNoteData)
                val newTextNoteData = NoteData(
                    textNoteData?.order ?: 0,
                    textNoteData?.type ?: NoteDataType.Text,
                    text = event.data
                )
                noteDataList.add(newTextNoteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
                currentlyEditingText.value = event.data
            }

            is CreateNoteEvent.UpdateCheckBox -> {
                val oldCheckBoxData = noteDataList.find { it.checkBoxList?.checkId == event.data.checkId }
                noteDataList.remove(oldCheckBoxData)
                val newCheckBoxNoteData = NoteData(
                    oldCheckBoxData?.order ?: 0,
                    oldCheckBoxData?.type ?: NoteDataType.CheckBoxList,
                    checkBoxList = event.data
                )
                noteDataList.add(newCheckBoxNoteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }

            is CreateNoteEvent.DeleteNoteData -> {
                noteDataList.remove(event.noteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }

            is CreateNoteEvent.AddNoteToFolder -> {
                viewModelScope.launch {
                    val note = _state.value.newNote
                    noteRepository.addNoteToFolder(event.folderId, note?.id ?: "")
                }
            }

            is CreateNoteEvent.UpdateNoteImageSize -> {
                val oldImageData = noteDataList.find { it.image?.imageID == event.image.imageID}
                noteDataList.remove(oldImageData)
                val newCheckBoxNoteData = NoteData(
                    oldImageData?.order ?: 0,
                    oldImageData?.type ?: NoteDataType.Image,
                    image = event.image
                )
                noteDataList.add(newCheckBoxNoteData)
                viewModelScope.launch {
                    noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                }
            }
            is CreateNoteEvent.NoteDataMoveDown -> {
                if (event.movingNoteData != null){
                    val movingTextIndex = noteDataList.indexOf(event.movingNoteData)
                    if (movingTextIndex < noteDataList.size - 1) { // Check if not at the bottom
                        val downTextDataIndex = movingTextIndex + 1
                        Collections.swap(noteDataList, movingTextIndex, downTextDataIndex)
                        viewModelScope.launch {
                            noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                        }
                    }
                }
            }
            is CreateNoteEvent.NoteDataMoveUp -> {
                if (event.movingNoteData != null){
                    val movingTextIndex = noteDataList.indexOf(event.movingNoteData)
                    if (movingTextIndex > 0) { // Check if not at the top
                        val upTextDataIndex = movingTextIndex - 1
                        Collections.swap(noteDataList, movingTextIndex, upTextDataIndex)
                        viewModelScope.launch {
                            noteRepository.updateNoteData(noteDataList.distinct(), _state.value.newNote?.id!!)
                        }
                    }
                }
            }
        }
    }

    private fun swapNoteData(sourceIndex: Int, targetIndex: Int) {
        if (sourceIndex >= 0 && sourceIndex < noteDataList.size &&
            targetIndex >= 0 && targetIndex < noteDataList.size) {

            val sourceNoteData = noteDataList[sourceIndex]
            noteDataList[sourceIndex] = noteDataList[targetIndex]
            noteDataList[targetIndex] = sourceNoteData
        }
    }

    fun deleteNote() {
        onEvent(
            CreateNoteEvent.DeleteNoteData(
                currentlyEditingNoteData.value!!
            )
        )
        noteOptions.value = CurrentNoteOptionShown.Default
    }

}