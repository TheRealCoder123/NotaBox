package com.upnext.notabox.presentation.global_components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upnext.notabox.R
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.domain.enums.Keyboard
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    onFilterClicked: () -> Unit = {},
    isShowFiltersToggled: Boolean = true
) {

    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }

    val keyboardState by keyboardAsState()

    LaunchedEffect(key1 = keyboardState, block = {
        isFocused = keyboardState == Keyboard.Opened
    })

    val transition = updateTransition(
        targetState = isFocused,
        label = "is_focused_transition"
    )

    val reduceCornerRadius by transition.animateDp(
        label = "reduceCornerRadius",
        targetValueByState = {
            if (it) 0.dp else NotaBoxTheme.spaces.extraLarge
        },
        transitionSpec = {
            tween(durationMillis = 600)
        }
    )
    val reduceSidePadding by transition.animateDp(
        targetValueByState = {
            if (it) 0.dp else NotaBoxTheme.spaces.mediumLarge
        },
        label = "reduceSidePadding",
        transitionSpec = {
            tween(durationMillis = 900)
        }
    )

    val verticalPadding by transition.animateDp(
        label = "verticalPadding",
        targetValueByState = {
            if (it) NotaBoxTheme.spaces.mediumLarge else NotaBoxTheme.spaces.medium
        },
        transitionSpec = {
            tween(durationMillis = 900)
        }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = reduceSidePadding,
                end = reduceSidePadding,
                top = reduceSidePadding,
                bottom = NotaBoxTheme.spaces.mediumLarge
            )
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(reduceCornerRadius))
                .background(NotaBoxTheme.colors.searchTFBackground)
                .padding(
                    vertical = verticalPadding,
                    horizontal = NotaBoxTheme.spaces.mediumLarge
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    tint = NotaBoxTheme.colors.onSearchTFBackground
                )
                Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                BasicTextField(
                    modifier = Modifier
                        .testTag(TestTags.SEARCH_TEXT_FIELD_TEST_TAG),
                    value = value,
                    onValueChange = { onValueChange(it) },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = NotaBoxTheme.colors.text
                    ),
                    decorationBox = { innerTextField ->
                        Box {
                            if (value.isEmpty()) {
                                Text(
                                    text = "Search",
                                    color = NotaBoxTheme.colors.onSearchTFBackground,
                                    modifier = Modifier.testTag(TestTags.SEARCH_TEXT_HINT_TEST_TAG)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close),
                        tint = NotaBoxTheme.colors.onSearchTFBackground,
                        modifier = Modifier.clickable {
                            onCloseClick()
                        }
                    )
                    Spacer(modifier = Modifier.width(NotaBoxTheme.spaces.medium))
                }
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = stringResource(id = R.string.filter),
                    tint = if (isShowFiltersToggled) NotaBoxTheme.colors.selected else NotaBoxTheme.colors.onSearchTFBackground,
                    modifier = Modifier
                        .clickable {
                            onFilterClicked()
                        }
                        .semantics {
                            testTag = TestTags.FILTER_ICON_TEST_TAG
                        }
                )
            }
        }

    }


}


@Composable
@Preview
fun SearchTextFieldPreviewLight() {
    SearchTextField(value = "", onValueChange = {})
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SearchTextFieldPreviewDark() {
    SearchTextField(value = "Search....", onValueChange = {})
}