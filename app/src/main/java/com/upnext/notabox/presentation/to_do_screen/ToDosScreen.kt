package com.upnext.notabox.presentation.to_do_screen

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
fun ToDosScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(
            text = "To-Dos Screen",
            color = NotaBoxTheme.colors.text,
            modifier = Modifier.testTag(TestTags.ToDos_SCREEN_TEST_TAG)
        )
    }
}