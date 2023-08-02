package com.upnext.notabox.domain.audio_recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}