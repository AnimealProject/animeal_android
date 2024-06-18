package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SwipeableFeedingItem(
    feedingModel: FeedingModel,
    currentlySwipedItemId: String?,
    isTappedAnywhere: Boolean,
    onTappedAnywhereHandle: () -> Unit,
    onItemClick: () -> Unit,
    onApproveClick: () -> Unit,
    onRejectClick: () -> Unit,
    onSwipe: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(initialValue = SwipeableFeedingItemState.Idle)
    val swipeOffset = with(LocalDensity.current) { -160.dp.toPx() }
    val anchors = mapOf(
        0f to SwipeableFeedingItemState.Idle,
        swipeOffset to SwipeableFeedingItemState.Swiped
    )
    val buttonsAlpha = swipeableState.offset.value / swipeOffset
    val swipeToIdle: suspend () -> Unit = {
        swipeableState.animateTo(SwipeableFeedingItemState.Idle)
    }

    LaunchedEffect(currentlySwipedItemId) {
        if (currentlySwipedItemId != feedingModel.id) {
            coroutineScope.launch { swipeToIdle() }
        }
    }

    LaunchedEffect(isTappedAnywhere) {
        if (isTappedAnywhere) {
            coroutineScope.launch { swipeToIdle() }
            onTappedAnywhereHandle()
        }
    }

    LaunchedEffect(swipeableState) {
        snapshotFlow { swipeableState.targetValue }.collect { itemState ->
            if (itemState == SwipeableFeedingItemState.Swiped) {
                onSwipe()
            }
        }
    }

    Box(
        modifier = Modifier.height(104.dp)
    ) {
        FeedingItemQuickButtons(
            onApproveClick = onApproveClick,
            onRejectClick = onRejectClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 18.dp)
                .alpha(buttonsAlpha)
        )

        FeedingItem(
            feedingModel = feedingModel,
            onClick = onItemClick,
            modifier = Modifier
                .offset { IntOffset(x = swipeableState.offset.value.roundToInt(), y = 0) }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Horizontal
                )
        )
    }
}

internal enum class SwipeableFeedingItemState {
    Idle, Swiped
}