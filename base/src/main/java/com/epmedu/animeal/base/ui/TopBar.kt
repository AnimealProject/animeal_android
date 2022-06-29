package com.epmedu.animeal.base.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.R
import com.epmedu.animeal.base.theme.AnimealTheme

/**
 * Similar to [TopAppBar], but it consists only from a navigation icon and a title.
 * You can pass `null` in the [navigationIcon] to display only the title.
 * @param title A title of the [TopBar].
 * @param padding Padding values that apply to the [TopBar],
 * except a start padding, which only applies when there is no navigation icon.
 * By default all values are 32 dp.
 * @param navigationIcon A navigation icon before the title. By default `null`.
 */
@Composable
fun TopBar(
    title: String,
    padding: PaddingValues = PaddingValues(32.dp),
    navigationIcon: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
                end = padding.calculateEndPadding(LayoutDirection.Ltr)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (navigationIcon == null) {
            Spacer(
                modifier = Modifier
                    .width(padding.calculateStartPadding(LayoutDirection.Ltr))
            )
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