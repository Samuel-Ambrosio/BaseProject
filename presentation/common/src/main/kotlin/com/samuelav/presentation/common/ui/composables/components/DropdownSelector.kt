package com.samuelav.presentation.common.ui.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samuelav.presentation.common.ui.composables.base.BodySmallBold
import com.samuelav.presentation.common.ui.composables.base.BodySmallRegular
import com.samuelav.presentation.common.ui.composables.base.ButtonText
import com.samuelav.presentation.common.ui.theme.AppTheme.colors
import com.samuelav.presentation.common.ui.theme.AppTheme.icons
import com.samuelav.presentation.common.ui.theme.AppTheme.spacing

@Composable
fun <T> DropdownSelector(
    modifier: Modifier = Modifier,
    text: String,
    menuItems: List<Pair<T, String>>,
    onItemSelected: (T) -> Unit,
    smallSelector: Boolean = false
) {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    Row(modifier = modifier) {
        AppOutlinedButton(
            modifier = modifier,
            onClick = { isDropdownMenuExpanded = !isDropdownMenuExpanded },
            borderColor = colors.onSurface.copy(alpha = 0.1f),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.xxs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (smallSelector) BodySmallBold(text = text, maxLines = 1) else ButtonText(text = text, maxLines = 1)
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter =
                        if (isDropdownMenuExpanded) icons.chevronUp.painter
                        else icons.chevronDown.painter,
                    contentDescription = null,
                    tint = colors.onSurface)
            }
        }

        DropdownMenu(
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }
        ) {
            menuItems.forEach {
                DropdownMenuItem(
                    onClick = {
                        isDropdownMenuExpanded = !isDropdownMenuExpanded
                        onItemSelected(it.first)
                    },
                    content = { BodySmallRegular(text = it.second) })
            }
        }
    }
}