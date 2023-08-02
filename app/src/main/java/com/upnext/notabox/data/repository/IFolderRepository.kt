package com.upnext.notabox.data.repository

import com.upnext.notabox.data.data_source.DataAccessObject
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.model.CreateFolder
import com.upnext.notabox.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow

class IFolderRepository (private val dao: DataAccessObject) : FolderRepository {
    override suspend fun createFolder(createParams: CreateFolder) {
        dao.insertFolder(Folder(0, createParams.folderName, createParams.isPinned))
    }

    override fun getAllFolders(): Flow<List<Folder>> {
        return dao.getFolders()
    }

    override suspend fun updateFolder(updatedFolder: Folder) {
        dao.updateFolder(updatedFolder.folderName, updatedFolder.isPinned, updatedFolder.id)
    }

    override suspend fun deleteFolder(folderId: Int) {
        dao.deleteFolder(folderId)
    }
}