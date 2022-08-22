package com.epmedu.animeal.signup.onboarding.presentation

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.navigation.route.MainRoute
import com.epmedu.animeal.navigation.route.SignUpRoute

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

// TODO: Remove when facebook login will be implemented
private fun Navigator.navigateToTabs() {
    parent?.navigate(MainRoute.Tabs.name) {
        popUpTo(MainRoute.SignUp.name) {
            inclusive = true
        }
    }
}