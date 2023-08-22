package com.upnext.notabox.data.repository

import com.upnext.notabox.data.data_source.DataAccessObject
import com.upnext.notabox.data.enitities.Priority
import com.upnext.notabox.domain.repository.PriorityRepository
import kotlinx.coroutines.flow.Flow

class IPriorityRepository(
    private val dao: DataAccessObject
) : PriorityRepository {

    override suspend fun insertPriority(priority: Priority) {
        dao.insertPriority(priority)
    }

    override fun getPriorities(): Flow<List<Priority>> {
        return dao.getPriorities()
    }

    override fun getPriorityFromId(id: Int): Flow<List<Priority>> {
        return dao.getPriorityFromId(id)
    }

}