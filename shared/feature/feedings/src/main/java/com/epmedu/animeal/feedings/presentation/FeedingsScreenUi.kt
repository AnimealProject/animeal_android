package com.epmedu.animeal.feedings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feedings.presentation.model.FeedStatus
import com.epmedu.animeal.feedings.presentation.model.FeedingItem
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FeedingsScreenUI(
    feedings: ImmutableList<FeedingItem>,
    onBack: () -> Unit,
    onEvent: (FeedingsScreenEvent) -> Unit,
) {
    Column {
        TopBar(
            title = stringResource(id = R.string.feedings),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        FeedingsScreenContentUI(feedings, PaddingValues(30.dp), onEvent)
    }
}

@AnimealPreview
@Composable
private fun FeedingScreenPreview() {
    val title = "FeedSpot"
    val feedings = listOf(
        FeedingItem(
            id = "0",
            title = title,
            user = DefaultConstants.EMPTY_STRING,
            status = FeedStatus.OUTDATED,
            image = DefaultConstants.EMPTY_STRING,
            elapsedTime = "12 hours ago"
        ),
    )
    AnimealTheme {
        FeedingsScreenUI(
            feedings = feedings.toImmutableList(),
            {},
            {},
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingScreenEmptyPreview() {
    AnimealTheme {
        FeedingsScreenUI(
            feedings = listOf<FeedingItem>().toImmutableList(),
            {},
            {},
        )
    }
}