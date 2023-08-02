package com.upnext.notabox.presentation.folders_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.upnext.notabox.R
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun EditFolderBottomBar(
    onClose: () -> Unit,
    onDelete: () -> Unit,
    onRename: () -> Unit,
    onPinned: () -> Unit
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = NotaBoxTheme.colors.background,
        elevation = NotaBoxTheme.spaces.medium
    ) {

        BottomNavigationItem(
            selected = true,
            onClick = {
                onRename()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.DriveFileRenameOutline,
                    contentDescription = stringResource(R.string.rename_folder),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )

        BottomNavigationItem(
            selected = true,
            onClick = {
                onPinned()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Attachment,
                    contentDescription = stringResource(R.string.pin_folder),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )

        BottomNavigationItem(
            selected = true,
            onClick = {
                onDelete()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = stringResource(R.string.delete_folder),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )

        BottomNavigationItem(
            selected = true,
            onClick = {
                onClose()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_folder_editor),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )
    }
}