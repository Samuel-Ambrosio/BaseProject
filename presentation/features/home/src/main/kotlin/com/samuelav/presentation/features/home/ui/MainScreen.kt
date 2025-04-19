package com.samuelav.presentation.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.extensions.handleErrorMessage
import com.samuelav.presentation.common.ui.base.CommandHandler
import com.samuelav.presentation.common.ui.composables.base.Heading1
import com.samuelav.presentation.common.ui.composables.base.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainScreen(appState: AppState) {
    val viewModel: MainViewModel = koinViewModel()
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    CommandHandler(viewModel) { command ->
        when (command) {
            is MainCommand.Error -> {
                appState.scaffoldState.snackbarHostState.showSnackbar(
                    message = command.error.handleErrorMessage(context = context),
                )
            }
        }
    }

    Screen(
        appState = appState,
        titleTopBar = stringResource(id = com.samuelav.presentation.common.R.string.nav_item_home),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Heading1(text = "Todos: ${state.todos.size}")
        }
    }
}