package com.epmedu.animeal.login.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.resources.R

@Composable
internal fun RowScope.LoginButtonContent(
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
    tint: Color? = null
) {
    Image(
        modifier = Modifier.height(20.dp),
        painter = painterResource(id = iconId),
        contentDescription = null,
        colorFilter = when {
            tint != null -> ColorFilter.tint(tint)
            else -> null
        }
    )
    WidthSpacer(16.dp)
    Text(
        text = stringResource(id = textId),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
private fun LoginButtonContentPreview() {
    AnimealTheme {
        Row {
            LoginButtonContent(
                iconId = R.drawable.ic_google,
                textId = R.string.sign_in_google,
            )
        }
    }
}
