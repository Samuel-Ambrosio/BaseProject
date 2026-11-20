package com.samuelav.presentation.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.Error
import com.samuelav.presentation.common.R
import com.samuelav.presentation.common.extensions.handleErrorMessage
import com.samuelav.presentation.common.ui.composables.base.BodyLargeRegular
import com.samuelav.presentation.common.ui.composables.base.ButtonText
import com.samuelav.presentation.common.ui.composables.base.Heading1
import com.samuelav.presentation.common.ui.composables.base.Screen
import com.samuelav.presentation.common.ui.composables.components.AppButton
import com.samuelav.presentation.common.ui.theme.AppTheme.colors
import com.samuelav.presentation.common.ui.theme.AppTheme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainScreen() {
    val viewModel: MainViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(titleTopBar = stringResource(id = R.string.nav_item_home)) {
        when (val currentState = state) {
            is MainState.Failure -> MainFailureContent(error = currentState.error, onRetryClick = viewModel::retry)
            is MainState.Loading -> MainLoadingContent()
            is MainState.Ready -> MainReadyContent(todos = currentState.todos)
        }
    }
}

@Composable
private fun MainFailureContent(error: Error, onRetryClick: () -> Unit) {
    val resources = LocalResources.current

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = spacing.l),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.m, Alignment.CenterVertically),
    ) {
        BodyLargeRegular(text = error.handleErrorMessage(resources = resources), textAlign = TextAlign.Center)

        AppButton(onClick = onRetryClick) {
            ButtonText(text = stringResource(id = R.string.retry), color = colors.onPrimary)
        }
    }
}

@Composable
private fun MainLoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(color = colors.primary)
    }
}

@Composable
private fun MainReadyContent(todos: List<Todo>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Heading1(text = "Todos: ${todos.size}")
    }
}
