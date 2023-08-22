package com.upnext.notabox.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upnext.notabox.data.enitities.Priority
import kotlinx.coroutines.flow.Flow

interface PriorityRepository {
    suspend fun insertPriority(priority: Priority)
    fun getPriorities() : Flow<List<Priority>>
    fun getPriorityFromId(id: Int) : Flow<List<Priority>>
}