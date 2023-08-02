package com.upnext.notabox.presentation.note_view_screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.upnext.notabox.common.DateFormatter
import com.upnext.notabox.common.DeviceVibration
import com.upnext.notabox.common.MimeType
import com.upnext.notabox.common.Permission
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.audio_player.IAudioPlayer
import com.upnext.notabox.domain.enums.CurrentNoteOptionShown
import com.upnext.notabox.domain.enums.FileNoteDataType
import com.upnext.notabox.domain.enums.Keyboard
import com.upnext.notabox.domain.enums.NoteTextAlignment
import com.upnext.notabox.domain.enums.NoteTextFontSize
import com.upnext.notabox.domain.enums.OnFolderClickSendResultTO
import com.upnext.notabox.domain.model.FileNoteData
import com.upnext.notabox.domain.model.NoteCheckBox
import com.upnext.notabox.domain.model.TextNoteData
import com.upnext.notabox.domain.util.NoteDataType
import com.upnext.notabox.presentation.global_components.keyboardAsState
import com.upnext.notabox.presentation.navigation.MainNavigationRoutes
import com.upnext.notabox.presentation.note_view_screen.components.AudioRecordingDialog
import com.upnext.notabox.presentation.note_view_screen.components.BottomMenu
import com.upnext.notabox.presentation.note_view_screen.components.DoneFloatingActionButton
import com.upnext.notabox.presentation.note_view_screen.components.EditFileBottomMenu
import com.upnext.notabox.presentation.note_view_screen.components.EditImageBottomMenu
import com.upnext.notabox.presentation.note_view_screen.components.MoreCreateNoteMenu
import com.upnext.notabox.presentation.note_view_screen.components.NoteAudioPlayer
import com.upnext.notabox.presentation.note_view_screen.components.NoteCheckBoxItem
import com.upnext.notabox.presentation.note_view_screen.components.NoteFileItem
import com.upnext.notabox.presentation.note_view_screen.components.NoteTextFloatingMenu
import com.upnext.notabox.presentation.note_view_screen.components.SelectFolderDialog
import com.upnext.notabox.presentation.note_view_screen.events.CreateNoteEvent
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun CreateNoteScreen(
    createNoteViewModel: CreateNoteViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    val state = createNoteViewModel.state.value

    val isFloatingNoteTextMenuVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val isKeyboardOpen by keyboardAsState()

    val player by lazy {
        IAudioPlayer(context)
    }



    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted){
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Please accept the permission", Toast.LENGTH_SHORT).show()
        }
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                val mimeType = context.contentResolver.getType(uri)
                val fileName = MimeType.getFileNameFromUri(context.contentResolver,uri)
                if (mimeType != null) {
                    when {
                        mimeType.startsWith("image/") -> {
                            createNoteViewModel.onEvent(CreateNoteEvent.AddImage(uri.toString()))
                        }
                        mimeType == "application/pdf" -> {
                            val file = FileNoteData(
                                uri.toString(),
                                mimeType,
                                fileName ?: "",
                                FileNoteDataType.PDF
                            )
                            createNoteViewModel.onEvent(CreateNoteEvent.AttachAFile(file))
                        }
                        mimeType.startsWith("application/msword") || mimeType.startsWith("application/vnd.openxmlformats-officedocument.wordprocessingml.document") -> {
                            val file = FileNoteData(
                                uri.toString(),
                                mimeType,
                                fileName ?: "",
                                FileNoteDataType.Word
                            )
                            createNoteViewModel.onEvent(CreateNoteEvent.AttachAFile(file))

                        }
                        mimeType.startsWith("application/vnd.ms-powerpoint") || mimeType.startsWith(
                            "application/vnd.openxmlformats-officedocument.presentationml.presentation"
                        ) -> {
                            val file = FileNoteData(
                                uri.toString(),
                                mimeType,
                                fileName ?: "",
                                FileNoteDataType.PowerPoint
                            )
                            createNoteViewModel.onEvent(CreateNoteEvent.AttachAFile(file))
                        }
                    }
                }
            }
        }
    )

    AudioRecordingDialog(
        isShowingDialog = createNoteViewModel.isRecordingAudio.value,
        onDoneRecording = {
            createNoteViewModel.isRecordingAudio.value = false
            createNoteViewModel.onEvent(CreateNoteEvent.AddAudioFile(it.toUri().toString()))
        }
    )

    SelectFolderDialog(
        isVisible = createNoteViewModel.isSelectFolderDialogVisible,
        onDone = { selectedFolder ->
            if (selectedFolder != null){
                createNoteViewModel.onEvent(CreateNoteEvent.AddNoteToFolder(selectedFolder.id))
                createNoteViewModel.isSelectFolderDialogVisible = false
            }else{
                createNoteViewModel.isSelectFolderDialogVisible = false
            }
        },
        addNoteFolderId = state.newNote?.folderId ?: 0
    )


    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            MoreCreateNoteMenu(
                onDeleteNoteClicked = {
                    createNoteViewModel.onEvent(CreateNoteEvent.DeleteNote(state.newNote?.id ?: ""))
                    navController.navigateUp()
                },
                onAddToFolder = {
                    createNoteViewModel.isSelectFolderDialogVisible = true
                }
            )
        },
        sheetBackgroundColor = NotaBoxTheme.colors.dialogBgColor
    ) {

        Scaffold(
            backgroundColor = NotaBoxTheme.colors.background,
            bottomBar = {
                when(createNoteViewModel.noteOptions.value) {
                    CurrentNoteOptionShown.Default -> {
                        BottomMenu(
                            onAddImage = {
                                launcher.launch(arrayOf("image/*"))
                            },
                            onAddText = {
                                createNoteViewModel.onEvent(CreateNoteEvent.AddText)
                            },
                            onAttachFile = {
                                val mimeTypes = arrayOf(
                                    "application/pdf",           // PDF files
                                    "application/msword",        // Word documents (.doc)
                                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // Word documents (.docx)
                                    "application/vnd.ms-powerpoint",   // PowerPoint presentations (.ppt)
                                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"  // PowerPoint presentations (.pptx)
                                )
                                launcher.launch(mimeTypes)
                            },
                            onAddCheckList = {
                                createNoteViewModel.onEvent(CreateNoteEvent.AddACheckList)
                            },
                            onAddAudioFile = {
                                if (Permission.hasPermission(context, Manifest.permission.RECORD_AUDIO)){
                                    createNoteViewModel.isRecordingAudio.value = true
                                }else{
                                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            },
                            onMore = {
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible)
                                        modalSheetState.hide()
                                    else
                                        modalSheetState.show()
                                }
                            }
                        )
                    }
                    CurrentNoteOptionShown.EDIT_FILE -> {
                        EditFileBottomMenu(
                            onDelete = createNoteViewModel::deleteNote,
                            onReAttach = {

                            },
                            onClose = {
                                createNoteViewModel.noteOptions.value = CurrentNoteOptionShown.Default
                            },
                            onOpen = {
                                val data = createNoteViewModel.currentlyEditingNoteData.value
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.setDataAndType(Uri.parse(data?.file?.fileUri), data?.file?.mimeType)
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                context.startActivity(intent)
                            }
                        )
                    }
                    CurrentNoteOptionShown.EDIT_IMAGE -> {
                        EditImageBottomMenu(
                            onDelete = createNoteViewModel::deleteNote,
                            onAddComment = {  },
                            onClose = {
                                createNoteViewModel.noteOptions.value = CurrentNoteOptionShown.Default
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                DoneFloatingActionButton {
                    createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteTitle(createNoteViewModel.titleTextState.value))
                }
            }
        ) {
            it

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(NotaBoxTheme.spaces.mediumLarge)
            ){

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = NotaBoxTheme.spaces.extraLarge)
                ) {

                    item {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = NotaBoxTheme.spaces.medium),
                                value = createNoteViewModel.titleTextState.value,
                                onValueChange = { createNoteViewModel.titleTextState.value = it },
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = NotaBoxTheme.colors.text,
                                    fontSize = 20.sp
                                ),
                                cursorBrush = SolidColor(NotaBoxTheme.colors.iconTint),
                                decorationBox = { innerTextField ->
                                    Box {
                                        if (createNoteViewModel.titleTextState.value.isEmpty()) {
                                            Text(
                                                text = "No Title",
                                                color = NotaBoxTheme.colors.onSearchTFBackground,
                                                modifier = Modifier,
                                                fontSize = 20.sp
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )

                            Text(
                                text = DateFormatter.formatDateFromMillis(state.newNote?.timestamp ?: 0L, "E d MMM yyyy 'at' HH:mm"),
                                color = NotaBoxTheme.colors.onSearchTFBackground,
                                modifier = Modifier,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))

                    }

                    state.newNote?.let { note ->
                        items(note.noteData.sortedBy { noteData -> noteData.order }){ data ->
                            when(data.type){
                                NoteDataType.Image -> {
                                    Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))
                                    GlideImage(
                                        model = data.imageUri,
                                        contentDescription = "Note Image",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                                            .clickable {
                                                DeviceVibration.vibrateDevice(context)
                                                createNoteViewModel.currentlyEditingNoteData.value =
                                                    data
                                                createNoteViewModel.noteOptions.value =
                                                    CurrentNoteOptionShown.EDIT_IMAGE
                                            }
                                    )
                                }
                                NoteDataType.Text -> {
                                    data.text?.let { textNoteData ->
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {

                                            TextField(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .onFocusChanged { focusState ->
                                                        createNoteViewModel.isTextNoteDataTextFieldFocused.value =
                                                            focusState.isFocused
                                                        createNoteViewModel.textNoteDataState.value =
                                                            textNoteData.text
                                                        if (focusState.isFocused) {
                                                            createNoteViewModel.currentlyEditingText.value =
                                                                textNoteData
                                                        }
                                                    },
                                                value = if (createNoteViewModel.isTextNoteDataTextFieldFocused.value && createNoteViewModel.currentlyEditingText.value?.textId == textNoteData.textId)
                                                    createNoteViewModel.textNoteDataState.value
                                                else
                                                    textNoteData.text,
                                                onValueChange = { newText -> createNoteViewModel.textNoteDataState.value = newText },
                                                textStyle = TextStyle(
                                                    color = Color(textNoteData.textColor),
                                                    fontSize = when(textNoteData.size) {
                                                        NoteTextFontSize.Large -> 25.sp
                                                        NoteTextFontSize.Normal -> 17.sp
                                                        NoteTextFontSize.Small -> 14.sp
                                                    },
                                                    textAlign = when(textNoteData.alignment){
                                                        NoteTextAlignment.Left -> TextAlign.Start
                                                        NoteTextAlignment.Center -> TextAlign.Center
                                                        NoteTextAlignment.Right -> TextAlign.End
                                                    }
                                                ),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    backgroundColor = Color.Transparent,
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent,
                                                    cursorColor = NotaBoxTheme.colors.iconTint,
                                                ),
                                            )

                                            isFloatingNoteTextMenuVisible.value = isKeyboardOpen == Keyboard.Opened && createNoteViewModel.isTextNoteDataTextFieldFocused.value && createNoteViewModel.currentlyEditingText.value?.textId == textNoteData.textId

                                            NoteTextFloatingMenu(
                                                isShowingFloatingMenu = isFloatingNoteTextMenuVisible.value,
                                                data = createNoteViewModel.currentlyEditingText.value,
                                                onDoneEditing = { noteData->
                                                    createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteText(
                                                        TextNoteData(
                                                            noteData.textId,
                                                            noteData.textColor,
                                                            createNoteViewModel.textNoteDataState.value,
                                                            noteData.size,
                                                            noteData.alignment
                                                        )
                                                    ))
                                                },
                                                onDelete = { noteData ->
                                                    createNoteViewModel.onEvent(CreateNoteEvent.DeleteNoteText(noteData))
                                                },
                                                onAlignmentChange = { noteData ->
                                                    val newAlignment = when(noteData.alignment){
                                                        NoteTextAlignment.Left -> NoteTextAlignment.Center
                                                        NoteTextAlignment.Center -> NoteTextAlignment.Right
                                                        NoteTextAlignment.Right -> NoteTextAlignment.Left
                                                    }
                                                    createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteText(
                                                        TextNoteData(
                                                            noteData.textId,
                                                            noteData.textColor,
                                                            noteData.text,
                                                            noteData.size,
                                                            newAlignment
                                                        )
                                                    ))
                                                },
                                                onTextSize = { noteData ->
                                                    val newFontSize = when(noteData.size){
                                                        NoteTextFontSize.Large -> NoteTextFontSize.Normal
                                                        NoteTextFontSize.Normal -> NoteTextFontSize.Small
                                                        NoteTextFontSize.Small -> NoteTextFontSize.Large
                                                    }
                                                    createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteText(
                                                        TextNoteData(
                                                            noteData.textId,
                                                            noteData.textColor,
                                                            noteData.text,
                                                            newFontSize,
                                                            noteData.alignment
                                                        )
                                                    ))
                                                }
                                            )

                                        }
                                    }
                                }
                                NoteDataType.File -> {
                                    NoteFileItem(
                                        file = data.file,
                                        onClick =  {
                                            DeviceVibration.vibrateDevice(context)
                                            createNoteViewModel.currentlyEditingNoteData.value = data
                                            createNoteViewModel.noteOptions.value = CurrentNoteOptionShown.EDIT_FILE
                                        }
                                    )
                                }
                                NoteDataType.CheckBoxList -> {
                                    NoteCheckBoxItem(
                                        checkBoxData = data.checkBoxList,
                                        onCheck = { checkBoxData, checked ->
                                            val checkBoxNewData = NoteCheckBox(
                                                checkBoxData?.title ?: "",
                                                checkId = checkBoxData?.checkId ?: 0,
                                                done = checked
                                            )
                                            createNoteViewModel.onEvent(CreateNoteEvent.UpdateCheckBox(checkBoxNewData))
                                        },
                                        onTitleChange = { noteCheckBox ->
                                            createNoteViewModel.onEvent(CreateNoteEvent.UpdateCheckBox(noteCheckBox))
                                        }
                                    )
                                }
                                NoteDataType.Audio -> {
                                    NoteAudioPlayer(
                                        data.audioUri,
                                        onStartPlaying = { fileUri ->
                                            player.play(fileUri)
                                            createNoteViewModel.currentlyPlayingAudioFile.value = fileUri.toString()
                                        },
                                        onStopPlaying = {
                                            player.stop()
                                            createNoteViewModel.currentlyPlayingAudioFile.value = null
                                        },
                                        player.playing.value,
                                        createNoteViewModel.currentlyPlayingAudioFile.value,
                                        onDelete = {
                                            createNoteViewModel.currentlyEditingNoteData.value = data
                                            createNoteViewModel.deleteNote()
                                        }
                                    )
                                }
                            }
                        }
                    }


                }



            }

        }



        //Bottom Sheet End
    }




}