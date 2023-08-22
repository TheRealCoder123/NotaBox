package com.upnext.notabox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.upnext.notabox.common.Constants
import com.upnext.notabox.presentation.note_view_screen.CreateNoteScreen

@Composable
fun CreateViewNavigationGraph(
    navHostController: NavHostController,
    onBackPressed: () -> Unit,
    noteId: String?,
    folderId: Int
){
    NavHost(
        navController = navHostController,
        startDestination = CreateViewNoteNavigationRoutes.CreateViewNoteScreen.route
    ) {
        composable(CreateViewNoteNavigationRoutes.CreateViewNoteScreen.route){
            CreateNoteScreen(
                navController = navHostController,
                onBackPressed =  {
                    onBackPressed()
                },
                noteId = noteId,
                folderId = folderId
            )
        }
    }
}