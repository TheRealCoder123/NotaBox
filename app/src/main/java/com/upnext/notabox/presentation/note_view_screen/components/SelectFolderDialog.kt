package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.presentation.folders_screen.FolderViewModel
import com.upnext.notabox.presentation.folders_screen.components.FolderItem
import com.upnext.notabox.presentation.folders_screen.event_and_state.FolderEvent
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectFolderDialog(
    isVisible: Boolean,
    onDone: (Folder?) -> Unit,
    addNoteFolderId: Int
) {
    if (isVisible){

        val folderViewModel = hiltViewModel<FolderViewModel>()
        val state = folderViewModel.state.value

        Dialog(
            onDismissRequest = {
                onDone(null)
            }
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NotaBoxTheme.colors.dialogBgColor)
                    .padding(NotaBoxTheme.spaces.mediumLarge)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Select A Folder",
                            color = NotaBoxTheme.colors.text,
                            style = NotaBoxTheme.typography.title,
                        )
                        Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))
                    }
                    items(state.folders){folder ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(NotaBoxTheme.spaces.mediumLarge))
                                .background(if (addNoteFolderId == folder.id) NotaBoxTheme.colors.onBackground else Color.Transparent)
                                .padding(NotaBoxTheme.spaces.mediumLarge)
                                .combinedClickable(
                                    onClick = {
                                        onDone(folder)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Folder,
                                    contentDescription = "Folder",
                                    tint = if (addNoteFolderId == folder.id) NotaBoxTheme.colors.onBackgroundIconTint else NotaBoxTheme.colors.iconTint
                                )
                                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                                Text(
                                    text = folder.folderName,
                                    color = if (addNoteFolderId == folder.id) NotaBoxTheme.colors.onBackgroundText else NotaBoxTheme.colors.text
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))
                    }
                }

            }


        }
    }
}