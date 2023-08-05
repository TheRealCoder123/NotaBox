 package com.upnext.notabox.presentation.activities.MainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.upnext.notabox.R
import com.upnext.notabox.common.Constants
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.presentation.activities.MainActivity.components.MainViewModel
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.DrawerBody
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.DrawerHeader
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.NavDrawerItemType
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.drawerItems
import com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar.MainTopBar
import com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar.TopBarActionItem
import com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar.TopBarActionType
import com.upnext.notabox.presentation.navigation.MainNavigationGraph
import com.upnext.notabox.presentation.navigation.MainNavigationRoutes
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

 @AndroidEntryPoint
 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotaBoxTheme {
                NotaBoxApp()
            }
        }
    }
}

 @Composable
 fun NotaBoxApp() {

     val context = LocalContext.current

     val viewModel : MainViewModel = hiltViewModel()

     val scaffoldState = rememberScaffoldState()
     val scope = rememberCoroutineScope()
     val navController = rememberNavController()

     val currentDestination = navController.currentBackStackEntryAsState().value?.destination

     when(currentDestination?.route){
         MainNavigationRoutes.NotesScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Notes
         MainNavigationRoutes.TasksScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.TO_DOS
         MainNavigationRoutes.PrioritiesScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Priorities
         MainNavigationRoutes.FoldersScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Folders
         MainNavigationRoutes.SettingsScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Settings
     }


     Scaffold(
         backgroundColor = NotaBoxTheme.colors.background,
         scaffoldState = scaffoldState,
         topBar = {
             if (currentDestination?.route == MainNavigationRoutes.CreateNoteScreenRoute.route + "/{${Constants.NOTE_ID_TO_CREATE_NOTE_SCREEN}}" + "/{${Constants.FOLDER_ID_TO_CREATE_NOTE_SCREEN_IF_IS_CURRENTLY_FOLDER_SELECTED}}"){
                 MainTopBar(
                     onNavigationClicked = {
                         navController.navigateUp()
                     },
                     navIcon = Icons.Default.ArrowBackIos,
                     title = "Create Note",
                     modifier = Modifier.testTag(TestTags.CREATE_NOTE_TOOL_BAR_TEST_TAG)
                 )
             }else {
                 when(viewModel.selectedDrawerItem.value){
                     NavDrawerItemType.Notes, NavDrawerItemType.TO_DOS -> {
                         MainTopBar(
                             items = listOf(
                                 TopBarActionItem(
                                     icon = Icons.Default.Settings,
                                     context.getString(R.string.settings),
                                     TopBarActionType.Settings
                                 )
                             ),
                             onActionClicked = {
                                 if (it.type == TopBarActionType.Settings){

                                 }
                             },
                             onNavigationClicked = {
                                 scope.launch {
                                     scaffoldState.drawerState.open()
                                 }
                             }
                         )
                     }
                     NavDrawerItemType.Priorities -> {
                         MainTopBar(
                             onNavigationClicked = {
                                 navController.navigateUp()
                             },
                             navIcon = Icons.Default.ArrowBackIos,
                             title = "Priorities",
                             modifier = Modifier.testTag(TestTags.PRIORITIES_TOOL_BAR_TEST_TAG)
                         )
                     }
                     NavDrawerItemType.Folders -> {
                         MainTopBar(
                             onNavigationClicked = {
                                 navController.navigateUp()
                             },
                             navIcon = Icons.Default.ArrowBackIos,
                             title = "Folders",
                             modifier = Modifier.testTag(TestTags.FOLDER_TOOL_BAR_TEST_TAG)

                         )
                     }
                     NavDrawerItemType.Settings -> {
                         MainTopBar(
                             onNavigationClicked = {
                                 navController.navigateUp()
                             },
                             navIcon = Icons.Default.ArrowBackIos,
                             title = "Settings",
                             modifier = Modifier.testTag(TestTags.SETTINGS_TOOL_BAR_TEST_TAG)
                         )
                     }
                 }
             }
         },
         drawerBackgroundColor = NotaBoxTheme.colors.drawerBgColor,
         drawerShape = RoundedCornerShape(topEnd = NotaBoxTheme.spaces.mediumLarge, bottomEnd = NotaBoxTheme.spaces.mediumLarge),
         drawerContent = {
             DrawerHeader()
             DrawerBody(
                 items = drawerItems(),
                 selectedItem = viewModel.selectedDrawerItem.value,
                 onClick = {
                     viewModel.selectedDrawerItem.value = it.type
                     when(it.type){
                         NavDrawerItemType.Notes -> {
                             navController.navigate(MainNavigationRoutes.NotesScreenRoute.route)
                         }
                         NavDrawerItemType.TO_DOS -> {
                             navController.navigate(MainNavigationRoutes.TasksScreenRoute.route)
                         }
                         NavDrawerItemType.Priorities -> {
                             navController.navigate(MainNavigationRoutes.PrioritiesScreenRoute.route)
                         }
                         NavDrawerItemType.Folders -> {
                             navController.navigate(MainNavigationRoutes.FoldersScreenRoute.route)
                         }
                         NavDrawerItemType.Settings -> {
                             navController.navigate(MainNavigationRoutes.SettingsScreenRoute.route)
                         }
                     }
                     scope.launch {
                         scaffoldState.drawerState.close()
                     }
                 }
             )
         },
         drawerGesturesEnabled = if (currentDestination?.route == MainNavigationRoutes.NotesScreenRoute.route || currentDestination?.route == MainNavigationRoutes.TasksScreenRoute.route) true else scaffoldState.drawerState.isOpen
     ) {
         it
         MainNavigationGraph(navHostController = navController)
     }
 }

