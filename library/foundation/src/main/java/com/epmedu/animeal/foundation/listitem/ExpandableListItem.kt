package com.epmedu.animeal.foundation.listitem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableListItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        ListItem(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp),
            text = { Text(text = title) },
            trailing = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = when {
                        isExpanded -> Icons.Default.KeyboardArrowUp
                        else -> Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null
                )
            }
        )
        AnimatedVisibility(visible = isExpanded) {
            content()
        }
    }
}

@AnimealPreview
@Composable
private fun ExpandableListItemPreview() {
    AnimealTheme {
        Column {
            ExpandableListItem(
                title = "Collapsed",
                onClick = {}
            ) {}
            Divider()
            ExpandableListItem(
                title = "Expanded",
                onClick = {},
                isExpanded = true
            ) {
                Text(
                    text = "Expanded text",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}