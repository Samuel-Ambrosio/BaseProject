package com.samuelav.presentation.features.home.ui

import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.fold
import com.samuelav.domain.usecase.todo.GetAllTodosUseCase
import com.samuelav.presentation.common.ui.base.ColdStateBaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector

internal class MainViewModel(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ColdStateBaseViewModel<MainState, Nothing>(initialState = MainState.Loading, ioDispatcher = ioDispatcher) {

    override suspend fun FlowCollector<MainState>.onInitState() {
        emit(MainState.Loading)
        getAllTodosUseCase().fold(
            isSuccess = { todos -> emit(MainState.Ready(todos = todos)) },
            isFailure = { error -> emit(MainState.Failure(error = error)) },
        )
    }

    fun retry() = retryInitState()
}

internal sealed interface MainState {
    data object Loading : MainState

    data class Ready(val todos: List<Todo>) : MainState

    data class Failure(val error: Error) : MainState
}
