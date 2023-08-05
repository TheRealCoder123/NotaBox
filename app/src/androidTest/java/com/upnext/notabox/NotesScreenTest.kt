package com.upnext.notabox

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.di.AppModule
import com.upnext.notabox.presentation.activities.MainActivity.MainActivity
import com.upnext.notabox.presentation.navigation.MainNavigationRoutes
import com.upnext.notabox.presentation.notes_screen.NotesScreen
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navHostController = rememberNavController()
            NotaBoxTheme {
                NavHost(
                    navController = navHostController,
                    startDestination = MainNavigationRoutes.NotesScreenRoute.route
                ) {
                    composable(MainNavigationRoutes.NotesScreenRoute.route){
                        NotesScreen(
                            navController =  navHostController,
                        )
                    }
                }
            }
        }
        composeRule.waitForIdle()
    }

    @Test
    fun test_toggle_filter_is_visible() {
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION_TEST_TAG).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.FILTER_ICON_TEST_TAG).performClick()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.FILTER_ICON_TEST_TAG).performClick()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun test_if_search_text_field_works() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithTag(TestTags.SEARCH_TEXT_HINT_TEST_TAG, useUnmergedTree = true).apply {
            assertIsDisplayed()
            assertTextEquals("Search")
        }
        composeRule.onNodeWithContentDescription(appContext.getString(R.string.close)).assertDoesNotExist()

        composeRule.onNodeWithTag(TestTags.SEARCH_TEXT_FIELD_TEST_TAG).performTextInput("Search Test Input")
        composeRule.onNodeWithContentDescription(appContext.getString(R.string.close)).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.SEARCH_TEXT_HINT_TEST_TAG).assertDoesNotExist()

        composeRule.onNodeWithContentDescription(appContext.getString(R.string.close)).performClick()

        composeRule.onNodeWithTag(TestTags.SEARCH_TEXT_HINT_TEST_TAG, useUnmergedTree = true).apply {
            assertTextEquals("Search")
            assertIsDisplayed()
        }
    }

}