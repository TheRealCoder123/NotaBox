package com.upnext.notabox.presentation.folders_screen.event_and_state

import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.model.CreateFolder

sealed class FolderEvent {
    data class CreateFolderEvent(val params: CreateFolder) : FolderEvent()
    data class SetCurrentlyEditingFolder(val folder: Folder?) : FolderEvent()
    data class UpdateFolder(val updateFolder: Folder) : FolderEvent()
    data class DeleteFolder(val folderId: Int) : FolderEvent()
    object GetFolders : FolderEvent()
}
