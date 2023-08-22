package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upnext.notabox.domain.enums.Keyboard
import com.upnext.notabox.domain.enums.NoteTextAlignment
import com.upnext.notabox.domain.enums.NoteTextFontSize
import com.upnext.notabox.domain.model.TextNoteData
import com.upnext.notabox.presentation.global_components.keyboardAsState
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteText(
    data: TextNoteData,
    onTextChange: (TextNoteData) -> Unit,
    onDelete: (TextNoteData) -> Unit,
    onAlignmentChange: (TextNoteData) -> Unit,
    onTextSize: (TextNoteData) -> Unit,
    onLongClick: () -> Unit
) {


    var textState by rememberSaveable {
        mutableStateOf(data.text)
    }


    var isMenuOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = textState){
        onTextChange(TextNoteData(
            data.textId,
            data.textColor,
            textState,
            data.size,
            data.alignment
        ))
    }

    val keyboardState by keyboardAsState()

    LaunchedEffect(key1 = keyboardState, block = {
        isMenuOpen = keyboardState == Keyboard.Opened && isFocused
    })

    val menuSize by animateDpAsState(
        targetValue = if(isMenuOpen) 70.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    onLongClick()
                },
                onClick = {}
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = textState,
            onValueChange = { newText -> textState = newText },
            textStyle = TextStyle(
                color = Color(data.textColor),
                fontSize = when (data.size) {
                    NoteTextFontSize.Large -> 25.sp
                    NoteTextFontSize.Normal -> 17.sp
                    NoteTextFontSize.Small -> 14.sp
                },
                textAlign = when (data.alignment) {
                    NoteTextAlignment.Left -> TextAlign.Start
                    NoteTextAlignment.Center -> TextAlign.Center
                    NoteTextAlignment.Right -> TextAlign.End
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = NotaBoxTheme.colors.iconTint,
            ),
        )


        AnimatedVisibility(
            visible = isMenuOpen,
            modifier = Modifier.fillMaxWidth(),
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
        ){
            NoteTextFloatingMenu(
                modifier = Modifier.size(menuSize),
                isShowingFloatingMenu = isMenuOpen,
                data = data,
                onDelete = { textNoteData ->
                    onDelete(textNoteData)
                },
                onAlignmentChange = { textNoteData ->
                    onAlignmentChange(textNoteData)
                },
                onTextSize = { textNoteData ->
                    onTextSize(textNoteData)
                }
            )
        }

    }




}