package com.epmedu.animeal.login.phone.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.OnboardingScreenRoute
import com.epmedu.animeal.login.phone.domain.EnterPhoneViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun EnterPhoneScreen() {
    val viewModel: EnterPhoneViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()
    val focusRequester = remember { FocusRequester() }

    EnterPhoneScreenUI(
        state = state,
        focusRequester = focusRequester,
        onNumberChange = { viewModel.updatePhoneNumber(it) },
        onBack = navigator::popBackStack,
        onNext = {
            viewModel.savePhoneNumberAndSendCode()
            navigator.navigate(OnboardingScreenRoute.EnterCode.name)
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}