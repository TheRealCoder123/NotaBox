package com.upnext.notabox.presentation.priorities_sceen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun PrioritiseScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(
            text = "Priorities Screen",
            color = NotaBoxTheme.colors.text,
            modifier = Modifier.testTag(TestTags.PRIORITIES_SCREEN_TEST_TAG)
        )
    }
}