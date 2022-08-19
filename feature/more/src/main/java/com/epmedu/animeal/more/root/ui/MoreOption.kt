package com.epmedu.animeal.more.root.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.AnimealTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MoreOption(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable { onClick() }
            .padding(start = 8.dp, end = 8.dp),
        text = {
            Text(text = title)
        },
        trailing = {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = title,
            )
        }
    )
}

@Preview
@Composable
private fun MoreOptionPreview() {
    AnimealTheme {
        Surface {
            MoreOption(title = "Profile Page") {}
        }
    }
}
