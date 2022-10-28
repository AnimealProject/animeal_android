package com.epmedu.animeal.home.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetValue
import com.epmedu.animeal.home.presentation.ui.rememberHomeBottomSheetState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent.ShowCurrentFeedingPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    val state by viewModel.stateFlow.collectAsState()
    var feedDialogState by remember { mutableStateOf(false) }
    val bottomSheetState = rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden)

    HomeScreenUI(
        state = state,
        bottomSheetState = bottomSheetState,
        onScreenEvent = viewModel::handleEvents,
        onWillFeedClick = {
            feedDialogState = true
        }
    )

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

    if (feedDialogState) {
        FeedConfirmationDialog(
            onAgreeClick = { feedDialogState = false },
            onCancelClick = { feedDialogState = false }
        )
    }
}
