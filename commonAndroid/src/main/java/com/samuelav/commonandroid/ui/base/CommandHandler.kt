package com.samuelav.commonandroid.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

@Composable
fun <Command> CommandHandler(
    viewModel: BaseViewModel<*, Command>,
    onCommand: suspend CoroutineScope.(Command) -> Unit
) {
    LaunchedEffect(viewModel) { viewModel.command.collect { onCommand(it) } }
}