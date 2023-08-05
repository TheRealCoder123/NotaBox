package com.upnext.notabox.presentation.task_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upnext.notabox.R
import com.upnext.notabox.common.DateFormatter
import com.upnext.notabox.presentation.activities.MainActivity.components.FAB.CreateNoteFab
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TasksScreen(
    taskViewModel: TaskViewModel = hiltViewModel(),
) {



    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )


    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            TaskViewCreateBottomSheet(
                task = taskViewModel.selectedTask
            )
        },
        sheetBackgroundColor = NotaBoxTheme.colors.dialogBgColor
    ){

        Scaffold(
            floatingActionButton = {
                CreateNoteFab {
                    taskViewModel.selectedTask = null
                    coroutineScope.launch {
                        modalSheetState.show()
                    }
                }
            }
        ) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(NotaBoxTheme.spaces.medium)
                    ) {
                        Text(
                            text = stringResource(R.string.today_s_tasks),
                            style = NotaBoxTheme.typography.title,
                            fontSize = 25.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            color = NotaBoxTheme.colors.text
                        )

                        Text(
                            text = DateFormatter.formatDateFromMillis(System.currentTimeMillis(), "EEEE - yyyy MMMM d"),
                            style = NotaBoxTheme.typography.description,
                            fontSize = 17.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            color = Color.Gray
                        )
                    }

                }



            }

        }


    }





}

