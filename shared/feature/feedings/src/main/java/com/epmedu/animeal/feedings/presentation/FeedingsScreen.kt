package com.epmedu.animeal.feedings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingsViewModel
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun FeedingsScreen() {
    val viewModel = hiltViewModel<FeedingsViewModel>()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()

    BottomBarVisibility(BottomBarVisibilityState.HIDDEN)

    FeedingsScreenUI(
        state = state,
        onBack = navigator::popBackStack
    )
}