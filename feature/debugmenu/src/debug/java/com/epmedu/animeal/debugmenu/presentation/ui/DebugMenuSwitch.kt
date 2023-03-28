package com.epmedu.animeal.debugmenu.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun DebugMenuSwitch(item: DebugMenuItem.Switch) {
    var checked by remember { mutableStateOf(item.checkedInitially) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                item.onCheckedChange(it)
            }
        )
        Text(text = item.title)
    }
}

@Preview
@Composable
private fun DebugMenuSwitchPreview() {
    AnimealTheme {
        DebugMenuSwitch(
            item = DebugMenuItem.Switch(
                title = "Switch",
                checkedInitially = false,
                onCheckedChange = {}
            )
        )
    }
}