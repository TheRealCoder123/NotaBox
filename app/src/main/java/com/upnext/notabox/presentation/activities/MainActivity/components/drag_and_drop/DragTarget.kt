package com.upnext.notabox.presentation.activities.MainActivity.components.drag_and_drop

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import com.upnext.notabox.presentation.ui.theme.LocalDragTargetInfo


@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    content: @Composable () -> Unit
) {
    var currentPosition by remember {
        mutableStateOf(Offset.Zero)
    }

    val currentDragState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
            .onGloballyPositioned {
                currentPosition = it.localToWindow(Offset.Zero)
            }
            .pointerInput(Unit){
                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        currentDragState.dataToDrop = dataToDrop
                        currentDragState.isDragging = true
                        currentDragState.dragPosition = currentPosition + it
                        currentDragState.draggableComposable = content
                    },
                    onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
                        currentDragState.dragOffset = Offset(dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        currentDragState.dragOffset = Offset.Zero
                        currentDragState.isDragging = false
                    },
                    onDragCancel = {
                        currentDragState.dragOffset = Offset.Zero
                        currentDragState.isDragging = false
                    }
                )
            }
    ){
        content()
    }


}