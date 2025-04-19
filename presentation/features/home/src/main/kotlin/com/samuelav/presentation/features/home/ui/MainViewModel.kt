package com.samuelav.presentation.features.home.ui

import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.fold
import com.samuelav.domain.usecase.todo.GetAllTodosUseCase
import com.samuelav.presentation.common.ui.base.BaseViewModel
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val getAllTodosUseCase: GetAllTodosUseCase,
) : BaseViewModel<MainState, MainCommand>(MainState()) {
    init {
        ioCoroutineScope.launch {
            getAllTodosUseCase().fold(
                isSuccess = { todos ->
                    updateState { state -> state.copy(isLoading = false, todos = todos) }
                },
                isFailure = { error ->
                    updateState { state -> state.copy(isLoading = false) }
                    emitCommand(MainCommand.Error(error = error))
                }
            )
        }
    }
}

internal data class MainState(
    val isLoading: Boolean = false,
    val todos: List<Todo> = emptyList(),
)

internal sealed interface MainCommand {
    data class Error(val error: com.samuelav.domain.model.utils.Error) : MainCommand
}