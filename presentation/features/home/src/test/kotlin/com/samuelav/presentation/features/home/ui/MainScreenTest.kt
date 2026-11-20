package com.samuelav.presentation.features.home.ui

import android.content.Context
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.models.fakeTodos
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.repository.todo.TodoRepository
import com.samuelav.domain.usecase.todo.GetAllTodosUseCase
import com.samuelav.presentation.common.R
import com.samuelav.presentation.common.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTestRule

@RunWith(AndroidJUnit4::class)
class MainScreenTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create()

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun showsAProgressIndicatorWhileLoading() {
        setMainScreen(
            todoRepository = object : TodoRepository {
                override suspend fun getAll(): Result<Error, List<Todo>> = awaitCancellation()
            }
        )

        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertExists()
        composeTestRule.onNode(hasText("Todos:", substring = true)).assertDoesNotExist()
    }

    @Test
    fun showsTheNumberOfLoadedTodos() {
        setMainScreen(todoRepository = FakeTodoRepository(Result.Success(fakeTodos)))

        composeTestRule.waitUntilExactlyOneExists(hasText("Todos: ${fakeTodos.size}"))
    }

    @Test
    fun showsAFullScreenErrorWithRetryInsteadOfTodosWhenLoadingFails() {
        setMainScreen(todoRepository = FakeTodoRepository(Result.Failure(Error.Network)))

        composeTestRule.waitUntilExactlyOneExists(hasText(context.getString(R.string.result_error_network)))
        composeTestRule.onNodeWithText(context.getString(R.string.retry)).assertExists()
        composeTestRule.onNode(hasText("Todos:", substring = true)).assertDoesNotExist()
    }

    @Test
    fun retryLoadsTheTodosAfterAFailure() {
        setMainScreen(todoRepository = FakeTodoRepository(Result.Failure(Error.Network), Result.Success(fakeTodos)))
        composeTestRule.waitUntilExactlyOneExists(hasText(context.getString(R.string.retry)))

        composeTestRule.onNodeWithText(context.getString(R.string.retry)).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasText("Todos: ${fakeTodos.size}"))
    }

    private fun setMainScreen(todoRepository: TodoRepository) {
        loadKoinModules(
            module {
                viewModel {
                    MainViewModel(
                        getAllTodosUseCase = GetAllTodosUseCase(todoRepository = todoRepository),
                        ioDispatcher = Dispatchers.Main,
                    )
                }
            }
        )
        composeTestRule.setContent {
            AppTheme { MainScreen() }
        }
    }
}
