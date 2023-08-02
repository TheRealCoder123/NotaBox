package com.upnext.notabox.domain.repository

import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.model.CreateFolder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(createParams: CreateFolder)
    fun getAllFolders() : Flow<List<Folder>>
    suspend fun updateFolder(updatedFolder: Folder)
    suspend fun deleteFolder(folderId: Int)
}