package com.upnext.notabox.domain.enums

enum class OnFolderClickSendResultTO {
    VIEW_NOTE_SCREEN, NOTES_SCREEN
}

fun decodeOnFolderResultEnum(value: String?) : OnFolderClickSendResultTO {
    return when(value){
        "VIEW_NOTE_SCREEN" -> OnFolderClickSendResultTO.VIEW_NOTE_SCREEN
        "NOTES_SCREEN" -> OnFolderClickSendResultTO.VIEW_NOTE_SCREEN
        else -> throw IllegalArgumentException()
    }
}