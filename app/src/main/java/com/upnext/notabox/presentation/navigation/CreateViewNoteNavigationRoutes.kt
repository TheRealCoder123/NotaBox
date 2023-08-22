package com.upnext.notabox.presentation.navigation

sealed class CreateViewNoteNavigationRoutes(val route: String) {
    object CreateViewNoteScreen: CreateViewNoteNavigationRoutes("create_view_screen")
}
