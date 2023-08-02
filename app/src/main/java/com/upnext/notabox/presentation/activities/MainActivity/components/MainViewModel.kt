package com.upnext.notabox.presentation.activities.MainActivity.components

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.NavDrawerItem
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.drawerItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val selectedDrawerItem = mutableStateOf(drawerItems()[0].type)

}