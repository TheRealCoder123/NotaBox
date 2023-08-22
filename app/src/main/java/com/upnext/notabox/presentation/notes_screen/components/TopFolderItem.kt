package com.upnext.notabox.presentation.notes_screen.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.upnext.notabox.R
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun TopFolderItem(
    folder: Folder?,
    isAllShown: Boolean = false,
    isSelected: Boolean,
    isAllFoldersBtnShown: Boolean = false,
    onClick: (Folder?) -> Unit
) {

    val transition = updateTransition(
        targetState = isSelected,
        label = "color_trans"
    )

    val selectedColorAnim by transition.animateColor(
        targetValueByState = {
            if (it) NotaBoxTheme.colors.selected else NotaBoxTheme.colors.unSelected
        },
        label = "bg_color",
        transitionSpec = { tween(durationMillis = 500) }
    )


    val selectedTintColorAnim by transition.animateColor(
        targetValueByState = {
            if (it) NotaBoxTheme.colors.onSelected else NotaBoxTheme.colors.text
        },
        label = "bg_color",
        transitionSpec = { tween(durationMillis = 500) }
    )


    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
            .background(selectedColorAnim)
            .padding(
                horizontal = NotaBoxTheme.spaces.mediumLarge,
                vertical = NotaBoxTheme.spaces.medium
            )
            .clickable {
                onClick(folder)
            }
        ,
        contentAlignment = Alignment.Center
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isAllShown) "All" else if(isAllFoldersBtnShown) "All Folders" else folder?.folderName ?: "Null Folder",
                color = selectedTintColorAnim
            )
            if (folder?.isPinned == true) {
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                Icon(
                    imageVector = Icons.Default.PushPin,
                    contentDescription = stringResource(R.string.pinned_folder),
                    tint = selectedTintColorAnim,
                    modifier = Modifier.size(NotaBoxTheme.spaces.mediumLarge)
                )
            }

        }
    }

}

fun Modifier.addBorderOnCondition(condition: Boolean) = this.then(
    if (condition){
        border(
            1.dp,
            Color.Yellow,
            RoundedCornerShape(8.dp)
        )
    }else{
        border(
            0.dp,
            Color.Transparent,
            RoundedCornerShape(0.dp)
        )
    }
)