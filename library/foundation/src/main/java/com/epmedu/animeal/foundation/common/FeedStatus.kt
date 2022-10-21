package com.epmedu.animeal.foundation.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
fun FeedStatus(
    modifier: Modifier = Modifier,
    status: FeedStatus
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides status.color,
        ) {
            Icon(
                painter = painterResource(status.iconId),
                contentDescription = null
            )
            Text(
                text = stringResource(status.titleId),
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

enum class FeedStatus(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val color: Color
) {
    GREEN(R.string.feed_status_green, R.drawable.ic_face_happy, CustomColor.StatusGreen),
    RED(R.string.feed_status_red, R.drawable.ic_face_upset, CustomColor.StatusRed);
}
