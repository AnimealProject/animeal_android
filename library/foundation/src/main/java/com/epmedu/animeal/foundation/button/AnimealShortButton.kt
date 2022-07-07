package com.epmedu.animeal.foundation.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme

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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ShortButtonPreview() {
    AnimealTheme {
        Surface {
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
}