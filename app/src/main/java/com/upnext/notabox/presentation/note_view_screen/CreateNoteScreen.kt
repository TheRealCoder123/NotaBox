package com.upnext.notabox.presentation.note_view_screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.upnext.notabox.R
import com.upnext.notabox.common.DateFormatter
import com.upnext.notabox.common.DeviceVibration
import com.upnext.notabox.common.MimeType
import com.upnext.notabox.common.Permission
import com.upnext.notabox.common.rememberWindowInfo
import com.upnext.notabox.domain.audio_player.IAudioPlayer
import com.upnext.notabox.domain.enums.CurrentNoteOptionShown
import com.upnext.notabox.domain.enums.FileNoteDataType
import com.upnext.notabox.domain.enums.NoteTextAlignment
import com.upnext.notabox.domain.enums.NoteTextFontSize
import com.upnext.notabox.domain.model.FileNoteData
import com.upnext.notabox.domain.model.NoteCheckBox
import com.upnext.notabox.domain.model.TextNoteData
import com.upnext.notabox.domain.enums.NoteDataType
import com.upnext.notabox.domain.model.NoteImage
import com.upnext.notabox.domain.model.NoteImageSize
import com.upnext.notabox.presentation.activities.MainActivity.components.drag_and_drop.DragTarget
import com.upnext.notabox.presentation.global_components.keyboardAsState
import com.upnext.notabox.presentation.note_view_screen.components.AudioRecordingDialog
import com.upnext.notabox.presentation.note_view_screen.components.BottomMenu
import com.upnext.notabox.presentation.note_view_screen.components.EditFileBottomMenu
import com.upnext.notabox.presentation.note_view_screen.components.EditImageBottomMenu
import com.upnext.notabox.presentation.note_view_screen.components.MoreCreateNoteMenu
import com.upnext.notabox.presentation.note_view_screen.components.MoveNoteDataBottomBar
import com.upnext.notabox.presentation.note_view_screen.components.NoteAudioPlayer
import com.upnext.notabox.presentation.note_view_screen.components.NoteCheckBoxItem
import com.upnext.notabox.presentation.note_view_screen.components.NoteFileItem
import com.upnext.notabox.presentation.note_view_screen.components.NoteImage
import com.upnext.notabox.presentation.note_view_screen.components.NoteText
import com.upnext.notabox.presentation.priorities_dialog.PriorityDialog
import com.upnext.notabox.presentation.note_view_screen.components.SelectFolderDialog
import com.upnext.notabox.presentation.note_view_screen.events.CreateNoteEvent
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun CreateNoteScreen(
    createNoteViewModel: CreateNoteViewModel = hiltViewModel(),
    navController: NavController,
    onBackPressed: () -> Unit = {},
    noteId: String? = "",
    folderId: Int = -1
) {

    LaunchedEffect(key1 = true){
        createNoteViewModel.createNoteIfNoteCreated(noteId = noteId, folderId = folderId)
    }

    val state = createNoteViewModel.state.value


    val context = LocalContext.current
    val windowInfo = rememberWindowInfo()
    val coroutineScope = rememberCoroutineScope()
    val isKeyboardOpen by keyboardAsState()
    val lazyColumnState = rememberLazyListState()

    val player by lazy {
        IAudioPlayer(context)
    }



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

    LaunchedEffect(key1 = createNoteViewModel.titleTextState.value){
        if (state.newNote != null){
            createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteTitle(createNoteViewModel.titleTextState.value))
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                val mimeType = context.contentResolver.getType(uri)
                val fileName = MimeType.getFileNameFromUri(context.contentResolver,uri)
                coroutineScope.launch {
                    lazyColumnState.scrollToItem(state.newNote?.noteData?.indexOf(state.newNote.noteData.last()) ?: 0)
                }
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
            coroutineScope.launch {
                lazyColumnState.scrollToItem(state.newNote?.noteData?.indexOf(state.newNote.noteData.last()) ?: 0)
            }
        }
    )

    PriorityDialog(
        isVisible = createNoteViewModel.isPriorityDialogOpen.value,
        onDismiss = {
            createNoteViewModel.isPriorityDialogOpen.value = false
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
                    onBackPressed()
                },
                onAddToFolder = {
                    createNoteViewModel.isSelectFolderDialogVisible = true
                },
                onAddPriorityToNote = {
                    createNoteViewModel.isPriorityDialogOpen.value = true
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
                                coroutineScope.launch {
                                    lazyColumnState.scrollToItem(state.newNote?.noteData?.indexOf(state.newNote.noteData.last()) ?: 0)
                                }
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
                                coroutineScope.launch {
                                    if (state.newNote?.noteData?.isNotEmpty()!!){
                                        lazyColumnState.scrollToItem(state.newNote.noteData.indexOf(state.newNote.noteData.last()))
                                    }
                                }
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
                                createNoteViewModel.currentlyEditingNoteData.value = null
                                createNoteViewModel.noteOptions.value = CurrentNoteOptionShown.Default
                            },
                            onResize = {
                                val image = createNoteViewModel.currentlyEditingNoteData.value?.image
                                val imageSize = if (image?.size == NoteImageSize.MATCH_SCREEN){
                                    NoteImageSize.SMALL
                                }else{
                                    NoteImageSize.MATCH_SCREEN
                                }
                                val newImage = NoteImage(
                                    image?.imageID ?: 0,
                                    image?.imageUri!!,
                                    image.comment,
                                    imageSize
                                )
                                createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteImageSize(newImage))
                                createNoteViewModel.noteOptions.value = CurrentNoteOptionShown.Default
                                createNoteViewModel.currentlyEditingNoteData.value = null
                            }
                        )
                    }

                    CurrentNoteOptionShown.MOVE_NOTE_DATA -> {
                        MoveNoteDataBottomBar(
                            noteData = createNoteViewModel.currentlyEditingNoteData.value,
                            onMoveUp = {
                                createNoteViewModel.onEvent(CreateNoteEvent.NoteDataMoveUp(it))
                            },
                            onMoveDown = {
                                createNoteViewModel.onEvent(CreateNoteEvent.NoteDataMoveDown(it))
                            }
                        )
                    }
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "All Notes",
                            color = NotaBoxTheme.colors.text
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIos,
                                contentDescription = stringResource(id = R.string.back),
                                tint = NotaBoxTheme.colors.iconTint
                            )
                        }
                    },
                    backgroundColor = NotaBoxTheme.colors.background,
                    actions = {
                        when(createNoteViewModel.noteOptions.value){
                            CurrentNoteOptionShown.Default -> {}
                            CurrentNoteOptionShown.EDIT_FILE -> {}
                            CurrentNoteOptionShown.EDIT_IMAGE -> {}
                            CurrentNoteOptionShown.MOVE_NOTE_DATA -> {
                                IconButton(onClick = {
                                    createNoteViewModel.noteOptions.value = CurrentNoteOptionShown.Default
                                    createNoteViewModel.currentlyEditingNoteData.value = null
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(id = R.string.close)
                                    )
                                }
                            }
                        }
                    }
                )
            }
        ) { padding ->


            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(NotaBoxTheme.spaces.mediumLarge)
            ){

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = NotaBoxTheme.spaces.extraLarge),
                    state = lazyColumnState
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
                        Log.e("note data", "${note.noteData}")
                        items(note.noteData.distinct()){ data ->
                            when(data.type){
                                NoteDataType.Image -> {
                                    NoteImage(
                                        image = data.image,
                                        onPress = {
                                            DeviceVibration.vibrateDevice(context)
                                            if (createNoteViewModel.currentlyEditingNoteData.value == data){
                                                createNoteViewModel.currentlyEditingNoteData.value = null
                                                createNoteViewModel.noteOptions.value =
                                                    CurrentNoteOptionShown.Default
                                            }else{
                                                createNoteViewModel.currentlyEditingNoteData.value = data
                                                createNoteViewModel.noteOptions.value =
                                                    CurrentNoteOptionShown.EDIT_IMAGE
                                            }
                                        },
                                        onLongPress = {}
                                    )
                                }
                                NoteDataType.Text -> {
                                    data.text?.let {
                                        NoteText(
                                            data = it,
                                            onTextChange = { newTextNoteData->
                                                createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteText(newTextNoteData))
                                            },
                                            onDelete = { textNoteData->
                                                createNoteViewModel.onEvent(CreateNoteEvent.DeleteNoteText(textNoteData))
                                            },
                                            onAlignmentChange = { textNoteData->
                                                val newAlignment = when(textNoteData.alignment){
                                                    NoteTextAlignment.Left -> NoteTextAlignment.Center
                                                    NoteTextAlignment.Center -> NoteTextAlignment.Right
                                                    NoteTextAlignment.Right -> NoteTextAlignment.Left
                                                }
                                                createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteText(
                                                    TextNoteData(
                                                        textNoteData.textId,
                                                        textNoteData.textColor,
                                                        textNoteData.text,
                                                        textNoteData.size,
                                                        newAlignment
                                                    )
                                                ))
                                            },
                                            onTextSize = { textNoteData->
                                                val newFontSize = when(textNoteData.size){
                                                    NoteTextFontSize.Large -> NoteTextFontSize.Normal
                                                    NoteTextFontSize.Normal -> NoteTextFontSize.Small
                                                    NoteTextFontSize.Small -> NoteTextFontSize.Large
                                                }
                                                createNoteViewModel.onEvent(CreateNoteEvent.UpdateNoteText(
                                                    TextNoteData(
                                                        textNoteData.textId,
                                                        textNoteData.textColor,
                                                        textNoteData.text,
                                                        newFontSize,
                                                        textNoteData.alignment
                                                    )
                                                ))
                                            },
                                            onLongClick = {}
                                        )

                                    }
                                }
                                NoteDataType.File -> {
                                    NoteFileItem(
                                        file = data.file,
                                        onClick =  {
                                            DeviceVibration.vibrateDevice(context)
                                            createNoteViewModel.currentlyEditingNoteData.value = data
                                            createNoteViewModel.noteOptions.value = CurrentNoteOptionShown.EDIT_FILE
                                        },
                                        onLongClick = {}
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
                                        },
                                        onDelete = {
                                            createNoteViewModel.onEvent(CreateNoteEvent.DeleteNoteData(data))
                                        },
                                        onLongClick = {}
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
                                        },
                                        onLongClick = {}
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