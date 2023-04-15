package com.epmedu.animeal.tabs.search.presentation.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.viewmodel.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
internal fun SearchScreenUi(
    state: SearchState,
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (SearchScreenEvent) -> Unit
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
        scope,
        onEvent
    )
}

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
private fun ScreenScaffold(
    bottomSheetState: AnimealBottomSheetState,
    state: SearchState,
    contentAlpha: Float,
    buttonAlpha: Float,
    scope: CoroutineScope,
    onEvent: (SearchScreenEvent) -> Unit
) {
    AnimealBottomSheetLayout(
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 20.dp),
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        skipHalfExpanded = true,
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
                        onEvent(SearchScreenEvent.FavouriteChange(isFavourite, feedingPoint))
                    },
                    onShowOnMap = {
                        onEvent(SearchScreenEvent.ShowOnMap(feedingPoint.id, feedingPoint.animalType))
                    }
                )
            }
        },
        sheetControls = {
            FeedingPointActionButton(
                alpha = buttonAlpha,
                enabled = state.showingFeedingPoint?.animalStatus == AnimalState.RED,
                onClick = { onEvent(SearchScreenEvent.ShowWillFeedDialog) },
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.secondaryVariant),
        ) { padding ->
            ScreenContent(state, scope, onEvent)
        }
    }

    WillFeedConfirmationDialog(state, onEvent)
}

@Composable
private fun HandleFeedingPointSheetHiddenState(
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isHidden }.distinctUntilChanged().filter { it }.collect {
            onEvent(SearchScreenEvent.FeedingPointHidden)
        }
    }
}

@Composable
internal fun WillFeedConfirmationDialog(
    state: SearchState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    if (state.showingWillFeedDialog) {
        FeedConfirmationDialog(
            onAgreeClick = { onEvent(SearchScreenEvent.DismissWillFeedDialog) },
            onCancelClick = { onEvent(SearchScreenEvent.DismissWillFeedDialog) }
        )
    }
}

@Composable
private fun ScreenContent(
    state: SearchState,
    scope: CoroutineScope,
    onEvent: (SearchScreenEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        AnimalTypeHorizontalPager(
            tabRowHorizontalPadding = 30.dp,
            scope = scope
        ) { animalType ->

            SearchFeedingPointsUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = bottomBarHeight,
                        top = 12.dp
                    ),
                feedingPoints = state.getFeedingPointsBy(animalType),
                animalType = animalType,
                onEvent = onEvent,
                query = state.getQueryBy(animalType),
            )
        }
    }
}

@AnimealPreview
@Composable
private fun SearchScreenUiPreview() {
    AnimealTheme {
        SearchScreenUi(
            SearchState(),
            AnimealBottomSheetState(AnimealBottomSheetValue.Hidden),
        ) {}
    }
}