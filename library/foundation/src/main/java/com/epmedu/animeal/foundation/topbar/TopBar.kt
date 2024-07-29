package com.epmedu.animeal.foundation.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

/**
 * Similar to [TopAppBar], but it consists only from a navigation icon and a title.
 * @param title A title of the [TopBar].
 * @param modifier A modifier to be applied to the [TopBar].
 * @param navigationIcon A navigation icon before the title. By default empty.
 */
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit) = {}
) {
    Column(
        modifier = modifier
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
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = stringResource(id = R.string.content_description_back),
            modifier = Modifier.size(48.dp)
        )
    }
}

@AnimealPreview
@Composable
private fun TopBarPreview() {
    AnimealTheme {
        Column {
            TopBar(title = "Title")
            Divider()
            TopBar(title = "Title with icon") {
                BackButton {}
            }
        }
    }
}