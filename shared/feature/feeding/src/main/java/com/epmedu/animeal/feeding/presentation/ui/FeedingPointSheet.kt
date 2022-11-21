package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feeding.data.model.Feeder
import com.epmedu.animeal.feeding.data.model.enum.AnimalPriority
import com.epmedu.animeal.feeding.data.model.enum.Remoteness
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.button.AnimealHeartButton
import com.epmedu.animeal.foundation.common.FeedStatus
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point

@Composable
fun FeedingPointSheetContent(
    feedingPoint: FeedingPointModel,
    contentAlpha: Float,
    expandToFullScreen: Boolean = false,
    isShowOnMapVisible: Boolean = false,
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
        FeedingPointHeader(
            title = feedingPoint.title,
            status = feedingPoint.feedStatus,
            isFavourite = feedingPoint.isFavourite,
            onFavouriteChange = onFavouriteChange
        )
        FeedingPointDetails(
            description = feedingPoint.description,
            lastFeederName = feedingPoint.lastFeeder.name,
            lastFeedTime = feedingPoint.lastFeeder.time,
            scrimAlpha = contentAlpha
        )
        if (expandToFullScreen) {
            Spacer(modifier = Modifier.weight(1.0f))
        }
        if (isShowOnMapVisible) {
            ShowOnMapLink()
        }
    }
}

@Composable
internal fun FeedingPointHeader(
    title: String,
    status: FeedStatus,
    isFavourite: Boolean,
    onFavouriteChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
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
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
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
        AnimealHeartButton(
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

@Composable
internal fun FeedingPointDetails(
    scrimAlpha: Float,
    description: String,
    lastFeederName: String,
    lastFeedTime: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(32.dp)
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
            FeedingPointLastFeeder(
                name = lastFeederName,
                time = lastFeedTime,
            )
        }
    }
}

@Composable
internal fun FeedingPointLastFeeder(
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

@Composable
private fun ShowOnMapLink() {
    Text(
        text = stringResource(id = R.string.show_on_map),
        style = MaterialTheme.typography.subtitle1.copy(
            color = CustomColor.SeaSerpent,
            textDecoration = TextDecoration.Underline
        ),
        color = CustomColor.SeaSerpent,
    )
}

@AnimealPreview
@Composable
private fun FeedingPointSheetPreview(@PreviewParameter(LoremIpsum::class) text: String) {
    AnimealTheme {
        FeedingPointSheetContent(
            feedingPoint = FeedingPointModel(
                -1,
                text.take(30),
                feedStatus = FeedStatus.RED,
                description = text.take(200),
                isFavourite = true,
                lastFeeder = Feeder(
                    id = -1,
                    name = text.take(20),
                    time = "14 hours ago"
                ),
                animalPriority = AnimalPriority.HIGH,
                animalType = AnimalType.Dogs,
                remoteness = Remoteness.ANY,
                coordinates = Point.fromLngLat(0.0, 0.0)
            ),
            contentAlpha = 1f,
            isShowOnMapVisible = true,
            onFavouriteChange = {}
        )
    }
}