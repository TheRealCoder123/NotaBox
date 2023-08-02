package com.upnext.notabox.presentation.folders_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderItem(
    folder: Folder,
    onClick: (Folder) -> Unit,
    onLongPress: (Folder) -> Unit,
    isCurrentlyEditing: Boolean
) {

    val colorAnimation by animateColorAsState(
        targetValue = if (isCurrentlyEditing) Color.LightGray else NotaBoxTheme.colors.onBackground,
        animationSpec = tween(durationMillis = 800)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(NotaBoxTheme.spaces.mediumLarge))
            .background(colorAnimation)
            .padding(NotaBoxTheme.spaces.mediumLarge)
            .combinedClickable(
                onClick = {
                    onClick(folder)
                },
                onLongClick = {
                    onLongPress(folder)
                }
            ),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Folder,
                    contentDescription = "Folder",
                    tint = NotaBoxTheme.colors.onBackgroundIconTint
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                Text(
                    text = folder.folderName,
                    color = NotaBoxTheme.colors.onBackgroundText
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(
                    visible = folder.isPinned,
                    enter = slideInHorizontally() + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = "Folder",
                        tint = NotaBoxTheme.colors.onBackgroundIconTint,
                    )
                }
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                Text(
                    text = "25",
                    color = NotaBoxTheme.colors.onBackgroundText
                )
            }
        }

    }
}




@Composable
@Preview
fun FolderItemPreview() {
    FolderItem(folder = Folder(1, "Folder Name", true), onLongPress = {}, onClick = {}, isCurrentlyEditing = false)
}