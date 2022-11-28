package com.epmedu.animeal.home.presentation.ui.bottomsheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.foundation.bottombar.LocalBottomBarVisibilityController
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.home.data.model.enum.AnimalState
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeBottomSheet(
    homeState: HomeState,
    bottomSheetState: HomeBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    content: @Composable () -> Unit,
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

    HomeBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            homeState.currentFeedingPoint?.let { feedingPoint ->
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
                enabled = homeState.currentFeedingPoint?.animalStatus == AnimalState.RED,
                onClick = { onScreenEvent(HomeScreenEvent.ShowWillFeedDialog) }
            )
        },
        content = content,
    )
}

@Composable
private fun FeedingPointActionButton(
    alpha: Float,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    AnimealButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .alpha(alpha),
        enabled = enabled,
        text = stringResource(R.string.i_will_feed),
        onClick = onClick,
    )
}
