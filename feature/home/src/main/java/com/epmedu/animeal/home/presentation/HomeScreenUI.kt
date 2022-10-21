package com.epmedu.animeal.home.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.foundation.bottombar.LocalBottomBarVisibilityController
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.home.presentation.HomeScreenEvent.*
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.ui.*
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: HomeBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onWillFeedClick: () -> Unit
) {
    val changeBottomBarVisibilityState = LocalBottomBarVisibilityController.current

    LaunchedEffect(bottomSheetState.progress) {
        val showBottomBar = if (bottomSheetState.isShowing) false else bottomSheetState.isHidden

        changeBottomBarVisibilityState(BottomBarVisibilityState.ofBoolean(showBottomBar))
    }

    val contentAlpha: Float by animateFloatAsState(
        targetValue = when {
            bottomSheetState.isExpanding -> bottomSheetState.progress.fraction
            bottomSheetState.isCollapsing -> 1f - bottomSheetState.progress.fraction
            else -> 0f
        }
    )

    val buttonAlpha: Float by animateFloatAsState(
        targetValue = when {
            bottomSheetState.isShowing -> bottomSheetState.progress.fraction
            bottomSheetState.isHiding -> 1f - bottomSheetState.progress.fraction
            else -> 1f
        }
    )

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
                            onScreenEvent(FeedingPointFavouriteChange(isFavourite = it))
                        }
                    )
                }
            },
            sheetControls = {
                FeedingPointActionButton(
                    alpha = buttonAlpha,
                    onClick = onWillFeedClick
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
}

@Composable
private fun MapContent(
    state: HomeState,
    onFeedingPointSelect: (point: FeedingPointUi) -> Unit,
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
