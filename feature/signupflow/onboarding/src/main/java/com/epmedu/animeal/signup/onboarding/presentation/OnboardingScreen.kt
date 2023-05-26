package com.epmedu.animeal.signup.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.component.FacebookSignInLauncher
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.getActivity
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.RedirectedFromFacebookWebUi
import com.epmedu.animeal.signup.onboarding.presentation.viewmodel.OnboardingState
import com.epmedu.animeal.signup.onboarding.presentation.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen() {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val activity = LocalContext.current.getActivity()
    val state by viewModel.stateFlow.collectAsState()

    OnState(state = state, navigator = navigator, onEvent = viewModel::handleEvent)

    OnboardingScreenUI(
        onSignInMobile = {
            viewModel.handleEvent(OnboardingScreenEvent.SignInWithMobileClicked)
        },
        onSignInFacebook = {
            (activity as? FacebookSignInLauncher)?.signInWithFacebook(
                onSuccess = { viewModel.handleEvent(RedirectedFromFacebookWebUi) },
                onError = {}
            )
        },
    )
}

@Composable
private fun OnState(
    state: OnboardingState,
    navigator: Navigator,
    onEvent: (event: OnboardingScreenEvent) -> Unit
) {
    state.authenticationType?.let {
        when (it) {
            AuthenticationType.Mobile -> {
                navigator.navigate(SignUpRoute.EnterPhone.name)
            }
            is AuthenticationType.Facebook -> {
                if (it.isPhoneNumberVerified) {
                    navigator.navigateToTabs()
                } else {
                    navigator.navigate(SignUpRoute.FinishProfile.name)
                }
            }
        }

        onEvent(OnboardingScreenEvent.SignInFinished)
    }
}

private fun Navigator.navigateToTabs() {
    parent?.navigate(MainRoute.Tabs.name) {
        popUpTo(MainRoute.SignUp.name) {
            inclusive = true
        }
    }
}