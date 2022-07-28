package com.epmedu.animeal.more.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun ProfileScreen() {
    val viewModel: ProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state = viewModel.stateFlow.collectAsState().value

    ProfileScreenUI(
        state = state,
        onBack = {
            navigator.popBackStack()
        }
    ) { event ->
        viewModel.handleEvents(event)
    }
}
