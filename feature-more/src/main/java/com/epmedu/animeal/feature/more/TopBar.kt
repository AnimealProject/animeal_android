package com.epmedu.animeal.feature.more

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.feature_more.R

/**
 * Similar to [TopAppBar], but it consists only from the back button and a title.
 * If you pass `null` or nothing in [onBack], the back button will disappear.
 * @param title A title of the [TopBar]
 * @param onBack A callback for the back button.
 * If `null` there will be no button. By default `null`.
 */
@Composable
fun TopBar(title: String, onBack: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBack?.let { BackButton(onClick = { onBack() }) }
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
        )
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier.padding(end = 8.dp)
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
private fun TopBarJustTitlePreview() {
    AnimealTheme {
        Surface {
            TopBar(title = "Title")
        }
    }
}

@Preview
@Composable
private fun TopBarFullPreview() {
    AnimealTheme {
        Surface {
            TopBar(title = "Title") {}
        }
    }
}