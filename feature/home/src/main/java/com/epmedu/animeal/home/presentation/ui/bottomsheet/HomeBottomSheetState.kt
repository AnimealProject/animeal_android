package com.epmedu.animeal.home.presentation.ui.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.epmedu.animeal.home.presentation.ui.bottomsheet.HomeBottomSheetValue.*

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
