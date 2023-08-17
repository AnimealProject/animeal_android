package com.epmedu.animeal.signup.onboarding.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.component.FacebookSignInLauncher
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.getActivity
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.ErrorShown
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.FacebookWebUIOpened
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.NotSignedInWithFacebook
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.SignInWithFacebookClicked
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.SignInWithMobileClicked
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.SignedInWithFacebook
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
        onSignInFacebook = { viewModel.handleEvent(SignInWithFacebookClicked) },
    )
}

@Composable
private fun OnState(
    state: OnboardingState,
    navigator: Navigator,
    onEvent: (event: OnboardingScreenEvent) -> Unit
) {
    val activity = LocalContext.current.getActivity()

    if (state.isOpeningFacebookWebUI) {
        (activity as? FacebookSignInLauncher)?.signInWithFacebook(
            onSuccess = { onEvent(SignedInWithFacebook) },
            onError = { onEvent(NotSignedInWithFacebook) }
        )
        onEvent(FacebookWebUIOpened)
    }
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