package com.epmedu.animeal.base.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.R
import com.epmedu.animeal.base.theme.AnimealTheme

/**
 * Similar to [TopAppBar], but it consists only from a navigation icon and a title.
 * You can pass `null` in the [navigationIcon] to display only the title.
 * @param title A title of the [TopBar].
 * @param navigationIcon A navigation icon before the title. By default `null`.
 */
@Composable
fun TopBar(title: String, navigationIcon: (@Composable () -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, end = 32.dp, bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (navigationIcon == null) {
            Spacer(modifier = Modifier.width(24.dp))
        } else {
            Spacer(modifier = Modifier.width(8.dp))
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
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
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