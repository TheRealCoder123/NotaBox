package com.upnext.notabox.presentation.notes_screen.components.filter_component

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}