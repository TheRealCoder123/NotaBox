package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upnext.notabox.R
import com.upnext.notabox.domain.audio_player.IAudioPlayer
import com.upnext.notabox.domain.audio_recorder.IAudioRecorder
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme
import java.io.File

@Composable
fun AudioRecordingDialog(
    isShowingDialog: Boolean,
    onDoneRecording: (File) -> Unit,
) {

    if (isShowingDialog){

        val context = LocalContext.current

        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.audio_recording_anim))

        val recorder by lazy {
            IAudioRecorder(context)
        }

        var file: File? = null

        LaunchedEffect(key1 = true) {
            File(context.cacheDir, System.currentTimeMillis().toString() + "_notaBox").also {
                recorder.start(it)
                file = it
            }
        }


        Dialog(
            onDismissRequest = {
                recorder.stop()
                file?.let {
                    onDoneRecording(it)
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(NotaBoxTheme.colors.dialogBgColor)
                    .size(200.dp),
                contentAlignment = Alignment.Center
            ){

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))

                    Text(
                        text = "Audio Recording",
                        color = NotaBoxTheme.colors.text
                    )
                    Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))

                    Box(modifier = Modifier
                        .size(NotaBoxTheme.spaces.veryLarge)
                        .clip(CircleShape)
                        .background(NotaBoxTheme.colors.onBackground)
                        .clickable {
                            recorder.stop()
                            file?.let {
                                  onDoneRecording(it)
                            }
                        },
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = stringResource(R.string.choose_an_image),
                            tint = NotaBoxTheme.colors.onBackgroundIconTint,
                            modifier = Modifier.size(NotaBoxTheme.spaces.large)
                        )
                    }


                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        isPlaying = true,
                        modifier = Modifier
                            .width(100.dp)
                            .height(40.dp)
                    )


                }



            }
        }


    }

}