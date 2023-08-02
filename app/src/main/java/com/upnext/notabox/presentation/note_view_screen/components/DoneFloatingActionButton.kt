package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.upnext.notabox.R
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun DoneFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
        backgroundColor = NotaBoxTheme.colors.onBackground,
        shape = RoundedCornerShape(100.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.DoneAll,
            contentDescription = stringResource(R.string.done),
            tint = NotaBoxTheme.colors.onBackgroundIconTint
        )
    }
}