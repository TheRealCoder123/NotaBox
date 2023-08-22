package com.upnext.notabox.presentation.notes_screen

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.upnext.notabox.common.Constants
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.common.rememberWindowInfo
import com.upnext.notabox.presentation.activities.CreateViewNotesActivity.NoteActivity
import com.upnext.notabox.presentation.activities.MainActivity.components.FAB.CreateNoteFab
import com.upnext.notabox.presentation.global_components.SearchTextField
import com.upnext.notabox.presentation.navigation.MainNavigationRoutes
import com.upnext.notabox.presentation.notes_screen.components.filter_component.FilterSection
import com.upnext.notabox.presentation.notes_screen.components.NoteItem
import com.upnext.notabox.presentation.notes_screen.components.TopFolderItem
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = notesViewModel.state.value

    val lazyColumnState = rememberLazyListState()
    val windowInfo = rememberWindowInfo()

    val context = LocalContext.current

    val size by animateDpAsState(
        targetValue = if(state.isOrderSectionVisible) 100.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = notesViewModel.searchTextFieldState.value) {
        if (notesViewModel.searchTextFieldState.value.isNotEmpty()){
            notesViewModel.onEvent(NotesEvent.Search(state.noteOrder, notesViewModel.searchTextFieldState.value))
        }else{
            notesViewModel.onEvent(NotesEvent.GetNotes(state.noteOrder))
        }
    }

    Scaffold(
        backgroundColor = NotaBoxTheme.colors.background,
        floatingActionButton = {
            CreateNoteFab {
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(Constants.NOTE_ID_TO_CREATE_NOTE_SCREEN, Constants.CREATE_NOTE_PARAM_PASSED_TO_CREATE_NOTE_SCREEN)
                intent.putExtra(
                    Constants.FOLDER_ID_TO_CREATE_NOTE_SCREEN_IF_IS_CURRENTLY_FOLDER_SELECTED,
                    notesViewModel.selectedFolder?.id ?: Constants.ADD_NOTE_TO_FOLDER_PARAM_PASSED_TO_CREATE_NOTE_SCREEN_IS_MINUS_ONE
                )
                context.startActivity(intent)
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .testTag(TestTags.NOTES_SCREEN_TEST_TAG)
                .fillMaxSize(),
            state = lazyColumnState,
            contentPadding = PaddingValues(bottom = 70.dp)
        ) {


            item {

                SearchTextField(
                    value = notesViewModel.searchTextFieldState.value,
                    onValueChange = { query ->
                        notesViewModel.searchTextFieldState.value = query
                    },
                    onCloseClick = {
                        notesViewModel.searchTextFieldState.value = ""
                    },
                    onFilterClicked = {
                        notesViewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                    isShowFiltersToggled = state.isOrderSectionVisible
                )

                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    modifier = Modifier.fillMaxWidth(),
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut(),
                ) {
                    FilterSection(
                        noteOrder = state.noteOrder,
                        onOrderChange = {
                            notesViewModel.onEvent(NotesEvent.Order(it))
                        },
                        modifier = Modifier.height(size)
                    )
                }

                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(horizontal = NotaBoxTheme.spaces.medium)
                ) {
                    item {
                        TopFolderItem(
                            folder = null,
                            isAllShown = true,
                            isSelected = notesViewModel.selectedFolder == null,
                            onClick = {
                                notesViewModel.selectedFolder = null
                                if (notesViewModel.searchTextFieldState.value.isNotEmpty()){
                                    notesViewModel.onEvent(NotesEvent.Search(state.noteOrder, notesViewModel.searchTextFieldState.value))
                                }else{
                                    notesViewModel.onEvent(NotesEvent.GetNotes(state.noteOrder))
                                }
                            }
                        )
                    }
                    items(state.folders){
                        Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                        TopFolderItem(
                            folder = it,
                            isSelected = notesViewModel.selectedFolder?.id == it.id,
                            onClick = { folder ->
                                notesViewModel.selectedFolder = folder
                                if (notesViewModel.searchTextFieldState.value.isNotEmpty()){
                                    notesViewModel.onEvent(NotesEvent.Search(state.noteOrder, notesViewModel.searchTextFieldState.value))
                                }else{
                                    notesViewModel.onEvent(NotesEvent.GetNotes(state.noteOrder))
                                }
                            }
                        )
                    }
                    if (!windowInfo.isTablet){
                        item{
                            Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                            TopFolderItem(
                                folder = null,
                                isAllShown = false,
                                isSelected = false,
                                isAllFoldersBtnShown = true,
                                onClick = {
                                    navController.navigate(MainNavigationRoutes.FoldersScreenRoute.route)
                                }
                            )
                        }
                    }
                }
            }


            items(state.notes){
                NoteItem(note = it){ noteListItemData ->
                    val intent = Intent(context, NoteActivity::class.java)
                    intent.putExtra(Constants.NOTE_ID_TO_CREATE_NOTE_SCREEN, noteListItemData.id)
                    intent.putExtra(Constants.FOLDER_ID_TO_CREATE_NOTE_SCREEN_IF_IS_CURRENTLY_FOLDER_SELECTED, Constants.ADD_NOTE_TO_FOLDER_PARAM_PASSED_TO_CREATE_NOTE_SCREEN_IS_MINUS_ONE)
                    context.startActivity(intent)
                }
            }

        }

    }

    if (state.notes.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(NotaBoxTheme.spaces.large),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "We found no notes saved, if you want to create a note click on the plus button",
                color = Color.Gray,
                style = NotaBoxTheme.typography.title,
                textAlign = TextAlign.Center
            )
        }
    }



}