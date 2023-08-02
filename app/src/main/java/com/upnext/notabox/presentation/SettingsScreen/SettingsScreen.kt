package com.upnext.notabox.presentation.SettingsScreen

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
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(
            text = "Settings Screen",
            color = NotaBoxTheme.colors.text,
            modifier = Modifier.testTag(TestTags.SETTINGS_SCREEN_TEST_TAG)
        )
    }
}