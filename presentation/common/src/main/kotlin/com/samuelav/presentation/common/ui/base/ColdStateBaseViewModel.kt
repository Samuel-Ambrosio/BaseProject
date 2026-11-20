package com.samuelav.presentation.common.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
abstract class ColdStateBaseViewModel<State, Command>(
    initialState: State,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(), CommandSource<Command> {
    protected val ioCoroutineScope = CoroutineScope(viewModelScope.coroutineContext + ioDispatcher)

    private val retryFlow = MutableSharedFlow<Unit>()
    private val _command = MutableSharedFlow<Command>()

    protected abstract suspend fun FlowCollector<State>.onInitState()

    val state: StateFlow<State> =
        retryFlow
            .onStart { emit(Unit) }
            .flatMapLatest { flow { onInitState() } }
            .stateIn(
                scope = ioCoroutineScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = STOP_TIMEOUT_MILLIS),
                initialValue = initialState,
            )

    override val command: SharedFlow<Command> = _command.asSharedFlow()

    protected fun emitCommand(command: Command) { viewModelScope.launch { _command.emit(command) } }

    protected fun retryInitState() { viewModelScope.launch { retryFlow.emit(Unit) } }

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
