package com.epmedu.animeal.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.feeding.data.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.foundation.dialog.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.dialog.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.dialog.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.home.presentation.HomeScreenEvent.*
import com.epmedu.animeal.home.presentation.ui.CheckLocationPermission
import com.epmedu.animeal.home.presentation.ui.GeoLocationFloatingActionButton
import com.epmedu.animeal.home.presentation.ui.MapboxMap
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: AnimealBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit
) {
    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()

    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    BackHandler(enabled = state.willFeedState.isDialogShowing) {
        scope.launch { onScreenEvent(DismissWillFeedDialog) }
    }

    CheckLocationPermission {
        AnimealBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            sheetContent = {
                state.currentFeedingPoint?.let { feedingPoint ->
                    FeedingPointSheetContent(
                        feedingPoint = FeedingPointModel(feedingPoint),
                        contentAlpha = contentAlpha,
                        onFavouriteChange = {
                            onScreenEvent(FeedingPointFavouriteChange(isFavourite = it))
                        }
                    )
                }
            },
            sheetControls = {
                FeedingPointActionButton(
                    alpha = buttonAlpha,
                    enabled = state.currentFeedingPoint?.animalStatus == AnimalState.RED,
                    onClick = { onScreenEvent(ShowWillFeedDialog) }
                )
            }
        ) {
            MapContent(
                state = state,
                onFeedingPointSelect = { onScreenEvent(FeedingPointSelected(it.id)) },
                onGeolocationClick = { onScreenEvent(UserCurrentGeolocationRequest) }
            )
        }
    }

    WillFeedConfirmationDialog(state, onScreenEvent)
}

@Composable
private fun MapContent(
    state: HomeState,
    onFeedingPointSelect: (point: FeedingPointModel) -> Unit,
    onGeolocationClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
            state = state,
            onFeedingPointClick = onFeedingPointSelect
        )
        AnimealSwitch(
            modifier = Modifier
                .statusBarsPadding()
                .align(alignment = Alignment.TopCenter)
                .padding(top = 24.dp),
            onSelectTab = {}
        )
        GeoLocationFloatingActionButton(
            modifier = Modifier
                .padding(
                    bottom = bottomBarHeight + 24.dp,
                    end = 24.dp
                )
                .align(alignment = Alignment.BottomEnd),
            onClick = onGeolocationClick
        )
    }
}

@Composable
private fun WillFeedConfirmationDialog(state: HomeState, onScreenEvent: (HomeScreenEvent) -> Unit) {
    if (state.willFeedState.isDialogShowing) {
        FeedConfirmationDialog(
            onAgreeClick = { onScreenEvent(DismissWillFeedDialog) },
            onCancelClick = { onScreenEvent(DismissWillFeedDialog) }
        )
    }
}