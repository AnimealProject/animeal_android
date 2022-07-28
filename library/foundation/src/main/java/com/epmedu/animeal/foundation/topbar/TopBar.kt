package com.epmedu.animeal.foundation.topbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

/**
 * Similar to [TopAppBar], but it consists only from a navigation icon and a title.
 * You can pass `null` in the [navigationIcon] to display only the title.
 * @param title A title of the [TopBar].
 * @param navigationIcon A navigation icon before the title. By default `null`.
 */
@Composable
fun TopBar(
    title: String,
    navigationIcon: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (navigationIcon == null) {
            WidthSpacer(24.dp)
        } else {
            WidthSpacer(8.dp)
            navigationIcon()
        }

        Text(
            text = title,
            style = MaterialTheme.typography.h4,
        )
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = stringResource(id = R.string.content_description_back),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    AnimealTheme {
        Surface {
            Column {
                TopBar(title = "Title")
                Divider()
                TopBar(title = "Title with icon") {
                    BackButton {}
                }
            }
        }
    }
}