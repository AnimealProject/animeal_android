package com.epmedu.animeal.foundation.bottombar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState

val LocalBottomBarVisibilityController =
    staticCompositionLocalOf<(BottomBarVisibilityState) -> Unit> { {} }

@Composable
fun BottomBarVisibility(state: BottomBarVisibilityState) {
    val bottomBarVisibilityController = LocalBottomBarVisibilityController.current

    LaunchedEffect(Unit) {
        bottomBarVisibilityController(state)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomBarVisibility(state: AnimealBottomSheetState) {
    val bottomBarVisibilityController = LocalBottomBarVisibilityController.current

    with(state) {
        LaunchedEffect(progress, currentValue) {
            val showBottomBar = isShowing.not() && isExpanding.not() && isHidden

            bottomBarVisibilityController(BottomBarVisibilityState.ofBoolean(showBottomBar))
        }
    }
}