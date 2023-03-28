package com.epmedu.animeal.debugmenu.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun DebugMenuColumn(
    menuItems: List<DebugMenuItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(menuItems) { item ->
            when (item) {
                is DebugMenuItem.Divider -> {
                    Divider()
                }
                is DebugMenuItem.Switch -> {
                    DebugMenuSwitch(item)
                }
                is DebugMenuItem.Button -> {
                    DebugMenuButton(text = item.title, onClick = item.onClick)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DebugMenuColumnPreview() {
    AnimealTheme {
        DebugMenuColumn(
            menuItems = listOf(
                DebugMenuItem.Switch(title = "Switch", checkedInitially = false, onCheckedChange = {}),
                DebugMenuItem.Divider,
                DebugMenuItem.Button(title = "Button", onClick = {})
            )
        )
    }
}