package com.upnext.notabox.presentation.folders_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.upnext.notabox.common.Constants
import com.upnext.notabox.common.DeviceVibration
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.enums.OnFolderClickSendResultTO
import com.upnext.notabox.domain.model.CreateFolder
import com.upnext.notabox.presentation.folders_screen.components.CreateFolderDialog
import com.upnext.notabox.presentation.folders_screen.components.EditFolderBottomBar
import com.upnext.notabox.presentation.folders_screen.components.FolderItem
import com.upnext.notabox.presentation.folders_screen.event_and_state.FolderEvent
import com.upnext.notabox.presentation.folders_screen.event_and_state.FolderScreenState
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun FoldersScreen (
    onEvent: (FolderEvent) -> Unit,
    state: FolderScreenState,
    isDisplayedInDrawer: Boolean = false
) {

    val context = LocalContext.current

    var isCreateDialogON by rememberSaveable {
        mutableStateOf(false)
    }

    var isFolderEditorVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isRenamingFolder by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state.currentlyEditingFolder) {
        isFolderEditorVisible = state.currentlyEditingFolder != null
    }

    CreateFolderDialog(
        isDialogVisible = isCreateDialogON,
        onDismiss = {
            isCreateDialogON = !isCreateDialogON
        },
        onCreate = { name, isRenaming ->
            if (isRenaming){
                val oldFolder = state.currentlyEditingFolder
                oldFolder?.let {
                    val updatedFolder = Folder(
                        id = it.id,
                        folderName = name,
                        isPinned = oldFolder.isPinned
                    )
                    onEvent(FolderEvent.UpdateFolder(updatedFolder))
                }
            }else{
                val folder = CreateFolder(name, false)
                onEvent(FolderEvent.CreateFolderEvent(folder))
            }
            isCreateDialogON = !isCreateDialogON
            isRenamingFolder = !isRenamingFolder
        },
        isRenamingFolder = isRenamingFolder
    )

    Scaffold(
        backgroundColor = if (isDisplayedInDrawer) NotaBoxTheme.colors.dialogBgColor else NotaBoxTheme.colors.background,
        bottomBar = {
            AnimatedVisibility(
                visible = isFolderEditorVisible,
                modifier = Modifier.fillMaxWidth(),
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                EditFolderBottomBar(
                    onClose = {
                        onEvent(FolderEvent.SetCurrentlyEditingFolder(null))
                    },
                    onDelete = {
                        onEvent(FolderEvent.DeleteFolder(state.currentlyEditingFolder?.id ?: 0))
                        onEvent(FolderEvent.SetCurrentlyEditingFolder(null))
                    },
                    onPinned = {
                        onEvent(
                            FolderEvent.UpdateFolder(
                                Folder(
                                    state.currentlyEditingFolder?.id ?: 0,
                                    state.currentlyEditingFolder?.folderName ?: "",
                                    !(state.currentlyEditingFolder?.isPinned ?: false)
                                )
                            )
                        )
                    },
                    onRename = {
                        isCreateDialogON = true
                        isRenamingFolder = true
                    }
                )
            }
        }
    ) {
        it
        LazyColumn(
            modifier = Modifier
                .testTag(TestTags.FOLDERS_SCREEN_TEST_TAG)
                .fillMaxSize()
                .padding(horizontal = NotaBoxTheme.spaces.medium),
            contentPadding = PaddingValues(vertical = NotaBoxTheme.spaces.mediumLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state.folders.isEmpty()){
                item {
                    Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.large))
                    Text(
                        text = "Looks like you dont have any folders, want to create click bellow",
                        color = NotaBoxTheme.colors.text,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = NotaBoxTheme.spaces.mediumLarge)
                    )
                    Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.large))
                }
            }

            items(state.folders){ folder ->
                FolderItem(
                    folder = folder,
                    onClick = {

                    },
                    onLongPress = {
                        DeviceVibration.vibrateDevice(context)
                        isFolderEditorVisible = true
                        onEvent(FolderEvent.SetCurrentlyEditingFolder(folder))
                    },
                    isCurrentlyEditing = state.currentlyEditingFolder?.id == folder.id
                )
                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))
            }

            item {
                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))
                Spacer(
                    modifier = Modifier
                        .width(NotaBoxTheme.spaces.veryLarge)
                        .height(NotaBoxTheme.spaces.small)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(NotaBoxTheme.spaces.mediumLarge))
                        .background(NotaBoxTheme.colors.onBackground)
                        .padding(vertical = NotaBoxTheme.spaces.mediumLarge)
                        .clickable {
                            isCreateDialogON = !isCreateDialogON
                            isRenamingFolder = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add Folder",
                            tint = NotaBoxTheme.colors.onBackgroundIconTint
                        )
                        Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                        Text(
                            text = "Create Folder",
                            color = NotaBoxTheme.colors.onBackgroundText
                        )
                    }

                }
            }

        }

    }

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun FolderScreenPreview() {
    FoldersScreen(
        onEvent = {},
        state = FolderScreenState(
            folders = listOf(
                Folder(
                    0, "Folder Preview", isPinned = true
                ),
                Folder(
                    1, "Folder Preview", isPinned = true
                )
            ),
            currentlyEditingFolder =  Folder(
                0, "Folder Preview", isPinned = true
            )
        ),
    )
}