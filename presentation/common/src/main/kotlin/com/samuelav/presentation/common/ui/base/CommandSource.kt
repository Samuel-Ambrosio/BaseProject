package com.samuelav.presentation.common.ui.base

import kotlinx.coroutines.flow.SharedFlow

interface CommandSource<Command> {
    val command: SharedFlow<Command>
}
