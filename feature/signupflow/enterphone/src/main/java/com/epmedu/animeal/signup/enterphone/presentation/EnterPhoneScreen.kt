package com.epmedu.animeal.signup.enterphone.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.effect.DisplayedEffect
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.NavigatedToEnterCode
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.ScreenDisplayed
import com.epmedu.animeal.signup.enterphone.presentation.viewmodel.EnterPhoneViewModel

@Composable
fun EnterPhoneScreen() {
    val viewModel: EnterPhoneViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()
    val focusRequester = remember { FocusRequester() }

    EnterPhoneScreenUi(
        state = state,
        focusRequester = focusRequester,
        onEvent = viewModel::handleEvents,
        onBack = { navigator.popBackStackOrNavigate(SignUpRoute.Onboarding.name) }
    )

    LaunchedEffect(state.navigateToEnterCode) {
        if (state.navigateToEnterCode) {
            navigator.navigate(SignUpRoute.EnterCode.name)
            viewModel.handleEvents(NavigatedToEnterCode)
        }
    }

    DisplayedEffect {
        viewModel.handleEvents(ScreenDisplayed)
    }
}