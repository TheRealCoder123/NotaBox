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
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun DrawerBody(
    items: List<NavDrawerItem>,
    selectedItem: NavDrawerItemType,
    onClick: (NavDrawerItem) -> Unit
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


            items.forEach {item->

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            onClick(item)
                        }
                        .padding(vertical = NotaBoxTheme.spaces.medium)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = NotaBoxTheme.colors.text//if (selectedItem == item.type) NotaBoxTheme.colors.selected else NotaBoxTheme.colors.unSelected
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.large))
                    Text(
                        text = item.title,
                        color = NotaBoxTheme.colors.text,//if (selectedItem == item.type) NotaBoxTheme.colors.selected else NotaBoxTheme.colors.unSelected,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .testTag(item.type.name)
                    )
                }

                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))

            }
        }


    }

}