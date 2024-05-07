package com.epmedu.animeal.feedings.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.ErrorShown
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.UpdateCurrentFeeding
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingsViewModel
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue.Hidden
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.foundation.loading.FullScreenLoading
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedingsScreen() {
    val context = LocalContext.current

    val viewModel = hiltViewModel<FeedingsViewModel>()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(initialValue = Hidden)
    val coroutineScope = rememberCoroutineScope()

    BottomBarVisibility(BottomBarVisibilityState.HIDDEN)

    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch { bottomSheetState.hide() }
    }

    LaunchedEffect(bottomSheetState.isHidden) {
        if (bottomSheetState.isHidden) viewModel.handleEvents(UpdateCurrentFeeding(null))
    }

    LaunchedEffect(state.currentFeeding) {
        coroutineScope.launch {
            when (state.currentFeeding) {
                null -> bottomSheetState.hide()
                else -> bottomSheetState.expand()
            }
        }
    }

    FeedingsScreenUI(
        state = state,
        bottomSheetState = bottomSheetState,
        onBack = navigator::popBackStack,
        onEvent = viewModel::handleEvents
    )

    if (state.isLockedAndLoading) FullScreenLoading()

    if (state.isError) {
        Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
        viewModel.handleEvents(ErrorShown)
    }
}