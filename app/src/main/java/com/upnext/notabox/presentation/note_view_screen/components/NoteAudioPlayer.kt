package com.upnext.notabox.presentation.note_view_screen.components

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.upnext.notabox.common.AudioFormatter
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteAudioPlayer(
    fileUri: String?,
    onStartPlaying: (Uri) -> Unit,
    onStopPlaying: () -> Unit,
    isPlaying: Boolean,
    currentlyPlayingAudio: String?,
    onDelete: () -> Unit,
    onLongClick: () -> Unit
) {


    var audioDuration by remember { mutableStateOf(0L) }
    val context = LocalContext.current

    LaunchedEffect(fileUri) {
        // You can use the MediaMetadataRetriever here to get the duration of the audio file.
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.parse(fileUri))
        val durationString = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        audioDuration = durationString?.toLong() ?: 0L
        retriever.release()
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = NotaBoxTheme.spaces.mediumLarge)
        .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
        .background(NotaBoxTheme.colors.searchTFBackground)
        .combinedClickable(
            onClick = {
                if (isPlaying)
                    onStopPlaying()
                else
                    onStartPlaying(Uri.parse(fileUri))
            },
            onLongClick = {
                onLongClick()
            }
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (currentlyPlayingAudio == fileUri && isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                contentDescription = "Play Audio Icon",
                tint = NotaBoxTheme.colors.iconTint
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AudioFormatter.formatAudioDuration(audioDuration)
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Audio",
                    tint = NotaBoxTheme.colors.iconTint,
                    modifier = Modifier.clickable {
                        onDelete()
                    }
                )
            }
        }
    }




}