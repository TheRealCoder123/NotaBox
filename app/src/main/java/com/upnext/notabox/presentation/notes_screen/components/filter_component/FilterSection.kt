package com.upnext.notabox.presentation.notes_screen.components.filter_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme


@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier.testTag(TestTags.FILTER_SECTION_TEST_TAG)
        ) {
            item {
                FilterRadioButton(
                    text = "Title (Alphabetically)",
                    selected = noteOrder is NoteOrder.Title,
                    onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.small))
                FilterRadioButton(
                    text = "Date",
                    selected = noteOrder is NoteOrder.Date,
                    onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.small))
                FilterRadioButton(
                    text = "Priority",
                    selected = noteOrder is NoteOrder.Priority,
                    onSelect = { onOrderChange(NoteOrder.Priority(noteOrder.orderType)) }
                )
            }
        }
        LazyRow(
            modifier = Modifier
        ) {
            item {
                FilterRadioButton(
                    text = "Ascending",
                    selected = noteOrder.orderType is OrderType.Ascending,
                    onSelect = {
                        onOrderChange(noteOrder.copy(OrderType.Ascending))
                    }
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.small))
                FilterRadioButton(
                    text = "Descending",
                    selected = noteOrder.orderType is OrderType.Descending,
                    onSelect = {
                        onOrderChange(noteOrder.copy(OrderType.Descending))
                    }
                )
            }
        }
    }
}

