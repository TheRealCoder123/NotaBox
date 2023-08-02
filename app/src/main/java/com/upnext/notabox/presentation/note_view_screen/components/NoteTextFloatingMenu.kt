package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upnext.notabox.common.IdentifierGenerator
import com.upnext.notabox.domain.enums.NoteTextAlignment
import com.upnext.notabox.domain.enums.NoteTextFontSize
import com.upnext.notabox.domain.model.TextNoteData
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun NoteTextFloatingMenu(
    isShowingFloatingMenu: Boolean,
    data: TextNoteData?,
    onDoneEditing: (TextNoteData) -> Unit = {},
    onDelete: (TextNoteData) -> Unit = {},
    onAlignmentChange: (TextNoteData) -> Unit = {},
    onTextSize: (TextNoteData) -> Unit = {}
) {

    if (isShowingFloatingMenu && data != null){
        Box(
            modifier = Modifier.padding(5.dp)
        ){

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(NotaBoxTheme.spaces.extraLarge))
                    .background(NotaBoxTheme.colors.searchTFOuterBackground)
                    .padding(vertical = NotaBoxTheme.spaces.small, horizontal = NotaBoxTheme.spaces.mediumLarge),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .background(Color(data.textColor))
                )

                Spacer(
                    modifier = Modifier
                        .width(NotaBoxTheme.spaces.mediumLarge)
                )

                IconButton(onClick = {
                    onTextSize(data)
                }) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "Text Size",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(NotaBoxTheme.spaces.mediumLarge)
                )

                IconButton(onClick = {
                    onAlignmentChange(data)
                }) {
                    Icon(
                        imageVector = when(data.alignment){
                            NoteTextAlignment.Left -> Icons.Default.FormatAlignLeft
                            NoteTextAlignment.Center -> Icons.Default.FormatAlignCenter
                            NoteTextAlignment.Right -> Icons.Default.FormatAlignRight
                        },
                        contentDescription = "Text Align",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(NotaBoxTheme.spaces.mediumLarge)
                )

                IconButton(onClick = {
                    onDelete(data)
                }) {
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = "Text Delete",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(NotaBoxTheme.spaces.mediumLarge)
                )

                IconButton(onClick = {
                    onDoneEditing(data)
                }) {
                    Icon(
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = "Text Done",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                }

            }

        }
    }

}

@Composable
@Preview
fun Preview() {
    NoteTextFloatingMenu(
        true,
        TextNoteData(
            IdentifierGenerator.generateNumberId(),
            Color.Gray.toArgb(),
            "Start Typing",
            NoteTextFontSize.Normal,
            NoteTextAlignment.Left
        )
    )
}