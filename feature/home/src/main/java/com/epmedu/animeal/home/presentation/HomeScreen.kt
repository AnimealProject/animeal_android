package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetValue
import com.epmedu.animeal.home.presentation.ui.rememberHomeBottomSheetState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent.ShowCurrentFeedingPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    val bottomSheetState = rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    HomeScreenUI(
        homeViewModel = viewModel,
        bottomSheetState = bottomSheetState,
        onScreenEvent = viewModel::handleEvents
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ShowCurrentFeedingPoint -> {
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
