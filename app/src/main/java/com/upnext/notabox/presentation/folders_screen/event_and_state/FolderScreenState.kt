package com.upnext.notabox.presentation.folders_screen.event_and_state

import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.enums.OnFolderClickSendResultTO

data class FolderScreenState(
    val folders: List<Folder> = emptyList(),
    val currentlyEditingFolder: Folder? = null,
)
