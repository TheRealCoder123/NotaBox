package com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavDrawerItem(
    val icon: ImageVector,
    val title: String,
    val type: NavDrawerItemType
)


fun drawerItems() : List<NavDrawerItem> {
    return listOf(
        NavDrawerItem(
            icon = Icons.Default.Notes,
            "Notes",
            NavDrawerItemType.Notes
        ),
//        NavDrawerItem(
//            icon = Icons.Default.Checklist,
//            "To-Dos",
//            NavDrawerItemType.TO_DOS
//        ),
        NavDrawerItem(
            icon = Icons.Default.Folder,
            "Folders",
            NavDrawerItemType.Folders
        ),
        NavDrawerItem(
            icon = Icons.Default.Settings,
            "Settings",
            NavDrawerItemType.Settings
        )
    )
}
