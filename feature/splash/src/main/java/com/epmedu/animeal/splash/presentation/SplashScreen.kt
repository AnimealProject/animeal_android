package com.epmedu.animeal.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Home
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Onboarding
import com.epmedu.animeal.splash.presentation.viewmodel.SplashViewModel

@Composable
fun SplashScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: SplashViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsState()

    state.nextDestination?.let { direction ->
        navigator.navigate(
            when (direction) {
                Onboarding -> MainRoute.SignUp.name
                Home -> MainRoute.Tabs.name
            }
        ) {
            popUpTo(MainRoute.Splash.name) {
                inclusive = true
            }
        }
    }

    SplashScreenUI()
}
