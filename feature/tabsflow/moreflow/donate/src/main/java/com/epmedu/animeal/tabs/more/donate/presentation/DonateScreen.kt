package com.epmedu.animeal.tabs.more.donate.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.more.donate.presentation.viewmodel.DonateViewModel

@Composable
fun DonateScreen() {
    val viewModel = hiltViewModel<DonateViewModel>()

    BottomBarVisibility(HIDDEN)

    val navigator = LocalNavigator.currentOrThrow

    val state by viewModel.stateFlow.collectAsState()

    DonateScreenUI(
        state = state,
        onEvent = viewModel::handleEvent,
        onBack = navigator::popBackStack,
    )
}
