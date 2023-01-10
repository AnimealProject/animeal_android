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
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.navigation.route.AuthenticationType
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToNextPage
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeViewModel
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.FacebookEnterCodeViewModel
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.MobileEnterCodeViewModel

@Composable
fun EnterCodeScreen(authenticationType: AuthenticationType) {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: EnterCodeViewModel = when (authenticationType) {
        AuthenticationType.Mobile -> hiltViewModel<MobileEnterCodeViewModel>()
        AuthenticationType.Facebook -> hiltViewModel<FacebookEnterCodeViewModel>()
    }
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

        viewModel.events.collect {
            if (it is NavigateToNextPage) {
                when (authenticationType) {
                    AuthenticationType.Mobile -> navigator.navigate(SignUpRoute.FinishProfile.name)
                    AuthenticationType.Facebook -> navigator.navigateToTabs()
                }
            }
        }
    }
}

private fun Navigator.navigateToTabs() {
    parent?.navigate(MainRoute.Tabs.name) {
        popUpTo(MainRoute.SignUp.name) {
            inclusive = true
        }
    }
}