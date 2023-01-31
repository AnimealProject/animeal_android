package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealShortButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    AnimealButton(
        modifier = modifier.width(208.dp),
        text = text,
        enabled = enabled,
        onClick = onClick
    )
}

@AnimealPreview
@Composable
private fun ShortButtonPreview() {
    AnimealTheme {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            AnimealShortButton(
                text = "Enabled",
                onClick = {}
            )
            AnimealShortButton(
                text = "Disabled",
                enabled = false,
                onClick = {}
            )
        }
    }
}