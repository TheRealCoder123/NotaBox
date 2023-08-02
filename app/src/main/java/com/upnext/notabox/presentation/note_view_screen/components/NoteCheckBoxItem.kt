package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import com.upnext.notabox.domain.model.NoteCheckBox
import com.upnext.notabox.presentation.note_view_screen.CreateNoteViewModel
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun NoteCheckBoxItem(
    checkBoxData: NoteCheckBox?,
    onCheck: (data: NoteCheckBox?, checked: Boolean) -> Unit,
    onTitleChange: (data: NoteCheckBox) -> Unit
) {

    var checkedState by remember {
        mutableStateOf(checkBoxData?.done ?: false)
    }

    var titleTextState by remember {
        mutableStateOf(checkBoxData?.title ?: "")
    }

    LaunchedEffect(key1 = checkedState){
        onCheck(checkBoxData, checkedState)
    }

    LaunchedEffect(key1 = titleTextState){
        if (titleTextState != "Empty Check Box" || titleTextState.isNotEmpty()){
            onTitleChange(NoteCheckBox(titleTextState, checkedState, checkBoxData?.checkId ?: 0))
        }
    }

    if (checkBoxData != null){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = NotaBoxTheme.colors.selected,
                    uncheckedColor = NotaBoxTheme.colors.unSelected,
                    checkmarkColor = NotaBoxTheme.colors.onSelected
                )
            )

            Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                          if (it.isFocused){
                              if (titleTextState == "Empty Check Box"){
                                  titleTextState = ""
                              }
                          }
                    },
                value = titleTextState,
                onValueChange = { newText -> titleTextState = newText },
                cursorBrush = SolidColor(NotaBoxTheme.colors.iconTint),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = NotaBoxTheme.colors.text
                )
            )
        }
    }

}