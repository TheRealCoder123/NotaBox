 package com.upnext.notabox.presentation.activities.MainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.upnext.notabox.R
import com.upnext.notabox.common.Constants
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.common.rememberWindowInfo
import com.upnext.notabox.presentation.activities.MainActivity.components.MainViewModel
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.DrawerBody
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.DrawerHeader
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.NavDrawerItemType
import com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar.MainTopBar
import com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar.TopBarActionItem
import com.upnext.notabox.presentation.activities.MainActivity.components.TopAppBar.TopBarActionType
import com.upnext.notabox.presentation.folders_screen.FolderViewModel
import com.upnext.notabox.presentation.folders_screen.FoldersScreen
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
     val folderVm = hiltViewModel<FolderViewModel>()

     val windowInfo = rememberWindowInfo()

     val scaffoldState = rememberScaffoldState()
     val scope = rememberCoroutineScope()
     val navController = rememberNavController()


     val currentDestination = navController.currentBackStackEntryAsState().value?.destination

     when(currentDestination?.route){
         MainNavigationRoutes.NotesScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Notes
         //MainNavigationRoutes.TasksScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Tasks
         MainNavigationRoutes.FoldersScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Folders
         MainNavigationRoutes.SettingsScreenRoute.route -> viewModel.selectedDrawerItem.value = NavDrawerItemType.Settings
     }


     Scaffold(
         backgroundColor = NotaBoxTheme.colors.background,
         scaffoldState = scaffoldState,
         topBar = {
             when(viewModel.selectedDrawerItem.value){
                 NavDrawerItemType.Notes, NavDrawerItemType.Tasks, NavDrawerItemType.Folders -> {
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
         },
         drawerBackgroundColor = NotaBoxTheme.colors.drawerBgColor,
         drawerShape = RoundedCornerShape(topEnd = NotaBoxTheme.spaces.mediumLarge, bottomEnd = NotaBoxTheme.spaces.mediumLarge),
         drawerContent = {
             if(!windowInfo.isTablet){
                 DrawerHeader()
                 DrawerBody(
                     onClick = {
                         viewModel.selectedDrawerItem.value = it
                         when(it){
                             NavDrawerItemType.Notes -> {
                                 if (currentDestination?.route != MainNavigationRoutes.NotesScreenRoute.route){
                                     navController.navigate(MainNavigationRoutes.NotesScreenRoute.route){
                                         launchSingleTop = true
                                         popUpTo(MainNavigationRoutes.NotesScreenRoute.route){
                                             inclusive = true
                                         }
                                     }
                                 }
                             }
                             NavDrawerItemType.Tasks -> {}
                             NavDrawerItemType.Folders -> {
                                 if (currentDestination?.route != MainNavigationRoutes.FoldersScreenRoute.route){
                                     navController.navigate(MainNavigationRoutes.FoldersScreenRoute.route){
                                         launchSingleTop = true
                                         popUpTo(MainNavigationRoutes.FoldersScreenRoute.route){
                                             inclusive = true
                                         }
                                     }
                                 }
                             }
                             NavDrawerItemType.Settings -> {
                                 navController.navigate(MainNavigationRoutes.SettingsScreenRoute.route)
                             }
                         }
                         scope.launch {
                             scaffoldState.drawerState.close()
                         }
                     },
                     windowInfo = windowInfo
                 )
             }else{
                 Row(
                     modifier = Modifier.fillMaxSize()
                 ) {
                     LazyColumn(
                         modifier = Modifier
                             .fillMaxHeight()
                             .width(300.dp)
                     ) {
                         item {
                             DrawerHeader()
                             DrawerBody(
                                 onClick = {
                                     viewModel.selectedDrawerItem.value = it
                                     when(it){
                                         NavDrawerItemType.Notes -> {
                                             if (currentDestination?.route != MainNavigationRoutes.NotesScreenRoute.route){
                                                 navController.navigate(MainNavigationRoutes.NotesScreenRoute.route){
                                                     launchSingleTop = true
                                                     popUpTo(MainNavigationRoutes.NotesScreenRoute.route){
                                                         inclusive = true
                                                     }
                                                 }
                                             }
                                         }
                                         NavDrawerItemType.Tasks -> {}
                                         NavDrawerItemType.Folders -> {}
                                         NavDrawerItemType.Settings -> {
                                             navController.navigate(MainNavigationRoutes.SettingsScreenRoute.route){
                                                 popUpTo(MainNavigationRoutes.SettingsScreenRoute.route){
                                                     inclusive = true
                                                 }
                                             }
                                         }
                                     }
                                     scope.launch {
                                         scaffoldState.drawerState.close()
                                     }
                                 },
                                 windowInfo = windowInfo
                             )
                         }
                     }

                     FoldersScreen(
                         onEvent = { folderVm.onEvent(it) },
                         state = folderVm.state.value,
                         isDisplayedInDrawer = true
                     )

                 }
             }
         },
         drawerGesturesEnabled = if (currentDestination?.route == MainNavigationRoutes.NotesScreenRoute.route || currentDestination?.route == MainNavigationRoutes.FoldersScreenRoute.route) true else scaffoldState.drawerState.isOpen
     ) {
         it
         MainNavigationGraph(navHostController = navController, folderVm, viewModel)
     }
 }

