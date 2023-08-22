package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.PhotoSizeSelectActual
import androidx.compose.material.icons.filled.PhotoSizeSelectLarge
import androidx.compose.material.icons.filled.PhotoSizeSelectSmall
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.upnext.notabox.R
import com.upnext.notabox.domain.model.NoteImage
import com.upnext.notabox.domain.model.NoteImageSize
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun EditImageBottomMenu(
    onDelete: () -> Unit,
    onAddComment: () -> Unit,
    onClose: () -> Unit,
    onResize: () -> Unit,
) {


    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = NotaBoxTheme.colors.background,
        elevation = NotaBoxTheme.spaces.medium
    ){

        BottomNavigationItem(
            selected = true,
            onClick = {
                onResize()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.PhotoSizeSelectSmall,
                    contentDescription = stringResource(R.string.resize_image),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )

        BottomNavigationItem(
            selected = true,
            onClick = {
                onAddComment()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Comment,
                    contentDescription = stringResource(R.string.comment),
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
                    contentDescription = stringResource(R.string.delete_file),
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
                    contentDescription = stringResource(R.string.close),
                    tint = NotaBoxTheme.colors.iconTint
                )
            },
            selectedContentColor = Color.Black
        )


    }

}