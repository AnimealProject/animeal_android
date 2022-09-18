package com.epmedu.animeal.signup.onboarding.presentation

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.getActivity
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.navigation.route.AuthenticationType
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingViewModel.Event

@Composable
fun OnboardingScreen(onAuthenticationTypeChange: (AuthenticationType) -> Unit) {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val activity = LocalContext.current.getActivity()

    val facebookSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            RESULT_OK -> {
                onAuthenticationTypeChange(AuthenticationType.Facebook)
                viewModel.loadNetworkProfile()
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

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is Event.NavigateToFinishPageInFacebookAuthFlow -> {
                    navigator.navigate(SignUpRoute.FinishProfile.name)
                }
                is Event.NavigateToHomeScreen -> {
                    navigator.navigateToTabs()
                }
            }
        }
    }

    OnboardingScreenUI(
        onSignInMobile = {
            onAuthenticationTypeChange(AuthenticationType.Mobile)
            navigator.navigate(SignUpRoute.EnterPhone.name)
        },
        onSignInFacebook = {
            val intent = Intent(activity, FacebookSignInActivity::class.java)
            facebookSignInLauncher.launch(intent)
        },
    )
}

private fun Navigator.navigateToTabs() {
    parent?.navigate(MainRoute.Tabs.name) {
        popUpTo(MainRoute.SignUp.name) {
            inclusive = true
        }
    }
}