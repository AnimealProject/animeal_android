package com.epmedu.animeal.login.foundation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.spacer.VSpacer

@Composable
internal fun RowScope.LoginButtonContent(
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
) {
    Image(
        modifier = Modifier.height(20.dp),
        painter = painterResource(id = iconId),
        contentDescription = null,
    )
    VSpacer(16.dp)
    Text(
        text = stringResource(id = textId),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
