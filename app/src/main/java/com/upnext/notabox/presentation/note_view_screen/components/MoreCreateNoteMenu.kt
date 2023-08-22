package com.upnext.notabox.presentation.note_view_screen.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun MoreCreateNoteMenu(
    onDeleteNoteClicked: () -> Unit,
    onAddToFolder: () -> Unit,
    onAddPriorityToNote: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(NotaBoxTheme.spaces.medium)
        .background(NotaBoxTheme.colors.dialogBgColor)
    ){
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            item {
                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))
                Text(
                    text = "More Options",
                    color = NotaBoxTheme.colors.text,
                    style = NotaBoxTheme.typography.title,
                )
                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))
//                Not in first version
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
//                        .background(NotaBoxTheme.colors.searchTFOuterBackground)
//                        .padding(NotaBoxTheme.spaces.mediumLarge),
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Map,
//                        contentDescription = "Create Mind Map",
//                        tint = NotaBoxTheme.colors.iconTint
//                    )
//                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.mediumLarge))
//                    Text(
//                        text = "Create mind map",
//                        color = NotaBoxTheme.colors.text
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))
//                Not in first version
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
//                        .background(NotaBoxTheme.colors.searchTFOuterBackground)
//                        .padding(NotaBoxTheme.spaces.mediumLarge),
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Print,
//                        contentDescription = "Print Note",
//                        tint = NotaBoxTheme.colors.iconTint
//                    )
//                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.mediumLarge))
//                    Text(
//                        text = "Print",
//                        color = NotaBoxTheme.colors.text
//                    )
//                }

                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                        .background(NotaBoxTheme.colors.searchTFOuterBackground)
                        .padding(NotaBoxTheme.spaces.mediumLarge)
                        .clickable {
                            onAddPriorityToNote()
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PriorityHigh,
                        contentDescription = "Add a priority",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.mediumLarge))
                    Text(
                        text = "Add a priority",
                        color = NotaBoxTheme.colors.text
                    )
                }


                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                        .background(NotaBoxTheme.colors.searchTFOuterBackground)
                        .padding(NotaBoxTheme.spaces.mediumLarge)
                        .clickable {
                            onAddToFolder()
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Add to folder",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.mediumLarge))
                    Text(
                        text = "Add to folder",
                        color = NotaBoxTheme.colors.text
                    )
                }

                Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.medium))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(NotaBoxTheme.spaces.medium))
                        .background(NotaBoxTheme.colors.searchTFOuterBackground)
                        .padding(NotaBoxTheme.spaces.mediumLarge)
                        .clickable {
                            onDeleteNoteClicked()
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = "Delete Note",
                        tint = NotaBoxTheme.colors.iconTint
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.mediumLarge))
                    Text(
                        text = "Delete Note",
                        color = NotaBoxTheme.colors.text
                    )
                }



            }



        }
    }
}