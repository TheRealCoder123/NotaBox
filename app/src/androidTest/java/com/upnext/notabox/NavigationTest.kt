package com.upnext.notabox

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.upnext.notabox.common.TestTags
import com.upnext.notabox.di.AppModule
import com.upnext.notabox.presentation.activities.MainActivity.MainActivity
import com.upnext.notabox.presentation.activities.MainActivity.components.NavigationDrawer.NavDrawerItemType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun on_app_opened_test_if_first_screen_is_notes_screen() {
        composeRule.onNodeWithTag(TestTags.NOTES_SCREEN_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun test_navigation_with_to_do_screen() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(appContext.getString(R.string.nav_icon)).performClick()
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(NavDrawerItemType.Tasks.name).performClick()
        composeRule.onNodeWithTag(TestTags.ToDos_SCREEN_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun test_navigation_with_priorities_screen() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(appContext.getString(R.string.nav_icon)).performClick()
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(NavDrawerItemType.Priorities.name).performClick()
        composeRule.onNodeWithTag(TestTags.PRIORITIES_SCREEN_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.PRIORITIES_TOOL_BAR_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun test_navigation_with_folders_screen() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(appContext.getString(R.string.nav_icon)).performClick()
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(NavDrawerItemType.Folders.name).performClick()
        composeRule.onNodeWithTag(TestTags.FOLDERS_SCREEN_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.FOLDER_TOOL_BAR_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun test_navigation_with_settings_screen() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(appContext.getString(R.string.nav_icon)).performClick()
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(NavDrawerItemType.Settings.name).performClick()
        composeRule.onNodeWithTag(TestTags.SETTINGS_SCREEN_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.SETTINGS_TOOL_BAR_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun text_navigation_with_create_note_screen() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithTag(TestTags.NAV_DRAWER_TEST_TAG).assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(appContext.getString(R.string.add)).performClick()
        composeRule.onNodeWithTag(TestTags.CREATE_NOTE_SCREEN_TEST_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.CREATE_NOTE_TOOL_BAR_TEST_TAG).assertIsDisplayed()
    }

}