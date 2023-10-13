package com.epmedu.animeal.feedings.presentation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.feedings.presentation.model.FeedingsViewModel
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun FeedingsScreen() {
    val feedingsViewModel = hiltViewModel<FeedingsViewModel>()
    val navigator = LocalNavigator.currentOrThrow
    val state by feedingsViewModel.stateFlow.collectAsState()
    BottomBarVisibility(BottomBarVisibilityState.HIDDEN)
    FeedingsScreenUI(
        feedings = state.feedings,
        onBack = navigator::popBackStack,
        onEvent = feedingsViewModel::handleEvents,
    )
}