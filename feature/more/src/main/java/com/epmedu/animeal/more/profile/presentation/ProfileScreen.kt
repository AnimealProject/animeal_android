package com.epmedu.animeal.more.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun ProfileScreen() {
    val viewModel: ProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state = viewModel.stateFlow.collectAsState().value

    BottomBarVisibility(HIDDEN)

    ProfileScreenUI(
        state = state,
        onBack = navigator::popBackStack,
        onProfileScreenEvent = viewModel::handleScreenEvent,
        onProfileFormEvent = viewModel::handleInputFormEvent
    )
}
