package com.epmedu.animeal.login.code.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.OnboardingScreenRoute
import com.epmedu.animeal.login.code.domain.EnterCodeViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun EnterCodeScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: EnterCodeViewModel = hiltViewModel()
    val focusRequester = remember { FocusRequester() }
    val state by viewModel.stateFlow.collectAsState()

    EnterCodeScreenUi(
        state = state,
        focusRequester = focusRequester,
        onBack = navigator::popBackStack,
        onDigitChange = { position, digit ->
            viewModel.changeDigit(position, digit)
        },
        onResend = {
            viewModel.resendCode()
            focusRequester.requestFocus()
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()

        viewModel.event.collect {
            if (it is EnterCodeViewModel.Event.NavigateToFinishProfile) {
                navigator.navigate(OnboardingScreenRoute.FinishProfile.name)
            }
        }
    }
}