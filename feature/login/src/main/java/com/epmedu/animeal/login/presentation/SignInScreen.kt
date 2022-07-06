package com.epmedu.animeal.login.presentation

import androidx.compose.runtime.Composable
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.OnboardingScreenRoute
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator

@Composable
internal fun SignInScreen() {
    val navigator = LocalNavigator.currentOrThrow

    SignInScreenUI(
        onSignInMobile = {
            // TODO: Replace route with EnterPhoneScreen
            navigator.navigate(OnboardingScreenRoute.EnterCode.name)
        },
        onSignInFacebook = {
            navigator.navigateToTabs()
        },
        onSignInGoogle = {
            navigator.navigateToTabs()
        },
    )
}

internal fun Navigator.navigateToTabs() {
    parent!!.navigate(MainScreenRoute.Tabs.name) {
        popUpTo(MainScreenRoute.Onboarding.name) {
            inclusive = true
        }
    }
}
