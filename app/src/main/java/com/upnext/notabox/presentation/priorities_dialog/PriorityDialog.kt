package com.upnext.notabox.presentation.priorities_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun PriorityDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit
) {

    val priorityViewModel = hiltViewModel<PriorityViewModel>()
    val state = priorityViewModel.state.value


    if (isVisible){
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NotaBoxTheme.colors.dialogBgColor)
                    .padding(NotaBoxTheme.spaces.mediumLarge)
            ) {

                item {
                    Text(
                        text = "Select A Priority",
                        color = NotaBoxTheme.colors.text,
                        style = NotaBoxTheme.typography.title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.mediumLarge))
                }

                if (state.priorities.isEmpty()){
                    item {
                        Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.large))
                        Text(
                            text = "Seems like you dont have any priorities.",
                            color = Color.Gray,
                            style = NotaBoxTheme.typography.title,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = NotaBoxTheme.spaces.large),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.large))
                    }
                }

                items(state.priorities){

                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(
                            Modifier
                                .clip(RoundedCornerShape(NotaBoxTheme.spaces.veryLarge))
                                .background(NotaBoxTheme.colors.onBackground)
                                .padding(
                                    horizontal = NotaBoxTheme.spaces.mediumLarge,
                                    vertical = NotaBoxTheme.spaces.medium
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){

                            Text(
                                text = "Create New",
                                color = NotaBoxTheme.colors.onBackgroundText
                            )

                            Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                            
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Create new priority",
                                tint = NotaBoxTheme.colors.onBackgroundIconTint,
                                modifier = Modifier.size(NotaBoxTheme.spaces.mediumLarge)
                            )

                        }
                    }
                }

            }
        }
    }
}