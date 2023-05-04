package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.whenCreated
import com.epmedu.animeal.camera.presentation.CameraView
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent.ShowCurrentFeedingPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(
        initialValue = AnimealBottomSheetValue.Hidden
    )

    if (state.cameraState is CameraState.Enabled) {
        CameraView(
            onImageCapture = {
                viewModel.handleEvents(HomeScreenEvent.CameraEvent.TakeNewPhoto(it))
            },
            onError = {
                viewModel.handleEvents(HomeScreenEvent.CameraEvent.CloseCamera)
            },
            onBackPress = {
                viewModel.handleEvents(HomeScreenEvent.CameraEvent.CloseCamera)
            }
        )
    } else {
        HomeScreenUI(
            state = state,
            bottomSheetState = bottomSheetState,
            onScreenEvent = viewModel::handleEvents,
            onWillFeedEvent = viewModel::handleWillFeedEvent
        )
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ShowCurrentFeedingPoint -> {
                    launch {
                        if (bottomSheetState.isHidden) {
                            if (state.feedingRouteState is com.epmedu.animeal.router.presentation.FeedingRouteState.Active) {
                                bottomSheetState.expand()
                            } else {
                                bottomSheetState.show()
                            }
                        }
                    }
                }
                HomeViewModelEvent.MinimiseBottomSheet -> {
                    if (bottomSheetState.isExpanding) {
                        bottomSheetState.show()
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.whenCreated {
            viewModel.handleEvents(HomeScreenEvent.ScreenDisplayed)
        }
    }
}
