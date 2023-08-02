package com.upnext.notabox.domain.use_case.note_use_case

import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.domain.repository.NoteRepository
import com.upnext.notabox.presentation.notes_screen.components.filter_component.NoteOrder
import com.upnext.notabox.presentation.notes_screen.components.filter_component.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Priority -> notes.sortedBy { it.priority?.priorityLevel }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Priority -> notes.sortedByDescending { it.priority?.priorityLevel }
                    }
                }
            }


        }
    }
}