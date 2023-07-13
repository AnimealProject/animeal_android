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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.common.constants.Arguments
import com.epmedu.animeal.common.route.TabsRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FavouriteChange
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FeedingPointHidden
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FeedingPointSelected
import com.epmedu.animeal.favourites.presentation.viewmodel.FavouritesState
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent.WillFeedClicked
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointItem
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.feeding.presentation.ui.WillFeedDialog
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState.FeedingStarted
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState.FeedingWasAlreadyBooked
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.permissions.presentation.AnimealPermissions
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.resources.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun FavouritesScreenUI(
    state: FavouritesState,
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (FavouritesScreenEvent) -> Unit,
    onFeedingEvent: (FeedingEvent) -> Unit,
    onPermissionsEvent: (PermissionsEvent) -> Unit,
    onWillFeedEvent: (WillFeedEvent) -> Unit
) {
    HandleFeedingPointSheetHiddenState(bottomSheetState, onEvent)

    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()

    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    AnimealPermissions(
        permissionsState = state.permissionsState,
        lifecycleState = LocalLifecycleOwner.current.lifecycle.currentState,
        onEvent = onPermissionsEvent
    ) {
        ScreenScaffold(
            bottomSheetState,
            state,
            contentAlpha,
            buttonAlpha,
            onEvent,
            onFeedingEvent,
            onWillFeedEvent
        )
    }
}

@Composable
private fun HandleFeedingPointSheetHiddenState(
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (FavouritesScreenEvent) -> Unit,
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
@Suppress("LongMethod")
private fun ScreenScaffold(
    bottomSheetState: AnimealBottomSheetState,
    state: FavouritesState,
    contentAlpha: Float,
    buttonAlpha: Float,
    onEvent: (FavouritesScreenEvent) -> Unit,
    onFeedingEvent: (FeedingEvent) -> Unit,
    onWillFeedEvent: (WillFeedEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow

    AnimealBottomSheetLayout(
        modifier = Modifier.statusBarsPadding(),
        skipHalfExpanded = true,
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            state.showingFeedingPoint?.let { feedingPoint ->
                FeedingPointSheetContent(
                    feedingPoint = feedingPoint,
                    contentAlpha = contentAlpha,
                    modifier = Modifier.fillMaxHeight(),
                    isShowOnMapVisible = state.feedState.feedPoint == null,
                    onFavouriteChange = { isFavourite ->
                        onEvent(FavouriteChange(isFavourite, feedingPoint))
                    },
                    onShowOnMap = {
                        navigator.navigate(
                            TabsRoute.Home.withArg(
                                Arguments.FORCED_FEEDING_POINT_ID to feedingPoint.id,
                                Arguments.ANIMAL_TYPE to feedingPoint.animalType.name
                            )
                        )
                    }
                )
            }
        },
        sheetControls = {
            FeedingPointActionButton(
                alpha = buttonAlpha,
                enabled = state.showingFeedingPoint?.feedStatus == FeedStatus.RED &&
                    state.feedState.feedPoint == null,
                onClick = { onWillFeedEvent(WillFeedClicked) },
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
            ScreenContent(state.favourites, padding, onEvent)
        }
    }

    WillFeedDialog(
        onAgreeClick = {
            scope.launch { bottomSheetState.hide() }
            state.showingFeedingPoint?.id?.let { onFeedingEvent(FeedingEvent.Start(it)) }
        }
    )
    OnFeedingConfirmationState(state.feedState.feedingConfirmationState, navigator, onFeedingEvent)
}

@Composable
private fun OnFeedingConfirmationState(
    feedingConfirmationState: FeedingConfirmationState,
    navigator: Navigator,
    onFeedingEvent: (FeedingEvent) -> Unit
) {
    when (feedingConfirmationState) {
        FeedingStarted -> {
            navigator.navigateTo(TabsRoute.Home.name)
            onFeedingEvent(FeedingEvent.Reset)
        }
        FeedingWasAlreadyBooked -> {
            AnimealAlertDialog(
                title = stringResource(id = R.string.feeding_point_expired_description),
                acceptText = stringResource(id = R.string.feeding_point_expired_accept),
                onConfirm = {
                    onFeedingEvent(FeedingEvent.Reset)
                }
            )
        }
        else -> {}
    }
}

@Composable
private fun ScreenContent(
    favourites: ImmutableList<FeedingPointModel>,
    padding: PaddingValues,
    onEvent: (FavouritesScreenEvent) -> Unit,
) {
    when {
        favourites.isEmpty() -> {
            EmptyState(padding)
        }

        else -> {
            FavouritesList(padding, favourites, onEvent)
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
    favourites: List<FeedingPointModel>,
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
                FeedingPointItem(
                    title = feedingPoint.title,
                    status = feedingPoint.feedStatus,
                    isFavourite = feedingPoint.isFavourite,
                    onFavouriteChange = { isFavourite ->
                        onEvent(FavouriteChange(isFavourite, feedingPoint))
                    },
                    imageUrl = feedingPoint.image,
                    onClick = { onEvent(FeedingPointSelected(feedingPoint)) }
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun FavouritesScreenPreview() {
    val title = "FeedSpot"
    val favourites = listOf(
        FeedingPointModel(
            FeedingPoint(
                id = "0",
                title = title,
                description = "Hungry dog",
                city = "Minsk",
                animalStatus = AnimalState.RED,
                animalType = AnimalType.Dogs,
                isFavourite = true,
                location = MapLocation.Tbilisi,
                images = emptyList(),
            )
        ),
    )
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                favourites = favourites.toImmutableList(),
            ),
            AnimealBottomSheetState(AnimealBottomSheetValue.Hidden),
            {},
            {},
            {},
            {}
        )
    }
}

@AnimealPreview
@Composable
private fun FavouritesScreenEmptyPreview() {
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                feedState = FeedState(),
                favourites = persistentListOf()
            ),
            AnimealBottomSheetState(AnimealBottomSheetValue.Hidden),
            {},
            {},
            {},
            {}
        )
    }
}