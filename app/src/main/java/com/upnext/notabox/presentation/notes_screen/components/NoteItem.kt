package com.upnext.notabox.presentation.notes_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.upnext.notabox.common.DateFormatter
import com.upnext.notabox.domain.model.NoteListItemData
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NoteItem(
    note: NoteListItemData,
    onNoteClick: (NoteListItemData) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(NotaBoxTheme.spaces.medium)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(NotaBoxTheme.spaces.mediumLarge))
                .background(NotaBoxTheme.colors.onBackground)
                .padding(NotaBoxTheme.spaces.mediumLarge)
                .clickable {
                    onNoteClick(note)
                }
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title.ifEmpty { "No Title" },
                    color = NotaBoxTheme.colors.onBackgroundText
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                if (note.priority != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(NotaBoxTheme.spaces.extraLarge))
                            .size(
                                width = NotaBoxTheme.spaces.medium,
                                height = NotaBoxTheme.spaces.small
                            )
                            .background(Color(note.priority.color))
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = if (note.textNoteData.isNotEmpty()) note.textNoteData.random().text?.text ?: "No Text" else "No Text",
                    color = NotaBoxTheme.colors.description,
                    fontSize = 13.sp,
                    maxLines = 3
                )


                note.image?.let {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                            .size(50.dp)
                    ){
                        GlideImage(
                            model = it,
                            contentDescription = "Note Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }


                }

            }

            Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = DateFormatter.formatDateFromMillis(note.timestamp, "E d MMM yyyy 'at' HH:mm"),
                    color = NotaBoxTheme.colors.description,
                    fontSize = 13.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (note.hasFile) {
                        Icon(
                            imageVector = Icons.Default.FilePresent,
                            contentDescription = "Audio FIle",
                            tint = NotaBoxTheme.colors.onBackgroundIconTint
                        )
                    }

                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))

                    if (note.hasAudioFiles) {
                        Icon(
                            imageVector = Icons.Default.AudioFile,
                            contentDescription = "Audio FIle",
                            tint = NotaBoxTheme.colors.onBackgroundIconTint
                        )
                    }

                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))

                    if (note.hasCheckBoxList) {
                        Icon(
                            imageVector = Icons.Default.Checklist,
                            contentDescription = "Check List",
                            tint = NotaBoxTheme.colors.onBackgroundIconTint
                        )
                    }

                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))

                    if (note.hasMindMap) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = "Mind Map",
                            tint = NotaBoxTheme.colors.onBackgroundIconTint
                        )
                    }

                }

            }

        }

    }
}


