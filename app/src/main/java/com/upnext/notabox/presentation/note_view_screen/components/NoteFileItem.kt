package com.upnext.notabox.presentation.note_view_screen.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upnext.notabox.domain.enums.FileNoteDataType
import com.upnext.notabox.domain.model.FileNoteData
import com.upnext.notabox.presentation.note_view_screen.events.CreateNoteEvent
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteFileItem(
    file: FileNoteData?,
    onClick: () -> Unit,
) {

    if (file != null) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = NotaBoxTheme.spaces.mediumLarge)
                .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                .background(NotaBoxTheme.colors.searchTFBackground)
                .combinedClickable(
                    onClick = {
                        onClick()
                    }
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Icon(
                        imageVector = Icons.Default.UploadFile,
                        contentDescription = "File",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                    Text(
                        text = file.fileName,
                        color = NotaBoxTheme.colors.text
                    )

                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Delete Corrupted file",
                    tint = NotaBoxTheme.colors.iconTint
                )


            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = NotaBoxTheme.spaces.mediumLarge)
                .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                .background(NotaBoxTheme.colors.searchTFBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FilePresent,
                        contentDescription = "File",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                    Text(
                        text = "Corrupted file, please delete",
                        color = NotaBoxTheme.colors.text
                    )
                }
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "Delete Corrupted file",
                    tint = NotaBoxTheme.colors.iconTint
                )
            }
        }

    }
}
