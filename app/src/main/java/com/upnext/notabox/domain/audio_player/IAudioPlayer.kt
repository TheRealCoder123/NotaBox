package com.upnext.notabox.domain.audio_player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import java.io.File

class IAudioPlayer(private var context: Context) : AudioPlayer {

    private var player: MediaPlayer? = null

    val playing = mutableStateOf(false)

    override fun play(fileUri: Uri) {
        MediaPlayer.create(context, fileUri).apply {
            player = this
            start()
            playing.value = true
            setOnCompletionListener {
                playing.value = false
            }
        }
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
        player?.release()
        playing.value = false
        player = null
    }
}