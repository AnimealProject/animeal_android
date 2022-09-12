package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetValue
import com.epmedu.animeal.home.presentation.ui.rememberHomeBottomSheetState
import com.epmedu.animeal.home.presentation.viewmodel.HomeEvent.ShowCurrentFeedSpotInformation
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(onChangeBottomNavBarVisibility: (Boolean) -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()

    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    HomeScreenUI(
        state = state,
        bottomSheetState = bottomSheetState,
        onChangeBottomNavBarVisibility = onChangeBottomNavBarVisibility,
        onEvent = { event ->
            viewModel.handleEvents(event)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ShowCurrentFeedSpotInformation -> {
                    scope.launch {
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
