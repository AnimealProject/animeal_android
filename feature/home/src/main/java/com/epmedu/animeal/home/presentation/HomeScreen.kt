package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent.ShowCurrentFeedingPoint
import com.epmedu.animeal.home.presentation.viewmodel.TimerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(onTimerEvent: (TimerEvent) -> Unit, timerState: TimerState) {
    val viewModel = hiltViewModel<HomeViewModel>()

    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(AnimealBottomSheetValue.Hidden)

    HomeScreenUI(
        state = state,
        timerState = timerState,
        bottomSheetState = bottomSheetState,
        onScreenEvent = viewModel::handleEvents,
        onTimerEvent = onTimerEvent
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ShowCurrentFeedingPoint -> {
                    launch {
                        if (bottomSheetState.isHidden) {
                            bottomSheetState.show()
                        }
                    }
                }
            }
        }
    }
}
