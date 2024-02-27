package com.epmedu.animeal.tabs.more.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.more.presentation.viewmodel.MoreViewModel

@Composable
fun MoreScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel = hiltViewModel<MoreViewModel>()
    val state by viewModel.stateFlow.collectAsState()

    BottomBarVisibility(SHOWN)

    MoreScreenUi(
        state = state,
        onNavigate = navigator::navigate,
    )
}
