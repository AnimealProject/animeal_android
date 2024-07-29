package com.epmedu.animeal.signup.presentation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.foundation.loading.FullScreenLoading
import com.epmedu.animeal.navigation.ScreenNavHost
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreen
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreen
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreen
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreen
import com.epmedu.animeal.signup.presentation.viewmodel.SignUpViewModel

@Composable
fun SignUpFlow() {
    val viewModel: SignUpViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    ScreenNavHost(
        modifier = Modifier.statusBarsPadding(),
        startDestination = state.startDestination.name
    ) {
        screen(SignUpRoute.Onboarding.name) { OnboardingScreen() }
        screen(SignUpRoute.EnterPhone.name) { EnterPhoneScreen() }
        screen(SignUpRoute.EnterCode.name) { EnterCodeScreen() }
        screen(SignUpRoute.FinishProfile.name) { FinishProfileScreen() }
    }

    if (state.isLoading) {
        keyboardController?.hide()
        FullScreenLoading()
    }
}
