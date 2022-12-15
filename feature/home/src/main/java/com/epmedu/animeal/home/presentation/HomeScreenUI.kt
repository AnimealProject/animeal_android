package com.epmedu.animeal.home.presentation

import android.os.CountDownTimer
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.extensions.formatNumberToHourMin
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.foundation.bottombar.LocalBottomBarVisibilityController
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.model.RouteResult
import com.epmedu.animeal.home.presentation.model.WillFeedState
import com.epmedu.animeal.home.presentation.ui.CheckLocationPermission
import com.epmedu.animeal.home.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.home.presentation.ui.GeoLocationFloatingActionButton
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetLayout
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetState
import com.epmedu.animeal.home.presentation.ui.MapboxMap
import com.epmedu.animeal.home.presentation.ui.map.RouteTopBar
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: HomeBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
) {
    val changeBottomBarVisibilityState = LocalBottomBarVisibilityController.current

    LaunchedEffect(bottomSheetState.progress) {
        val showBottomBar = if (bottomSheetState.isShowing) false else bottomSheetState.isHidden
        changeBottomBarVisibilityState(BottomBarVisibilityState.ofBoolean(showBottomBar))
    }

    val contentAlpha: Float by animateFloatAsState(targetValue = getContentAlpha(bottomSheetState))
    val buttonAlpha: Float by animateFloatAsState(targetValue = getButtonAlpha(bottomSheetState))

    val scope = rememberCoroutineScope()
    val timerHandler: CountDownTimer = remember { getTimeHandler(onScreenEvent) }

    OnBackHandling(scope = scope, bottomSheetState = bottomSheetState, state = state, onScreenEvent = onScreenEvent)

    CheckLocationPermission {
        HomeBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            sheetContent = {
                state.currentFeedingPoint?.let { feedingPoint ->
                    FeedingPointSheetContent(
                        feedingPoint = FeedingPointUi(feedingPoint),
                        contentAlpha = contentAlpha,
                        onFavouriteChange = {
                            onScreenEvent(HomeScreenEvent.FeedingPointFavouriteChange(isFavourite = it))
                        }
                    )
                }
            },
            sheetControls = {
                FeedingPointActionButton(
                    alpha = buttonAlpha,
                    onClick = { onScreenEvent(HomeScreenEvent.WillFeedEvent.ShowWillFeedDialog) }
                )
            }
        ) {
            MapContent(
                state = state,
                onFeedingPointSelect = { onScreenEvent(HomeScreenEvent.FeedingPointSelected(it.id)) },
                onGeolocationClick = { onScreenEvent(HomeScreenEvent.UserCurrentGeolocationRequest) },
                onMapInteraction = {
                    if (bottomSheetState.isExpanding && !state.feedingRouteState.isRouteActive) {
                        scope.launch { bottomSheetState.show() }
                    }
                },
                onCancelRouteClick = {
                    onScreenEvent(HomeScreenEvent.RouteEvent.FeedingRouteCancellationRequest)
                    timerHandler.cancel()
                },
                onRouteResult = { result ->
                    onScreenEvent(HomeScreenEvent.RouteEvent.FeedingRouteUpdateRequest(result))
                }
            )
        }

        WillFeedConfirmationDialog(state, onScreenEvent) {
            timerHandler.start()
        }
    }
}

@Composable
private fun MapContent(
    state: HomeState,
    onFeedingPointSelect: (point: FeedingPointUi) -> Unit,
    onGeolocationClick: () -> Unit,
    onMapInteraction: () -> Unit,
    onCancelRouteClick: () -> Unit,
    onRouteResult: (result: RouteResult) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
            state = state,
            onFeedingPointClick = onFeedingPointSelect,
            onMapInteraction = onMapInteraction,
            onRouteResult = onRouteResult
        )
        if (!state.feedingRouteState.isRouteActive) {
            AnimealSwitch(
                modifier = Modifier
                    .statusBarsPadding()
                    .align(alignment = Alignment.TopCenter)
                    .padding(top = 24.dp),
                onSelectTab = {}
            )
        } else {
            RouteTopBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp)
                    .padding(horizontal = 20.dp),
                timeLeft = state.feedingRouteState.timeLeft?.formatNumberToHourMin()
                    ?: stringResource(R.string.calculating_route),
                distanceLeft = state.feedingRouteState.distanceLeft?.run { " • $this" } ?: "",
                onCancelClick = onCancelRouteClick
            )
        }
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
private fun FeedingPointActionButton(
    alpha: Float,
    onClick: () -> Unit
) {
    AnimealButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .alpha(alpha),
        text = stringResource(R.string.i_will_feed),
        onClick = onClick,
    )
}

@Composable
private fun WillFeedConfirmationDialog(
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onAgreeClick: () -> Unit
) {
    if (state.willFeedState is WillFeedState.Showing) {
        FeedConfirmationDialog(
            onAgreeClick = {
                onScreenEvent(HomeScreenEvent.WillFeedEvent.DismissWillFeedDialog)
                onScreenEvent(HomeScreenEvent.RouteEvent.FeedingRouteStartRequest)
                onAgreeClick.invoke()
            },
            onCancelClick = { onScreenEvent(HomeScreenEvent.WillFeedEvent.DismissWillFeedDialog) }
        )
    }
}

@Composable
fun OnBackHandling(
    scope: CoroutineScope,
    bottomSheetState: HomeBottomSheetState,
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit
) {
    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    BackHandler(enabled = state.willFeedState is WillFeedState.Showing) {
        scope.launch { onScreenEvent(HomeScreenEvent.WillFeedEvent.DismissWillFeedDialog) }
    }
}

private fun getTimeHandler(onScreenEvent: (HomeScreenEvent) -> Unit) =
    object : CountDownTimer(HOUR_IN_MILLIS, MINUTE_IN_MILLIS) {
        override fun onTick(timeLeftInMillis: Long) {
            onScreenEvent(HomeScreenEvent.RouteEvent.FeedingTimerUpdateRequest(timeLeftInMillis))
        }

        override fun onFinish() {
            cancel()
        }
    }

@OptIn(ExperimentalMaterialApi::class)
private fun getButtonAlpha(bottomSheetState: HomeBottomSheetState) = when {
    bottomSheetState.isExpanding -> bottomSheetState.progress.fraction
    bottomSheetState.isCollapsing -> 1f - bottomSheetState.progress.fraction
    else -> 0f
}

@OptIn(ExperimentalMaterialApi::class)
private fun getContentAlpha(bottomSheetState: HomeBottomSheetState) = when {
    bottomSheetState.isShowing -> bottomSheetState.progress.fraction
    bottomSheetState.isHiding -> 1f - bottomSheetState.progress.fraction
    else -> 1f
}
