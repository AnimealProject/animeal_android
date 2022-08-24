package com.epmedu.animeal.tabs.presentation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.epmedu.animeal.tabs.presentation.HomeBottomSheetValue.Expanded
import com.epmedu.animeal.tabs.presentation.HomeBottomSheetValue.Hidden
import com.epmedu.animeal.tabs.presentation.HomeBottomSheetValue.Shown
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
class HomeBottomSheetState(
    initialValue: HomeBottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    val confirmStateChange: (HomeBottomSheetValue) -> Boolean = { true }
) : SwipeableState<HomeBottomSheetValue>(
    initialValue = initialValue,
    animationSpec = animationSpec,
    confirmStateChange = confirmStateChange
) {
    val isVisible: Boolean
        get() = currentValue != Hidden

    val isHidden: Boolean
        get() = currentValue == Hidden

    val isShowing: Boolean
        get() = progress.to == Shown && progress.from == Hidden

    val isHiding: Boolean
        get() = progress.to == Hidden

    val isExpanding: Boolean
        get() = progress.to == Expanded

    val isCollapsing: Boolean
        get() = progress.from == Expanded && progress.to == Shown

    suspend fun show() = animateTo(Shown)

    suspend fun expand() = animateTo(Expanded)

    suspend fun hide() = animateTo(Hidden)

    companion object {
        fun Saver(
            animationSpec: AnimationSpec<Float>,
            confirmStateChange: (HomeBottomSheetValue) -> Boolean
        ): Saver<HomeBottomSheetState, *> = Saver(
            save = { it.currentValue },
            restore = {
                HomeBottomSheetState(
                    initialValue = it,
                    animationSpec = animationSpec,
                    confirmStateChange = confirmStateChange
                )
            }
        )
    }
}

enum class HomeBottomSheetValue {
    Expanded,
    Hidden,
    Shown,
}

@Composable
@ExperimentalMaterialApi
fun rememberHomeBottomSheetState(
    initialValue: HomeBottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (HomeBottomSheetValue) -> Boolean = { true }
): HomeBottomSheetState {
    return rememberSaveable(
        initialValue,
        animationSpec,
        confirmStateChange,
        saver = HomeBottomSheetState.Saver(
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    ) {
        HomeBottomSheetState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheetLayout(
    modifier: Modifier = Modifier,
    sheetState: HomeBottomSheetState = rememberHomeBottomSheetState(Hidden),
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
                            if (sheetState.confirmStateChange(Hidden)) {
                                scope.launch { sheetState.hide() }
                            }
                            true
                        }
                        if (sheetState.currentValue == Shown) {
                            expand {
                                if (sheetState.confirmStateChange(Expanded)) {
                                    scope.launch { sheetState.expand() }
                                }
                                true
                            }
                        } else {
                            collapse {
                                if (sheetState.confirmStateChange(Shown)) {
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
            Column(content = sheetContent)
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
            fullHeight to Hidden,
            shownHeight to Shown,
            max(0f, fullHeight - sheetHeight) to Expanded
        )

        Modifier.swipeable(
            state = sheetState,
            anchors = anchors,
            orientation = Orientation.Vertical,
            enabled = sheetState.currentValue != Hidden,
            resistance = null
        )
    } else {
        Modifier
    }

    return this.then(modifier)
}