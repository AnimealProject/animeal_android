package com.epmedu.animeal.more.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.profile.presentation.FinishProfileScreenUi
import com.epmedu.animeal.login.profile.presentation.FinishProfileViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun ProfileScreen() {
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
                    navigator.popBackStack()
                }
            }
        }
    }
}
