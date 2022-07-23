package com.epmedu.animeal.login.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.signin.presentation.navigateToTabs
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun FinishProfileScreen(isFirstTime: Boolean = true) {
    val viewModel: FinishProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state = viewModel.stateFlow.collectAsState().value

    FinishProfileScreenUi(
        state = state,
        onBack = navigator::popBackStack
    ) { event ->
        viewModel.handleEvents(event)
    }

    LaunchedEffect(Unit) {
        viewModel.validationSharedFlow.collect {
            when (it) {
                FinishProfileViewModel.ValidationEvent.Success -> {
                    if (isFirstTime) {
                        navigator.navigateToTabs()
                    } else {
                        navigator.popBackStack()
                    }
                }
            }
        }
    }
}