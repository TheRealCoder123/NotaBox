package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upnext.notabox.R
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
@Deprecated("Not in use, instead we use a dialog")
fun RecordingBottomMenu(
    onFinished: () -> Unit
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.audio_recording_anim))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(NotaBoxTheme.spaces.veryLarge)
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.mediumLarge))

        IconButton(onClick = { onFinished() }) {
            Icon(
                imageVector = Icons.Default.Stop,
                contentDescription = stringResource(R.string.choose_an_image),
                tint = NotaBoxTheme.colors.iconTint
            )
        }
        
        Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))

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