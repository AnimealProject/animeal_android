package com.epmedu.animeal.favourites.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.DismissWillFeedDialog
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FavouriteChange
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FeedingPointHidden
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FeedingPointSelected
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.ShowWillFeedDialog
import com.epmedu.animeal.favourites.presentation.viewmodel.FavouritesState
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.model.toFeedStatus
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
internal fun FavouritesScreenUI(
    state: FavouritesState,
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
    HandleFeedingPointSheetHiddenState(bottomSheetState, onEvent)

    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()

    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    ScreenScaffold(
        bottomSheetState,
        state,
        contentAlpha,
        buttonAlpha,
        onEvent
    )
}

@Composable
private fun HandleFeedingPointSheetHiddenState(
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isHidden }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                onEvent(FeedingPointHidden)
            }
    }
}

@Composable
private fun ScreenScaffold(
    bottomSheetState: AnimealBottomSheetState,
    state: FavouritesState,
    contentAlpha: Float,
    buttonAlpha: Float,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
    AnimealBottomSheetLayout(
        modifier = Modifier.statusBarsPadding(),
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        isHalfExpandedStateEnabled = false,
        sheetContent = {
            state.showingFeedingPoint?.let { feedingPoint ->
                FeedingPointSheetContent(
                    feedingPoint = FeedingPointModel(
                        feedingPoint
                    ),
                    contentAlpha = contentAlpha,
                    modifier = Modifier.fillMaxHeight(),
                    isShowOnMapVisible = true,
                    onFavouriteChange = { isFavourite ->
                        onEvent(FavouriteChange(isFavourite, feedingPoint))
                    }
                )
            }
        },
        sheetControls = {
            FeedingPointActionButton(
                alpha = buttonAlpha,
                enabled = state.showingFeedingPoint?.animalStatus == AnimalState.RED,
                onClick = { onEvent(ShowWillFeedDialog) },
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.secondaryVariant),
            topBar = {
                TopBar(
                    title = stringResource(id = R.string.favourites),
                    modifier = Modifier.statusBarsPadding()
                )
            }
        ) { padding ->
            ScreenContent(state, padding, onEvent)
        }
    }

    WillFeedConfirmationDialog(state, onEvent)
}

@Composable
private fun ScreenContent(
    state: FavouritesState,
    padding: PaddingValues,
    onEvent: (FavouritesScreenEvent) -> Unit,
) {
    when {
        state.favourites.isEmpty() -> {
            EmptyState(padding)
        }
        else -> {
            FavouritesList(padding, state.favourites, onEvent)
        }
    }
}

@Composable
private fun EmptyState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.favourites_no_items),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun FavouritesList(
    padding: PaddingValues,
    favourites: List<FeedingPoint>,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 12.dp, bottom = 32.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(30.dp)

        ) {
            items(favourites) { feedingPoint ->
                FavouriteFeedingPointItem(
                    title = feedingPoint.title,
                    status = feedingPoint.animalStatus.toFeedStatus(),
                    isFavourite = feedingPoint.isFavourite,
                    onFavouriteChange = { isFavourite ->
                        onEvent(FavouriteChange(isFavourite, feedingPoint))
                    },
                    onClick = { onEvent(FeedingPointSelected(feedingPoint)) }
                )
            }
        }
    }
}

@Composable
internal fun WillFeedConfirmationDialog(
    state: FavouritesState,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
    if (state.showingWillFeedDialog) {
        FeedConfirmationDialog(
            onAgreeClick = { onEvent(DismissWillFeedDialog) },
            onCancelClick = { onEvent(DismissWillFeedDialog) }
        )
    }
}

@AnimealPreview
@Composable
private fun FavouritesScreenPreview() {
    val title = "FeedSpot"
    val favourites = listOf(
        FeedingPoint(
            id = "0",
            title = title,
            isFavourite = true,
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            description = "Hungry dog",
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        ),
    )
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                favourites = favourites.toImmutableList(),
            ),
            AnimealBottomSheetState(AnimealBottomSheetValue.Hidden),
        ) {}
    }
}

@AnimealPreview
@Composable
private fun FavouritesScreenEmptyPreview() {
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                persistentListOf()
            ),
            AnimealBottomSheetState(AnimealBottomSheetValue.Hidden),
        ) {}
    }
}