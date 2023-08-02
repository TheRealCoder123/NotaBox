package com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.upnext.notabox.R
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    items: List<TopBarActionItem> = emptyList(),
    onActionClicked: (TopBarActionItem) -> Unit = {},
    onNavigationClicked: () -> Unit = {},
    title: String = "NotaBox",
    showMenu: Boolean = true,
    navIcon: ImageVector = Icons.Default.Menu,
) {

    TopAppBar(
        modifier = modifier,
        title ={
            Text(
                text = title,
                color = NotaBoxTheme.colors.text,
                style = NotaBoxTheme.typography.title
            )
        },
        navigationIcon = {
           if (showMenu){
               IconButton(onClick = { onNavigationClicked() }) {
                   Icon(
                       imageVector = navIcon,
                       contentDescription = stringResource(R.string.nav_icon),
                       tint = NotaBoxTheme.colors.iconTint
                   )
               }
           }
        },
        actions = {
            items.forEach {
                IconButton(onClick = { onActionClicked(it) }) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription,
                        tint = NotaBoxTheme.colors.iconTint
                    )
                }
            }
        },
        backgroundColor = NotaBoxTheme.colors.background
    )

}