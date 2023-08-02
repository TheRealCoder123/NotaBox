package com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upnext.notabox.R
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun DrawerHeader() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(NotaBoxTheme.spaces.large),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.dark_mode_icon) else painterResource(id = R.drawable.light_mode_icon),
                contentDescription = stringResource(R.string.app_logo)
            )

            Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.mediumLarge))

            Text(
                text = stringResource(id = R.string.app_name),
                style = NotaBoxTheme.typography.title,
                fontSize = 32.sp,
                color = NotaBoxTheme.colors.text
            )

        }
    }

}

@Preview
@Composable
fun DrawerHeaderPreview() {
    DrawerHeader()
}