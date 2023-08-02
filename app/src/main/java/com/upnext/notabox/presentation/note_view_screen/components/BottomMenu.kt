package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.upnext.notabox.R
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun BottomMenu(
    onAddImage: () -> Unit,
    onAddText: () -> Unit,
    onAttachFile: () -> Unit,
    onAddCheckList: () -> Unit,
    onAddAudioFile: () -> Unit,
    onMore: () -> Unit,
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = NotaBoxTheme.colors.background,
        elevation = NotaBoxTheme.spaces.medium
    ) {
        BottomNavigationItem(
            selected = true,
            onClick = {
                 onAddImage()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = stringResource(R.string.choose_an_image),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )
        BottomNavigationItem(
            selected = true,
            onClick = {
                 onAddText()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.TextFields,
                    contentDescription = stringResource(R.string.add_a_text),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )
        BottomNavigationItem(
            selected = true,
            onClick = {
                 onAttachFile()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = stringResource(R.string.attach_a_file),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )
        BottomNavigationItem(
            selected = true,
            onClick = {
                 onAddCheckList()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Checklist,
                    contentDescription = stringResource(R.string.add_a_check_list),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )
        BottomNavigationItem(
            selected = true,
            onClick = {
                 onAddAudioFile()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Audiotrack,
                    contentDescription = stringResource(R.string.add_audio_file),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )
        BottomNavigationItem(
            selected = true,
            onClick = {
                 onMore()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = stringResource(R.string.more),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )
    }
}