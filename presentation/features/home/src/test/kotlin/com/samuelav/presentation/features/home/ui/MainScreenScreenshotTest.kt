package com.samuelav.presentation.features.home.ui

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.models.fakeTodos
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.usecase.todo.GetAllTodosUseCase
import com.samuelav.presentation.common.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
class MainScreenScreenshotTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainScreenLoaded() {
        setMainScreen(Result.Success(fakeTodos), waitForText = "Todos: ${fakeTodos.size}")

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun mainScreenLoadedDark() {
        setMainScreen(Result.Success(fakeTodos), waitForText = "Todos: ${fakeTodos.size}")

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun mainScreenFailure() {
        setMainScreen(Result.Failure(Error.Network), waitForText = "Retry")

        composeTestRule.onRoot().captureRoboImage()
    }

    private fun setMainScreen(result: Result<Error, List<Todo>>, waitForText: String) {
        loadKoinModules(
            module {
                viewModel {
                    MainViewModel(
                        getAllTodosUseCase = GetAllTodosUseCase(todoRepository = FakeTodoRepository(result)),
                        ioDispatcher = Dispatchers.Main,
                    )
                }
            }
        )
        composeTestRule.setContent {
            AppTheme { MainScreen() }
        }
        composeTestRule.waitUntilExactlyOneExists(hasText(waitForText))
    }
}
