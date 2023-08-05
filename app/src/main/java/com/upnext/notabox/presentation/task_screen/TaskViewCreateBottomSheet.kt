package com.upnext.notabox.presentation.task_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.upnext.notabox.data.enitities.Task
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun TaskViewCreateBottomSheet(
    task: Task?
) {

    var titleTaskState by rememberSaveable {
        mutableStateOf(task?.taskTitle ?: "")
    }

    var isTaskChecked by remember {
        mutableStateOf(task?.done ?: false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicTextField(
                        value = titleTaskState,
                        onValueChange = { titleTaskState = it },
                        decorationBox = {
                            Box {
                                if (titleTaskState.isEmpty()) {
                                    Text(
                                        text = "No Title",
                                        color = Color.Gray
                                    )
                                }
                            }
                            it()
                        },
                        textStyle = TextStyle(
                            color = NotaBoxTheme.colors.text
                        ),
                        singleLine = true
                    )
                },
                backgroundColor = NotaBoxTheme.colors.dialogBgColor,
                actions = {
                    Checkbox(
                        checked = isTaskChecked,
                        onCheckedChange = { isTaskChecked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = NotaBoxTheme.colors.onBackground,
                            uncheckedColor = NotaBoxTheme.colors.unSelected,
                            checkmarkColor = NotaBoxTheme.colors.onBackgroundIconTint
                        )
                    )
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.Archive,
                            contentDescription = "Archive",
                            tint = NotaBoxTheme.colors.iconTint
                        )
                    }
                }
            )
        },
        backgroundColor = NotaBoxTheme.colors.dialogBgColor,
        floatingActionButton = {
            if (task == null){
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(NotaBoxTheme.spaces.extraLarge),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = NotaBoxTheme.colors.onBackground
                    )
                ) {
                    Text(
                        text = "Create",
                        color = NotaBoxTheme.colors.onBackgroundText
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

        }
    }

}