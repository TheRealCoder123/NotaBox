package com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar

import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarActionItem(
    val icon: ImageVector,
    val contentDescription: String,
    val type: TopBarActionType
)
