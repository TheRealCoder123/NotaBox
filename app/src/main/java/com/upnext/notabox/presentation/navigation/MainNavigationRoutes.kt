package com.upnext.notabox.presentation.navigation

sealed class MainNavigationRoutes(val route: String) {
    object NotesScreenRoute: MainNavigationRoutes("notes_screen_route")
    //object TasksScreenRoute: MainNavigationRoutes("tasks_screen_route")
    object FoldersScreenRoute: MainNavigationRoutes("folder_screen_route")
    object SettingsScreenRoute: MainNavigationRoutes("settings_screen_route")
}
