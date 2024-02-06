package com.epmedu.animeal.foundation.tabs

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

private const val INDICATOR_TRANSITION_LABEL = "TAB_INDICATOR"
private const val INDICATOR_LEFT_TRANSITION_LABEL = "TAB_INDICATOR_LEFT"
private const val INDICATOR_RIGHT_TRANSITION_LABEL = "TAB_INDICATOR_RIGHT"

/**
 * Shows an indicator for the tab based on a given Enum.
 * @param tabPositions The list of [TabPosition]s from a [TabRow].
 * @param type The [Enum] type that is currently selected.
 */
@Composable
fun <T : Enum<T>> TabIndicator(
    tabPositions: List<TabPosition>,
    type: T,
) {
    val transition = updateTransition(
        targetState = type,
        label = INDICATOR_TRANSITION_LABEL
    )

    val indicatorLeft by transition.animateDp(
        label = INDICATOR_LEFT_TRANSITION_LABEL
    ) { page ->
        tabPositions[page.ordinal].left
    }

    val indicatorRight by transition.animateDp(
        label = INDICATOR_RIGHT_TRANSITION_LABEL
    ) { page ->
        tabPositions[page.ordinal].right
    }

    Box(
        Modifier
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .fillMaxHeight()
            .padding(all = 2.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(9.dp)
            )
            .zIndex(-1f)
    )
}