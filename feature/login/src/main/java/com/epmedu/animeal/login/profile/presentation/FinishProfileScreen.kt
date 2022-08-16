package com.epmedu.animeal.login.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.OnboardingScreenRoute
import com.epmedu.animeal.login.signin.presentation.navigateToTabs
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun FinishProfileScreen() {
    val viewModel: FinishProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()

    FinishProfileScreenUI(
        state = state,
        onCancel = {
            navigator.popBackStack(OnboardingScreenRoute.EnterPhone.name)
        }
    ) { event ->
        viewModel.handleEvents(event)
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                FinishProfileViewModel.Event.Saved -> {
                    navigator.navigateToTabs()
                }
            }
        }
    }
}