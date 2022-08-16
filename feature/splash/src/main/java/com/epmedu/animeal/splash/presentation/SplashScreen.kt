package com.epmedu.animeal.splash.presentation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun SplashScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                SplashViewModel.Event.NavigateToHome -> {
                    navigator.navigate(MainScreenRoute.Tabs.name) {
                        popUpTo(MainScreenRoute.Splash.name) {
                            inclusive = true
                        }
                    }
                }
                SplashViewModel.Event.NavigateToOnboarding -> {
                    navigator.navigate(MainScreenRoute.Onboarding.name) {
                        popUpTo(MainScreenRoute.Splash.name) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.verifyProfileSaved()
    }

    SplashScreenUI()
}
