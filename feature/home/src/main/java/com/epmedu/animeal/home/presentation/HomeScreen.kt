package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetValue
import com.epmedu.animeal.home.presentation.ui.rememberHomeBottomSheetState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent.ShowCurrentFeedingPoint
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.route.WillFeedRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val navigator = LocalNavigator.currentOrThrow

    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden)

    HomeScreenUI(
        state = state,
        bottomSheetState = bottomSheetState,
        onScreenEvent = viewModel::handleEvents
    ) {
        navigator.navigate(WillFeedRoute.Confirmation.name)
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ShowCurrentFeedingPoint -> {
                    launch {
                        if (bottomSheetState.isHidden) {
                            bottomSheetState.show()
                        } else {
                            bottomSheetState.hide()
                        }
                    }
                }
            }
        }
    }
}
