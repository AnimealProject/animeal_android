package com.epmedu.animeal.home.presentation.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.home.data.model.FeedSpot
import com.epmedu.animeal.home.data.model.FeedStatus
import com.epmedu.animeal.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun FeedSpotSheetContent(
    feedSpot: FeedSpot,
    contentAlpha: Float,
    onFavouriteChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.surface)
            .padding(
                top = 8.dp,
                bottom = 110.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Divider(
            modifier = Modifier
                .width(18.dp)
                .clip(RoundedCornerShape(2.dp)),
            thickness = 4.dp,
        )
        FeedSpotSheetHeader(
            title = feedSpot.title,
            status = feedSpot.status,
            isFavourite = feedSpot.isFavourite,
            onFavouriteChange = onFavouriteChange
        )
        FeedSpotDetails(
            description = feedSpot.description,
            lastFeederName = feedSpot.lastFeeder.name,
            lastFeedTime = feedSpot.lastFeeder.time,
            scrimAlpha = contentAlpha
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HeartButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onChange: (Boolean) -> Unit,
) {
    val iconColor =
        if (selected) MaterialTheme.colors.error
        else MaterialTheme.colors.secondaryVariant

    Surface(
        modifier = modifier.size(32.dp),
        shape = CircleShape,
        elevation = 1.dp,
        onClick = {
            onChange(!selected)
        },
    ) {
        Icon(
            modifier = Modifier.requiredSize(16.dp),
            imageVector = Icons.Filled.Favorite,
            tint = iconColor,
            contentDescription = null
        )
    }
}

@Composable
internal fun FeedSpotSheetHeader(
    title: String,
    status: FeedStatus,
    isFavourite: Boolean,
    onFavouriteChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.secondaryVariant)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface,
                maxLines = 2,
            )
            FeedStatus(
                status = status,
            )
        }
        HeartButton(
            modifier = Modifier.align(Alignment.Top),
            selected = isFavourite,
            onChange = onFavouriteChange,
        )
    }
}

@Composable
internal fun FeedStatus(
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
internal fun FeedSpotDetails(
    scrimAlpha: Float,
    description: String,
    lastFeederName: String,
    lastFeedTime: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(52.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onSurface,
            LocalContentAlpha provides scrimAlpha,
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
            )
            FeedSpotLastFeeder(
                name = lastFeederName,
                time = lastFeedTime,
            )
        }
    }
}

@Composable
internal fun FeedSpotLastFeeder(
    name: String,
    time: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.last_feeder),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(CustomColor.LynxWhite),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    tint = CustomColor.Orange,
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    color = CustomColor.DarkerGrey,
                    maxLines = 1,
                )
            }
        }
    }
}
