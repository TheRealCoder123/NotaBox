package com.upnext.notabox.presentation.activities.CreateViewNotesActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.upnext.notabox.R
import com.upnext.notabox.common.Constants
import com.upnext.notabox.presentation.navigation.CreateViewNavigationGraph
import com.upnext.notabox.presentation.navigation.CreateViewNoteNavigationRoutes
import com.upnext.notabox.presentation.note_view_screen.CreateNoteScreen
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotaBoxTheme {
                val navHostController = rememberNavController()
                val noteId = intent.getStringExtra(Constants.NOTE_ID_TO_CREATE_NOTE_SCREEN)
                val folderId = intent.getIntExtra(Constants.FOLDER_ID_TO_CREATE_NOTE_SCREEN_IF_IS_CURRENTLY_FOLDER_SELECTED, Constants.ADD_NOTE_TO_FOLDER_PARAM_PASSED_TO_CREATE_NOTE_SCREEN_IS_MINUS_ONE)
                CreateViewNavigationGraph(
                    navHostController = navHostController,
                    onBackPressed = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    noteId,
                    folderId
                )
            }
        }
    }
}