package com.epmedu.animeal.tabs.more.donate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.tabs.more.donate.viewmodel.DonateViewModel

@Composable
fun DonateScreen() {
    val viewModel = hiltViewModel<DonateViewModel>()

    BottomBarVisibility(HIDDEN)

    val state = viewModel.stateFlow.collectAsState()

    DonateScreenUI(
        state = state.value,
        onEvent = viewModel::handleEvent
    )
}
