package com.upnext.notabox.presentation.activities.MainActivity.components.FAB

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.upnext.notabox.R
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun CreateNoteFab(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        backgroundColor = NotaBoxTheme.colors.onBackground,
        shape = RoundedCornerShape(100.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(R.string.add),
            tint = NotaBoxTheme.colors.onBackgroundIconTint
        )
    }
}