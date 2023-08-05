package com.upnext.notabox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.upnext.notabox.common.Constants
import com.upnext.notabox.presentation.SettingsScreen.SettingsScreen
import com.upnext.notabox.presentation.folders_screen.FolderViewModel
import com.upnext.notabox.presentation.note_view_screen.CreateNoteScreen
import com.upnext.notabox.presentation.folders_screen.FoldersScreen
import com.upnext.notabox.presentation.notes_screen.NotesScreen
import com.upnext.notabox.presentation.priorities_sceen.PrioritiseScreen
import com.upnext.notabox.presentation.task_screen.TasksScreen

@Composable
fun MainNavigationGraph(navHostController: NavHostController){
    NavHost(
        navController = navHostController,
        startDestination = MainNavigationRoutes.NotesScreenRoute.route
    ) {
        composable(MainNavigationRoutes.NotesScreenRoute.route){
            NotesScreen(navController = navHostController)
        }
        composable(MainNavigationRoutes.TasksScreenRoute.route){
            TasksScreen()
        }
        composable(MainNavigationRoutes.PrioritiesScreenRoute.route){
            PrioritiseScreen()
        }
        composable(MainNavigationRoutes.FoldersScreenRoute.route){
            val viewModel = hiltViewModel<FolderViewModel>()
            FoldersScreen(
                onEvent = viewModel::onEvent,
                navHostController,
                state = viewModel.state.value
            )
        }
        composable(MainNavigationRoutes.SettingsScreenRoute.route){
            SettingsScreen()
        }
        composable(MainNavigationRoutes.CreateNoteScreenRoute.route + "/{${Constants.NOTE_ID_TO_CREATE_NOTE_SCREEN}}" + "/{${Constants.FOLDER_ID_TO_CREATE_NOTE_SCREEN_IF_IS_CURRENTLY_FOLDER_SELECTED}}"){
            CreateNoteScreen(navController = navHostController)
        }
    }
}