package com.upnext.notabox.domain.audio_player

import android.net.Uri
import java.io.File

interface AudioPlayer {
    fun play(fileUri: Uri)
    fun pause()
    fun stop()
}