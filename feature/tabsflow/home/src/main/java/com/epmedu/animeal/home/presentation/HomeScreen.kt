package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.withCreated
import com.epmedu.animeal.camera.presentation.CameraView
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedViewModel
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.home.presentation.HomeScreenEvent.CameraEvent
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.epmedu.animeal.router.presentation.FeedingRouteState.Active
import com.epmedu.animeal.router.presentation.FeedingRouteState.Disabled
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val willFeedViewModel = hiltViewModel<WillFeedViewModel>()
    val state by homeViewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(
        initialValue = AnimealBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    val minimizeBottomSheet: () -> Unit = {
        if (state.feedingRouteState is Disabled && bottomSheetState.isExpanding) {
            scope.launch { bottomSheetState.show() }
        }
    }

    if (state.cameraState is CameraState.Enabled) {
        CameraView(
            onImageCapture = { homeViewModel.handleEvents(CameraEvent.TakeNewPhoto(it)) },
            onError = { homeViewModel.handleEvents(CameraEvent.CloseCamera) },
            onBackPress = { homeViewModel.handleEvents(CameraEvent.CloseCamera) }
        )
    } else {
        HomeScreenUI(
            state = state,
            bottomSheetState = bottomSheetState,
            onCameraChange = minimizeBottomSheet,
            onScreenEvent = homeViewModel::handleEvents,
            onPermissionsEvent = homeViewModel::handlePermissionsEvent,
            onRouteEvent = homeViewModel::handleRouteEvent,
            onFeedingEvent = homeViewModel::handleFeedingEvent,
            onFeedingPointEvent = homeViewModel::handleFeedingPointEvent,
            onTimerEvent = homeViewModel::handleTimerEvent,
            onWillFeedEvent = willFeedViewModel::handleEvent
        )
    }

    LaunchedEffect(key1 = state.feedingPointState.currentFeedingPoint) {
        when (state.feedingPointState.currentFeedingPoint) {
            null -> {
                bottomSheetState.hide()
            }

            else -> {
                launch {
                    if (bottomSheetState.isHidden) {
                        when (state.feedingRouteState) {
                            is Active -> bottomSheetState.expand()
                            is Disabled -> bottomSheetState.show()
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.withCreated {
            homeViewModel.handleEvents(HomeScreenEvent.ScreenDisplayed)
        }
    }
}
