package com.upnext.notabox.presentation.priorities_dialog

import com.upnext.notabox.data.enitities.Priority

sealed class OnPriorityEvent {
    data class CreatePriority(val priority: Priority) : OnPriorityEvent()
    data class GetNoteFromId(val id: Int) : OnPriorityEvent()
}
