package com.epmedu.animeal.signup.entercode.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.effect.DisplayedEffect
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.NumberChanged
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.ReadSMS
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.ResendCode
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.ScreenDisplayed
import com.epmedu.animeal.signup.entercode.presentation.ui.SMSReader
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToFinishProfile
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToHomeScreen
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeViewModel

@Composable
fun EnterCodeScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: EnterCodeViewModel = hiltViewModel()

    val focusRequester = remember { FocusRequester() }
    val state by viewModel.stateFlow.collectAsState()

    // remember lambdas to avoid excess code row and back button recompositions
    // while resend code delay is ticking, not needed for resend button though
    val onBack: () -> Unit = remember { { navigator.popBackStack() } }

    val onNumberChange: (position: Int, number: String?) -> Unit =
        remember { { position, number -> viewModel.handleEvents(NumberChanged(position, number)) } }

    SMSReader(
        onSMS = { sms -> viewModel.handleEvents(ReadSMS(sms)) }
    )

    EnterCodeScreenUi(
        state = state,
        focusRequester = focusRequester,
        onBack = onBack,
        onNumberChange = onNumberChange,
        onResend = {
            viewModel.handleEvents(ResendCode)
            focusRequester.requestFocus()
        }
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                NavigateToFinishProfile -> {
                    navigator.navigate(SignUpRoute.FinishProfile.name)
                }

                NavigateToHomeScreen -> {
                    navigator.navigateToTabs()
                }
            }
        }
    }

    LaunchedEffect(state.isError) {
        if (state.isError) focusRequester.requestFocus()
    }

    DisplayedEffect {
        viewModel.handleEvents(ScreenDisplayed)
    }
}

private fun Navigator.navigateToTabs() {
    parent?.navigate(MainRoute.Tabs.name) {
        popUpTo(MainRoute.SignUp.name) {
            inclusive = true
        }
    }
}