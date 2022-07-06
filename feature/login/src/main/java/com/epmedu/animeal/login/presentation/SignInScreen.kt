package com.epmedu.animeal.login.presentation

import androidx.compose.runtime.Composable
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator

@Composable
fun SignInScreen() {
    val navigator = LocalNavigator.currentOrThrow

    SignInScreenUI(
        onSignInMobile = {
            navigator.replaceSignIn(MainScreenRoute.Tabs)
        },
        onSignInFacebook = {
            navigator.replaceSignIn(MainScreenRoute.Tabs)
        },
        onSignInGoogle = {
            navigator.replaceSignIn(MainScreenRoute.Tabs)
        },
    )
}

private fun Navigator.replaceSignIn(
    route: MainScreenRoute
) {
    navigate(route.name) {
        popUpTo(MainScreenRoute.SignIn.name) {
            inclusive = true
        }
    }
}
