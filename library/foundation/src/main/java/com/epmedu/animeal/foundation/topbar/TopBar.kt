package com.epmedu.animeal.foundation.topbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

/**
 * Similar to [TopAppBar], but it consists only from a navigation icon and a title.
 * @param title A title of the [TopBar].
 * @param navigationIcon A navigation icon before the title. By default empty.
 */
@Composable
fun TopBar(
    title: String,
    navigationIcon: (@Composable () -> Unit) = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .height(44.dp)
                .fillMaxWidth(),
        ) {
            navigationIcon()
        }

        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = title,
            style = MaterialTheme.typography.h5,
        )
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.then(Modifier.size(48.dp)),
        onClick = onClick
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