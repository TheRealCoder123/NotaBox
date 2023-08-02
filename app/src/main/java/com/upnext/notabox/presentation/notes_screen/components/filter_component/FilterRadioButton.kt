package com.upnext.notabox.presentation.notes_screen.components.filter_component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

class FilterRadioButton {
}

@Composable
fun FilterRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = NotaBoxTheme.colors.selected,
                unselectedColor = NotaBoxTheme.colors.unSelected
            ),
            modifier = Modifier.semantics {
                contentDescription = text
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = NotaBoxTheme.colors.text)
    }
}