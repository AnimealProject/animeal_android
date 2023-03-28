package com.epmedu.animeal.debugmenu.presentation.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun DebugMenuIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun DebugMenuIconButtonPreview() {
    AnimealTheme {
        DebugMenuIconButton(onClick = {})
    }
}