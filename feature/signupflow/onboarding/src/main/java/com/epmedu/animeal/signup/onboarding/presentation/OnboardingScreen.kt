package com.epmedu.animeal.signup.onboarding.presentation

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.getActivity
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.onboarding.presentation.viewmodel.OnboardingState
import com.epmedu.animeal.signup.onboarding.presentation.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen() {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val activity = LocalContext.current.getActivity()
    val state by viewModel.stateFlow.collectAsState()

    onState(state = state, navigator = navigator, onEvent = viewModel::handleEvent)

    val facebookSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            RESULT_OK -> {
                viewModel.handleEvent(OnboardingScreenEvent.RedirectedFromFacebookWebUi)
            }
            RESULT_CANCELED -> {
                // TODO temporary decision, discuss how to handle
                Toast.makeText(
                    activity,
                    R.string.onboarding_facebook_sign_in_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    OnboardingScreenUI(
        onSignInMobile = {
            viewModel.handleEvent(OnboardingScreenEvent.SignInWithMobileClicked)
        },
        onSignInFacebook = {
            val intent = Intent(activity, FacebookSignInActivity::class.java)
            facebookSignInLauncher.launch(intent)
        },
    )
}

@Composable
private fun onState(
    state: OnboardingState,
    navigator: Navigator,
    onEvent: (event: OnboardingScreenEvent) -> Unit
) {
    state.authenticationType?.let {
        when (it) {
            AuthenticationType.Mobile -> navigator.navigate(SignUpRoute.EnterPhone.name)
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