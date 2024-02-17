package com.epmedu.animeal.tabs.more.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MoreOption(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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

@AnimealPreview
@Composable
private fun MoreOptionPreview() {
    AnimealTheme {
        MoreOption(title = "Profile Page", onClick = {})
    }
}
