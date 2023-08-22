package com.upnext.notabox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.upnext.notabox.common.Constants
import com.upnext.notabox.presentation.SettingsScreen.SettingsScreen
import com.upnext.notabox.presentation.activities.MainActivity.components.MainViewModel
import com.upnext.notabox.presentation.folders_screen.FolderViewModel
import com.upnext.notabox.presentation.folders_screen.FoldersScreen
import com.upnext.notabox.presentation.notes_screen.NotesScreen

@Composable
fun MainNavigationGraph(
    navHostController: NavHostController,
    folderVm: FolderViewModel,
    viewModel: MainViewModel
){
    NavHost(
        navController = navHostController,
        startDestination = MainNavigationRoutes.NotesScreenRoute.route
    ) {
        composable(MainNavigationRoutes.NotesScreenRoute.route){
            NotesScreen(navController = navHostController)
        }
        composable(MainNavigationRoutes.FoldersScreenRoute.route){
            FoldersScreen(
                onEvent = folderVm::onEvent,
                state = folderVm.state.value
            )
        }
        composable(MainNavigationRoutes.SettingsScreenRoute.route){
            SettingsScreen()
        }
    }
}