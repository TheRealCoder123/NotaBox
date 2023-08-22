package com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.common.WindowInfo
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun DrawerBody(
    onClick: (NavDrawerItemType) -> Unit,
    windowInfo: WindowInfo
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(NotaBoxTheme.spaces.large)
            .testTag(TestTags.NAV_DRAWER_TEST_TAG)
    ){

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        onClick(NavDrawerItemType.Notes)
                    }
                    .padding(vertical = NotaBoxTheme.spaces.medium)
            ) {

                Icon(
                    imageVector = Icons.Default.Notes,
                    contentDescription = "Notes",
                    tint = NotaBoxTheme.colors.text
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.large))
                Text(
                    text = "Notes",
                    color = NotaBoxTheme.colors.text,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .testTag("Notes")
                )

            }

            Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))

            if (!windowInfo.isTablet){
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            onClick(NavDrawerItemType.Folders)
                        }
                        .padding(vertical = NotaBoxTheme.spaces.medium)
                ) {

                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Folders",
                        tint = NotaBoxTheme.colors.text
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.large))
                    Text(
                        text = "Folders",
                        color = NotaBoxTheme.colors.text,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .testTag("Folders")
                    )

                }
            }

            Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        onClick(NavDrawerItemType.Settings)
                    }
                    .padding(vertical = NotaBoxTheme.spaces.medium)
            ) {

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = NotaBoxTheme.colors.text
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.large))
                Text(
                    text = "Settings",
                    color = NotaBoxTheme.colors.text,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .testTag("Settings")
                )

            }

            Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))

        }


    }

}