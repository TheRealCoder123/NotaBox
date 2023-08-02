package com.upnext.notabox.presentation.notes_screen

import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.model.NoteListItemData
import com.upnext.notabox.presentation.notes_screen.components.filter_component.NoteOrder
import com.upnext.notabox.presentation.notes_screen.components.filter_component.OrderType

data class NotesState(
    val notes: List<NoteListItemData> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val folders: List<Folder> = emptyList(),
    val isOrderSectionVisible: Boolean = false
)