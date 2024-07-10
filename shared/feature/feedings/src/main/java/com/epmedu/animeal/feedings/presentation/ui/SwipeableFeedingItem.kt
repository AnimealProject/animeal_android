package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalFoundationApi::class)
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
    val (swipeOffset, velocity) = with(LocalDensity.current) {
        -160.dp.toPx() to 125.dp.toPx()
    }
    val anchoredDraggableState = remember { createAnchoredDraggableState(swipeOffset, velocity) }
    val buttonsAlpha = anchoredDraggableState.offset / swipeOffset
    val swipeToIdle: suspend () -> Unit = {
        anchoredDraggableState.animateTo(SwipeableFeedingItemState.Idle)
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

    LaunchedEffect(anchoredDraggableState) {
        snapshotFlow { anchoredDraggableState.targetValue }.collect { itemState ->
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
                .offset {
                    IntOffset(
                        x = when {
                            anchoredDraggableState.offset.isNaN() -> 0
                            else -> anchoredDraggableState.offset.roundToInt()
                        },
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = anchoredDraggableState,
                    orientation = Orientation.Horizontal
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun createAnchoredDraggableState(
    swipeOffset: Float,
    velocity: Float
): AnchoredDraggableState<SwipeableFeedingItemState> {
    val anchors = DraggableAnchors {
        SwipeableFeedingItemState.Idle at 0f
        SwipeableFeedingItemState.Swiped at swipeOffset
    }

    return AnchoredDraggableState(
        initialValue = SwipeableFeedingItemState.Idle,
        anchors = anchors,
        positionalThreshold = { distance -> distance / 2 },
        velocityThreshold = { velocity },
        animationSpec = SpringSpec()
    )
}

internal enum class SwipeableFeedingItemState {
    Idle, Swiped
}