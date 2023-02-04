package com.samuelav.presentation.common.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Command>(initialState: State): ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _command = MutableSharedFlow<Command>()

    val state = _state.asStateFlow()
    val command = _command.asSharedFlow()

    protected fun emitState(newState: State) { _state.value = newState }
    protected fun updateState(update: (oldState: State) -> State) { _state.update(update) }
    protected fun emitCommand(command: Command) { viewModelScope.launch { _command.emit(command) } }
}