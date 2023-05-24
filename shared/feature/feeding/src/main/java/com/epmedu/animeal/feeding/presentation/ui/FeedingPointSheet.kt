package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epmedu.animeal.extensions.formatNumberToHourMin
import com.epmedu.animeal.feeding.domain.model.enum.Remoteness
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.Feeding
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.button.AnimealHeartButton
import com.epmedu.animeal.foundation.loading.ShimmerLoading
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.text.MarkupText
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.foundation.util.withLocalAlpha
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point

/**
 * @param modifier Modifier.fillMaxHeight() to make the dialog full screen,
 * Modifier.wrapContentHeight() to make the dialog cover a part of the screen
 */
@Composable
fun FeedingPointSheetContent(
    feedingPoint: FeedingPointModel,
    contentAlpha: Float,
    modifier: Modifier = Modifier,
    isShowOnMapVisible: Boolean = false,
    onFavouriteChange: (Boolean) -> Unit,
    onShowOnMap: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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
        with(feedingPoint) {
            FeedingPointHeader(
                title = title,
                status = feedStatus,
                isFavourite = isFavourite,
                imageUrl = image,
                onFavouriteChange = onFavouriteChange
            )
            FeedingPointDetails(
                description = description,
                feedings = feedings,
                scrimAlpha = contentAlpha
            )
        }
        if (isShowOnMapVisible) {
            ShowOnMapLink(onClick = onShowOnMap)
        }
    }
}

@Composable
internal fun FeedingPointHeader(
    title: String,
    status: FeedStatus,
    isFavourite: Boolean,
    imageUrl: String,
    onFavouriteChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp)),
            elevation = 8.dp
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = title,
            )
        }
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
            FeedStatusItem(
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
internal fun FeedingPointDetails(
    scrimAlpha: Float,
    description: String,
    feedings: List<Feeding>?,
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
            MarkupText(
                text = description,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                alpha = scrimAlpha
            )
            FeedingPointFeedings(feedings = feedings)
        }
    }
}

@Composable
private fun FeedingPointFeedings(feedings: List<Feeding>?) {
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
        Column {
            feedings?.forEach { feeding ->
                Feeding(feeding = feeding)
            } ?: FeedingsLoading()
        }
    }
}

@Composable
private fun Feeding(feeding: Feeding) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(CustomColor.LynxWhite.withLocalAlpha()),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_person),
                tint = CustomColor.Orange.withLocalAlpha(),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = feeding.feederName.ifBlank { stringResource(id = R.string.unknown_user) },
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface.withLocalAlpha(),
                maxLines = 1,
            )
            Text(
                text = when (feeding) {
                    is Feeding.InProgress -> {
                        stringResource(
                            id = R.string.feeding_in_progress,
                            LocalContext.current.formatNumberToHourMin(feeding.timeLeft)
                                ?: stringResource(id = R.string.unknown_time)
                        )
                    }

                    is Feeding.History -> {
                        feeding.elapsedTime
                    }
                },
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis,
                color = CustomColor.DarkerGrey.withLocalAlpha(),
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun FeedingsLoading() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ShimmerLoading(
            modifier = Modifier.size(56.dp),
            alpha = LocalContentAlpha.current
        )
        Column(
            modifier = Modifier.height(56.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ShimmerLoading(
                modifier = Modifier.size(height = 14.dp, width = 160.dp),
                alpha = LocalContentAlpha.current
            )
            ShimmerLoading(
                modifier = Modifier.size(height = 12.dp, width = 96.dp),
                alpha = LocalContentAlpha.current
            )
        }
    }
}

@Composable
private fun ShowOnMapLink(onClick: () -> Unit) {
    Text(
        modifier = Modifier.clickable { onClick() },
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
private fun FeedingPointSheetLoadingPreview(@PreviewParameter(LoremIpsum::class) text: String) {
    AnimealTheme {
        FeedingPointSheetContent(
            feedingPoint = FeedingPointModel(
                id = "",
                title = text.take(30),
                feedStatus = FeedStatus.RED,
                description = stringResource(id = R.string.feeding_sheet_mock_text),
                city = "Minsk",
                isFavourite = true,
                feedings = null,
                animalType = AnimalType.Dogs,
                remoteness = Remoteness.ANY,
                coordinates = Point.fromLngLat(0.0, 0.0)
            ),
            contentAlpha = 1f,
            modifier = Modifier.fillMaxHeight(),
            isShowOnMapVisible = true,
            onFavouriteChange = {},
            onShowOnMap = {}
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingPointSheetPreview(@PreviewParameter(LoremIpsum::class) text: String) {
    AnimealTheme {
        FeedingPointSheetContent(
            feedingPoint = FeedingPointModel(
                id = "",
                title = text.take(30),
                feedStatus = FeedStatus.RED,
                description = stringResource(id = R.string.feeding_sheet_mock_text),
                city = "Minsk",
                isFavourite = true,
                feedings = listOf(
                    Feeding.History(
                        id = "-1",
                        feederName = text.take(20),
                        elapsedTime = "14 hours ago"
                    )
                ),
                animalType = AnimalType.Dogs,
                remoteness = Remoteness.ANY,
                coordinates = Point.fromLngLat(0.0, 0.0)
            ),
            contentAlpha = 1f,
            modifier = Modifier.fillMaxHeight(),
            isShowOnMapVisible = true,
            onFavouriteChange = {},
            onShowOnMap = {}
        )
    }
}