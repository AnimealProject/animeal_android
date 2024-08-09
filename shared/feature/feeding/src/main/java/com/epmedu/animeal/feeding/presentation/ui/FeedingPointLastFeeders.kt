package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.formatNumberToHourMin
import com.epmedu.animeal.feeding.presentation.model.Feeding
import com.epmedu.animeal.foundation.listitem.ExpandableListItem
import com.epmedu.animeal.foundation.loading.ShimmerLoading
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.foundation.util.withLocalAlpha
import com.epmedu.animeal.resources.R
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun FeedingPointLastFeeder(
    feeding: Feeding?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        LastFeedersHeader(isAlone = true)
        if (isLoading) FeedingPointFeedingLoading()
        feeding?.let { FeedingPointFeeding(feeding = feeding) }
    }
}

@Composable
internal fun FeedingPointExpandableLastFeeders(
    feedings: List<Feeding>?,
    isExpandedInitially: Boolean = false
) {
    val configuration = LocalConfiguration.current
    var isExpanded by remember { mutableStateOf(isExpandedInitially) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        ExpandableListItem(
            text = { LastFeedersHeader() },
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.requiredWidth(configuration.screenWidthDp.dp),
            headerPadding = PaddingValues(horizontal = 8.dp),
            isExpanded = isExpanded,
            content = {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 32.dp)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    feedings?.forEach { feeding ->
                        FeedingPointFeeding(feeding = feeding)
                    } ?: repeat(3) {
                        FeedingPointFeedingLoading()
                    }
                }
            }
        )
    }
}

@Composable
private fun LastFeedersHeader(
    isAlone: Boolean = false
) {
    Text(
        text = stringResource(if (isAlone) R.string.last_feeder else R.string.last_feeders),
        style = MaterialTheme.typography.body1,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun FeedingPointFeeding(feeding: Feeding) {
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
private fun FeedingPointFeedingLoading() {
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
@AnimealPreview
private fun FeedingPointLastFeederPreview() {
    AnimealTheme {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FeedingPointLastFeeder(
                feeding = Feeding.History(
                    feederName = "Jane Doe",
                    elapsedTime = "1 hour ago"
                )
            )
            Divider()
            FeedingPointLastFeeder(
                feeding = Feeding.InProgress(
                    feederName = "John Doe",
                    timeLeft = 1.minutes.inWholeMilliseconds
                )
            )
            Divider()
            FeedingPointLastFeeder(
                feeding = null
            )
            Divider()
            FeedingPointLastFeeder(
                feeding = null,
                isLoading = true
            )
        }
    }
}

@Composable
@AnimealPreview
private fun FeedingPointExpandableLastFeedersPreview() {
    val feedings = List(3) {
        Feeding.History(
            feederName = "Jane Doe",
            elapsedTime = "1 hour ago"
        )
    }

    AnimealTheme {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FeedingPointExpandableLastFeeders(
                feedings = feedings
            )
            Divider()
            FeedingPointExpandableLastFeeders(
                feedings = null,
                isExpandedInitially = true
            )
            Divider()
            FeedingPointExpandableLastFeeders(
                feedings = feedings,
                isExpandedInitially = true
            )
        }
    }
}