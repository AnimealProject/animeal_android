package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.whenCreated
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent.ShowCurrentFeedingPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(lifecycleOwner: LifecycleOwner) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(
        initialValue = AnimealBottomSheetValue.Hidden
    )

    HomeScreenUI(
        state = state,
        bottomSheetState = bottomSheetState,
        onScreenEvent = viewModel::handleEvents
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ShowCurrentFeedingPoint -> {
                    launch {
                        if (bottomSheetState.isHidden) {
                            if (state.feedingRouteState is FeedingRouteState.Active) {
                                bottomSheetState.expand()
                            } else {
                                bottomSheetState.show()
                            }
                        }
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
