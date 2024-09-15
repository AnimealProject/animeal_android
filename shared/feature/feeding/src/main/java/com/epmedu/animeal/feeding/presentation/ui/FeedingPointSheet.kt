package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.Feeding
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.button.AnimealHeartButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.text.MarkupText
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point
import kotlinx.coroutines.launch

/**
 * @param feedingPoint feeding point to be presented.
 * @param contentAlpha alpha of the content.
 * @param modifier Modifier.fillMaxHeight() to make the dialog full screen,
 * Modifier.wrapContentHeight() to make the dialog cover a part of the screen.
 * @param onFavouriteChange callback to be invoked on tapping on the heart button.
 */
@Composable
fun FeedingPointSheetContent(
    feedingPoint: FeedingPointModel,
    contentAlpha: Float,
    modifier: Modifier = Modifier,
    useExpandableFeeders: Boolean = false,
    showAssignedModerators: Boolean = false,
    onFavouriteChange: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(
                top = 8.dp,
                bottom = if (useExpandableFeeders) 140.dp else 110.dp,
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
                image = image,
                onFavouriteChange = onFavouriteChange
            )
            FeedingPointDetails(
                description = description,
                feedings = feedings,
                scrimAlpha = contentAlpha,
                assignedModerators = if (showAssignedModerators) assignedModerators else null,
                useExpandableFeeders = useExpandableFeeders
            )
        }
    }
}

@Composable
internal fun FeedingPointHeader(
    title: String,
    status: FeedStatus,
    isFavourite: Boolean,
    image: NetworkFile?,
    onFavouriteChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FeedingPointImage(
            image = image,
            contentDescription = title
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
    assignedModerators: List<String>?,
    useExpandableFeeders: Boolean
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colors.onSurface,
        LocalContentAlpha provides scrimAlpha,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                MarkupText(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    alpha = scrimAlpha
                )
            }
            item {
                HeightSpacer(height = 24.dp)
            }
            item {
                when {
                    useExpandableFeeders -> {
                        FeedingPointExpandableLastFeeders(feedings = feedings)
                    }

                    else -> {
                        FeedingPointLastFeeder(
                            feeding = feedings?.firstOrNull(),
                            modifier = Modifier.padding(top = 8.dp),
                            isLoading = feedings == null
                        )
                    }
                }
            }
            assignedModerators?.let { moderators ->
                item {
                    FeedingPointAssignedModerators(
                        moderators = moderators,
                        onExpanding = {
                            coroutineScope.launch {
                                lazyListState.layoutInfo.viewportSize.height.let {
                                    lazyListState.animateScrollBy(it.toFloat())
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowOnMapLink(onClick: () -> Unit) {
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
                feedStatus = FeedStatus.Starved,
                description = stringResource(id = R.string.feeding_sheet_mock_text),
                city = "Minsk",
                isFavourite = true,
                feedings = null,
                animalType = AnimalType.Dogs,
                coordinates = Point.fromLngLat(0.0, 0.0)
            ),
            contentAlpha = 1f,
            modifier = Modifier.fillMaxHeight(),
            onFavouriteChange = {}
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
                feedStatus = FeedStatus.Starved,
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
                coordinates = Point.fromLngLat(0.0, 0.0)
            ),
            contentAlpha = 1f,
            modifier = Modifier.fillMaxHeight()
        )
    }
}