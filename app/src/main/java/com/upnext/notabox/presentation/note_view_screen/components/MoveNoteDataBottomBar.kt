package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.upnext.notabox.R
import com.upnext.notabox.domain.model.NoteData
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun MoveNoteDataBottomBar(
    onMoveUp: (NoteData?) -> Unit,
    onMoveDown: (NoteData?) -> Unit,
    noteData: NoteData?
) {

    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = NotaBoxTheme.colors.background,
        elevation = NotaBoxTheme.spaces.medium
    ){
        BottomNavigationItem(
            selected = true,
            onClick = {
                onMoveUp(noteData)
            },
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = stringResource(R.string.move_up),
                        tint = NotaBoxTheme.colors.iconTint
                    )
                    Text(
                        text = stringResource(id = R.string.move_up),
                        color = NotaBoxTheme.colors.text
                    )
                }
            },
            selectedContentColor = Color.Black
        )
        BottomNavigationItem(
            selected = true,
            onClick = {
                onMoveDown(noteData)
            },
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = stringResource(R.string.move_down),
                        tint = NotaBoxTheme.colors.iconTint
                    )
                    Text(
                        text = stringResource(id = R.string.move_down),
                        color = NotaBoxTheme.colors.text
                    )
                }
            },
            selectedContentColor = Color.Black
        )
    }

}
