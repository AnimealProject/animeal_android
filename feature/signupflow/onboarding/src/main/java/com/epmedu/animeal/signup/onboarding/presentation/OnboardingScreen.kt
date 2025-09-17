package com.epmedu.animeal.signup.onboarding.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.ErrorShown
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.SignInGuestClicked
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.SignInWithMobileClicked
import com.epmedu.animeal.signup.onboarding.presentation.viewmodel.OnboardingState
import com.epmedu.animeal.signup.onboarding.presentation.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen() {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()

    OnState(state = state, navigator = navigator, onEvent = viewModel::handleEvent)

    OnboardingScreenUI(
        onSignInMobile = { viewModel.handleEvent(SignInWithMobileClicked) },
        onSignInGuest = { viewModel.handleEvent(SignInGuestClicked) },
    )
}

@Composable
private fun OnState(
    state: OnboardingState,
    navigator: Navigator,
    onEvent: (event: OnboardingScreenEvent) -> Unit
) {
    if (state.isError) {
        Toast.makeText(
            LocalContext.current,
            stringResource(R.string.something_went_wrong),
            Toast.LENGTH_SHORT
        ).show()
        onEvent(ErrorShown)
    }
    state.authenticationType?.let {
        when (it) {
            AuthenticationType.Mobile -> {
                navigator.navigate(SignUpRoute.EnterPhone.name)
            }
            AuthenticationType.Guest -> {
                navigator.navigateToTabs()
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