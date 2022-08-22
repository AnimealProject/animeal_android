package com.epmedu.animeal.more.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun ProfileScreen(
    onChangeBottomBarVisibility: (Boolean) -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state = viewModel.stateFlow.collectAsState().value

    LaunchedEffect(Unit) {
        onChangeBottomBarVisibility(false)
    }

    ProfileScreenUI(
        state = state,
        onBack = navigator::popBackStack,
        onEvent = viewModel::handleEvent
    )
}
