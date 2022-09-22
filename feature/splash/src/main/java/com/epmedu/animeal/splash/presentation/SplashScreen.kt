package com.epmedu.animeal.splash.presentation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.route.MainRoute

@Composable
fun SplashScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                SplashViewModel.Event.NavigateToHome -> {
                    navigator.navigate(MainRoute.Main.name) {
                        popUpTo(MainRoute.Splash.name) {
                            inclusive = true
                        }
                    }
                }
                SplashViewModel.Event.NavigateToOnboarding -> {
                    navigator.navigate(MainRoute.SignUp.name) {
                        popUpTo(MainRoute.Splash.name) {
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
