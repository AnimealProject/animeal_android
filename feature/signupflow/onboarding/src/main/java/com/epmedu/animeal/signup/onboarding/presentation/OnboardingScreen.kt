package com.epmedu.animeal.signup.onboarding.presentation

import androidx.compose.runtime.Composable
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun OnboardingScreen() {
    val navigator = LocalNavigator.currentOrThrow

    OnboardingScreenUI(
        onSignInMobile = {
            navigator.navigate(SignUpRoute.EnterPhone.name)
        },
        onSignInFacebook = {
            navigator.navigate(SignUpRoute.EnterPhone.name)
        },
    )
}
