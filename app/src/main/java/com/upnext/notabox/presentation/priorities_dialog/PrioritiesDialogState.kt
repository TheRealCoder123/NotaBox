package com.upnext.notabox.presentation.priorities_dialog

import com.upnext.notabox.data.enitities.Priority

data class PrioritiesDialogState(
    val priorities: List<Priority> = emptyList()
)