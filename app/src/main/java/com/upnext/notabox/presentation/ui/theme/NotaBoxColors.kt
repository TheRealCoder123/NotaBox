package com.upnext.notabox.presentation.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class NotaBoxColors(
    primary: Color,
    iconTint: Color,
    background: Color,
    onBackground: Color,
    onBackgroundText: Color,
    onBackgroundIconTint: Color,
    secondary: Color,
    text: Color,
    description: Color,
    selected: Color,
    onSelected: Color,
    unSelected: Color,
    onUnSelected: Color,
    checkBoxBorder: Color,
    checkBoxChecked: Color,
    onCheckBoxChecked: Color,
    radioButtonSelected: Color,
    radioButtonUnSelected: Color,
    creatingNoteTFTitleText: Color,
    searchTFOuterBackground: Color,
    searchTFBackground: Color,
    onSearchTFBackground: Color,
    searchTFCancelButton: Color,
    dialogBgColor: Color,
    drawerBgColor: Color
) {

    var primary by mutableStateOf(primary)
        private set

    var iconTint by mutableStateOf(iconTint)
        private set

    var onBackground by mutableStateOf(onBackground)
        private set

    var background by mutableStateOf(background)
       private set

    var onBackgroundText by mutableStateOf(onBackgroundText)
        private set

    var onBackgroundIconTint by mutableStateOf(onBackgroundIconTint)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var text by mutableStateOf(text)
        private set

    var description by mutableStateOf(description)
        private set

    var selected by mutableStateOf(selected)
        private set

    var onSelected by mutableStateOf(onSelected)
        private set

    var unSelected by mutableStateOf(unSelected)
        private set

    var onUnSelected by mutableStateOf(onUnSelected)
        private set

    var checkBoxBorder by mutableStateOf(checkBoxBorder)
        private set

    var checkBoxChecked by mutableStateOf(checkBoxChecked)
        private set

    var onCheckBoxChecked by mutableStateOf(onCheckBoxChecked)
        private set

    var radioButtonSelected by mutableStateOf(radioButtonSelected)
        private set

    var radioButtonUnSelected by mutableStateOf(radioButtonUnSelected)
        private set

    var creatingNoteTFTitleText by mutableStateOf(creatingNoteTFTitleText)
        private set

    var searchTFOuterBackground by mutableStateOf(searchTFOuterBackground)
        private set

    var searchTFBackground by mutableStateOf(searchTFBackground)
        private set

    var onSearchTFBackground by mutableStateOf(onSearchTFBackground)
        private set

    var searchTFCancelButton by mutableStateOf(searchTFCancelButton)
        private set


    var dialogBgColor by mutableStateOf(dialogBgColor)
        private set

    var drawerBgColor by mutableStateOf(drawerBgColor)
        private set


    fun copy(
        primary: Color = this.primary,
        iconTint: Color = this.iconTint,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        onBackgroundText: Color = this.onBackgroundText,
        onBackgroundIconTint: Color = this.onBackgroundIconTint,
        secondary: Color = this.secondary,
        text: Color = this.text,
        description: Color = this.description,
        selected: Color = this.selected,
        onSelected: Color = this.onSelected,
        unSelected: Color = this.unSelected,
        onUnSelected: Color = this.onUnSelected,
        checkBoxBorder: Color = this.checkBoxBorder,
        checkBoxChecked: Color = this.checkBoxChecked,
        onCheckBoxChecked: Color = this.onCheckBoxChecked,
        radioButtonSelected: Color = this.radioButtonSelected,
        radioButtonUnSelected: Color = this.radioButtonUnSelected,
        creatingNoteTFTitleText: Color = this.creatingNoteTFTitleText,
        searchTFOuterBackground: Color = this.searchTFOuterBackground,
        searchTFBackground: Color = this.searchTFBackground,
        onSearchTFBackground: Color = this.onSearchTFBackground,
        searchTFCancelButton: Color = this.searchTFCancelButton,
        dialogBgColor: Color = this.dialogBgColor,
        drawerBgColor: Color = this.drawerBgColor
    ) = NotaBoxColors(
        primary = primary,
        iconTint = iconTint,
        background = background,
        onBackground = onBackground,
        onBackgroundText = onBackgroundText,
        onBackgroundIconTint = onBackgroundIconTint,
        secondary = secondary,
        text = text,
        description = description,
        selected = selected,
        onSelected = onSelected,
        unSelected = unSelected,
        onUnSelected = onUnSelected,
        checkBoxBorder = checkBoxBorder,
        checkBoxChecked = checkBoxChecked,
        onCheckBoxChecked = onCheckBoxChecked,
        radioButtonSelected = radioButtonSelected,
        radioButtonUnSelected = radioButtonUnSelected,
        creatingNoteTFTitleText = creatingNoteTFTitleText,
        searchTFOuterBackground = searchTFOuterBackground,
        searchTFBackground = searchTFBackground,
        onSearchTFBackground = onSearchTFBackground,
        searchTFCancelButton = searchTFCancelButton,
        dialogBgColor = dialogBgColor,
        drawerBgColor = drawerBgColor
    )

    fun updateColorsFrom(other: NotaBoxColors) {
        primary = other.primary
        iconTint = other.iconTint
        background = other.background
        onBackground = other.onBackground
        onBackgroundText = other.onBackgroundText
        onBackgroundIconTint = other.onBackgroundIconTint
        secondary = other.secondary
        text = other.text
        description = other.description
        selected = other.selected
        onSelected = other.onSelected
        unSelected = other.unSelected
        onUnSelected = other.onUnSelected
        checkBoxBorder = other.checkBoxBorder
        checkBoxChecked = other.checkBoxChecked
        onCheckBoxChecked = other.onCheckBoxChecked
        radioButtonSelected = other.radioButtonSelected
        radioButtonUnSelected = other.radioButtonUnSelected
        creatingNoteTFTitleText = other.creatingNoteTFTitleText
        searchTFOuterBackground = other.searchTFOuterBackground
        searchTFBackground = other.searchTFBackground
        onSearchTFBackground = other.onSearchTFBackground
        searchTFCancelButton = other.searchTFCancelButton
    }

}