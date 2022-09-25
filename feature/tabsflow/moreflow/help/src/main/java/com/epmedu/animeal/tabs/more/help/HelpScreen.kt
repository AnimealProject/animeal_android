package com.epmedu.animeal.tabs.more.help

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.more.help.viewmodel.HelpViewModel

@Composable
fun HelpScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val helpViewModel = hiltViewModel<HelpViewModel>()

    val state by helpViewModel.stateFlow.collectAsState()

    BottomBarVisibility(HIDDEN)

    HelpScreenUI(
        helpState = state,
        onBack = navigator::popBackStack,
    )
}
