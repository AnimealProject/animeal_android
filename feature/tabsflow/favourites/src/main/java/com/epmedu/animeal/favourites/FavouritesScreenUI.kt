package com.epmedu.animeal.favourites

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.favourites.ui.FavouriteFeedingPointItem
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.feeding.data.model.Feeder
import com.epmedu.animeal.feeding.data.model.FeedingPoint
import com.epmedu.animeal.feeding.data.model.enum.AnimalPriority
import com.epmedu.animeal.feeding.data.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.model.toFeedStatus
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.foundation.dialog.bottomsheet.*
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import kotlinx.coroutines.launch

@Composable
internal fun FavouritesScreenUI(
    state: FavouritesState,
    bottomSheetState: HomeBottomSheetState,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
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
private fun ScreenScaffold(
    bottomSheetState: HomeBottomSheetState,
    state: FavouritesState,
    contentAlpha: Float,
    buttonAlpha: Float,
    onEvent: (FavouritesScreenEvent) -> Unit
) {

    HomeBottomSheetLayout(
        modifier = Modifier.statusBarsPadding(),
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        isShownStateEnabled = false,
        sheetContent = {
            state.showingFeedSpot?.let { feedingPoint ->
                FeedingPointSheetContent(
                    feedingPoint = FeedingPointModel(
                        feedingPoint
                    ),
                    contentAlpha = contentAlpha,
                    expandToFullScreen = true,
                    isShowOnMapVisible = true,
                    onFavouriteChange = { selected ->
                        onEvent(
                            FavouritesScreenEvent.FeedSpotChanged(
                                feedingPoint.id,
                                isFavorite = selected,
                            )
                        )
                    }
                )
            }
        },
        sheetControls = {
            FeedingPointActionButton(
                alpha = buttonAlpha,
                enabled = state.showingFeedSpot?.animalStatus == AnimalState.RED,
                onClick = { onEvent(FavouritesScreenEvent.ShowWillFeedDialog(state.showingFeedSpot!!.id)) },
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
            items(favourites) { item ->
                FavouriteFeedingPointItem(
                    title = item.title,
                    status = item.animalStatus.toFeedStatus(),
                    isFavourite = item.isFavourite,
                    onFavouriteChange = {
                        onEvent(FavouritesScreenEvent.FeedSpotChanged(item.id, isFavorite = false))
                    },
                    onClick = {
                        onEvent(FavouritesScreenEvent.FeedSpotSelected(item.id))
                    }
                )
            }
        }
    }
}

@Composable
internal fun WillFeedConfirmationDialog(state: FavouritesState, onEvent: (FavouritesScreenEvent) -> Unit) {
    if (state.showingWillFeedDialog) {
        FeedConfirmationDialog(
            onAgreeClick = { onEvent(FavouritesScreenEvent.DismissWillFeedDialog) },
            onCancelClick = { onEvent(FavouritesScreenEvent.DismissWillFeedDialog) }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@AnimealPreview
@Composable
private fun FavouritesScreenPreview() {
    val title = "FeedSpot"
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                listOf(
                    FeedingPoint(
                        id = 0,
                        title = title,
                        isFavourite = true,
                        animalPriority = AnimalPriority.HIGH,
                        animalStatus = AnimalState.RED,
                        animalType = AnimalType.Dogs,
                        description = "Hungry dog",
                        lastFeeder = Feeder(id = 0, "Fred", "12:00"),
                        location = MapLocation.Tbilisi,
                    ),
                )
            ),
            rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden)
        ) {}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@AnimealPreview
@Composable
private fun FavouritesScreenEmptyPreview() {
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                emptyList()
            ),
            rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden)
        ) {}
    }
}