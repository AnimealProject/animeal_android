package com.epmedu.animeal.home.presentation.ui.bottomsheet

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.roundToInt

@Suppress("LongParameterList", "LongMethod")
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeBottomSheetLayout(
    modifier: Modifier = Modifier,
    sheetState: HomeBottomSheetState = rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden),
    sheetShape: Shape = MaterialTheme.shapes.large,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetControls: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    BoxWithConstraints(modifier) {
        val fullHeight = constraints.maxHeight.toFloat()
        val shownHeight = with(LocalDensity.current) { fullHeight - 220.dp.toPx() }
        val sheetHeightState = remember { mutableStateOf<Float?>(null) }

        Box(Modifier.fillMaxSize()) {
            content()
        }
        Surface(
            Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(0, sheetState.offset.value.roundToInt())
                }
                .bottomSheetSwipeable(sheetState, fullHeight, shownHeight, sheetHeightState)
                .onGloballyPositioned {
                    sheetHeightState.value = it.size.height.toFloat()
                }
                .semantics {
                    if (sheetState.isVisible) {
                        dismiss {
                            if (sheetState.confirmStateChange(HomeBottomSheetValue.Hidden)) {
                                scope.launch { sheetState.hide() }
                            }
                            true
                        }
                        if (sheetState.currentValue == HomeBottomSheetValue.Shown) {
                            expand {
                                if (sheetState.confirmStateChange(HomeBottomSheetValue.Expanded)) {
                                    scope.launch { sheetState.expand() }
                                }
                                true
                            }
                        } else {
                            collapse {
                                if (sheetState.confirmStateChange(HomeBottomSheetValue.Shown)) {
                                    scope.launch { sheetState.show() }
                                }
                                true
                            }
                        }
                    }
                },
            shape = sheetShape,
            elevation = sheetElevation,
            color = sheetBackgroundColor,
            contentColor = sheetContentColor
        ) {
            Column(
                modifier = Modifier.defaultMinSize(minHeight = 1.dp),
                content = sheetContent
            )
        }

        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            sheetControls()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun Modifier.bottomSheetSwipeable(
    sheetState: HomeBottomSheetState,
    fullHeight: Float,
    shownHeight: Float,
    sheetHeightState: State<Float?>
): Modifier {
    val sheetHeight = sheetHeightState.value
    val modifier = if (sheetHeight != null) {
        val anchors = mapOf(
            fullHeight to HomeBottomSheetValue.Hidden,
            shownHeight to HomeBottomSheetValue.Shown,
            max(0f, fullHeight - sheetHeight) to HomeBottomSheetValue.Expanded
        )

        Modifier.swipeable(
            state = sheetState,
            anchors = anchors,
            orientation = Orientation.Vertical,
            enabled = sheetState.currentValue != HomeBottomSheetValue.Hidden,
            resistance = null
        )
    } else {
        Modifier
    }

    return this.then(modifier)
}
