package com.epmedu.animeal.tabs.more.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.more.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state = viewModel.stateFlow.collectAsState().value

    BottomBarVisibility(HIDDEN)

    ProfileScreenUI(
        state = state,
        onBack = navigator::popBackStack,
        onProfileScreenEvent = viewModel::handleScreenEvent
    )
}
