package com.upnext.notabox.domain.model

import com.upnext.notabox.domain.enums.TaskAttachmentFileType

data class TaskAttachment(
    val fileUri: String,
    val type: TaskAttachmentFileType,
    val name: String,
    val mimeType: String
)
