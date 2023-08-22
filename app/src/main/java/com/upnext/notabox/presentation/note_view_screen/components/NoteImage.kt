package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.upnext.notabox.common.DeviceVibration
import com.upnext.notabox.common.rememberWindowInfo
import com.upnext.notabox.domain.enums.CurrentNoteOptionShown
import com.upnext.notabox.domain.model.NoteImage
import com.upnext.notabox.domain.model.NoteImageSize
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteImage(
    image: NoteImage?,
    onPress: (NoteImage?) -> Unit,
    onLongPress: () -> Unit
) {

    val imageSizeModifier = if (image?.size == NoteImageSize.SMALL) {
        Modifier.size(200.dp)
    } else {
        Modifier.fillMaxWidth()
    }

    Column(
        modifier = Modifier
            .then(imageSizeModifier)
    ) {
        Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))
        GlideImage(
            model = image?.imageUri,
            contentDescription = "Note Image",
            modifier = Modifier
                .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                .combinedClickable(
                    onClick = {
                        onPress(image)
                    },
                    onLongClick = {
                        onLongPress()
                    }
                )
        )
    }


}