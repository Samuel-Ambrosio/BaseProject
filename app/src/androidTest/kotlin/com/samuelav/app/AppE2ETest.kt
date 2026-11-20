package com.samuelav.app

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.samuelav.app.ui.activity.MainActivity
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.samuelav.presentation.common.R as CommonR

@RunWith(AndroidJUnit4::class)
class AppE2ETest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    private lateinit var scenario: ActivityScenario<MainActivity>
    private val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    @After
    fun tearDown() {
        if (::scenario.isInitialized) scenario.close()
    }

    @Test
    fun homeShowsTheTodosReturnedByTheApi() {
        FakeBackend.todosResponse = FakeBackend.FakeResponse(
            code = 200,
            body = """[{"userId":1,"id":1,"title":"First","completed":false},
                |{"userId":1,"id":2,"title":"Second","completed":true},
                |{"userId":2,"id":3,"title":"Third","completed":false}]""".trimMargin(),
        )

        launchApp()

        composeTestRule.waitUntilExactlyOneExists(hasText("Todos: 3"), TIMEOUT_MILLIS)
    }

    @Test
    fun homeShowsAFullScreenErrorAndRetryRecoversWhenTheApiComesBack() {
        FakeBackend.todosResponse = FakeBackend.FakeResponse(code = 500, body = "")

        launchApp()

        composeTestRule.waitUntilExactlyOneExists(hasText(resources.getString(CommonR.string.result_error_server)), TIMEOUT_MILLIS)
        composeTestRule.onNode(hasText("Todos:", substring = true)).assertDoesNotExist()

        FakeBackend.todosResponse = FakeBackend.FakeResponse(
            code = 200,
            body = """[{"userId":1,"id":1,"title":"First","completed":false}]""",
        )
        composeTestRule.onNodeWithText(resources.getString(CommonR.string.retry)).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasText("Todos: 1"), TIMEOUT_MILLIS)
    }

    @Test
    fun bottomNavigationMovesBetweenSectionsAndBackReturnsHome() {
        FakeBackend.todosResponse = FakeBackend.FakeResponse(code = 200, body = "[]")
        launchApp()
        composeTestRule.waitUntilExactlyOneExists(hasText("Todos: 0"), TIMEOUT_MILLIS)

        composeTestRule.onNodeWithContentDescription(resources.getString(CommonR.string.nav_item_search)).performClick()
        composeTestRule.waitUntilExactlyOneExists(hasText("Search"), TIMEOUT_MILLIS)

        composeTestRule.onNodeWithContentDescription(resources.getString(CommonR.string.nav_item_more)).performClick()
        val moreTitle = resources.getString(CommonR.string.nav_item_more)
        composeTestRule.waitUntil(TIMEOUT_MILLIS) {
            composeTestRule.onAllNodesWithText("More").fetchSemanticsNodes().isNotEmpty() &&
                composeTestRule.onAllNodesWithText(moreTitle).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription(resources.getString(CommonR.string.content_description_navigate_up)).performClick()
        composeTestRule.waitUntilExactlyOneExists(hasText("Todos: 0"), TIMEOUT_MILLIS)

        composeTestRule.onNodeWithContentDescription(resources.getString(CommonR.string.nav_item_search)).performClick()
        composeTestRule.waitUntilExactlyOneExists(hasText("Search"), TIMEOUT_MILLIS)

        Espresso.pressBack()

        composeTestRule.waitUntilExactlyOneExists(hasText("Todos: 0"), TIMEOUT_MILLIS)
    }

    private fun launchApp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    private companion object {
        const val TIMEOUT_MILLIS = 10_000L
    }
}
