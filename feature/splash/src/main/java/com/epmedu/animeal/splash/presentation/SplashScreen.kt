package com.epmedu.animeal.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.splash.presentation.viewmodel.SplashEvent.NavigateToHome
import com.epmedu.animeal.splash.presentation.viewmodel.SplashEvent.NavigateToOnboarding
import com.epmedu.animeal.splash.presentation.viewmodel.SplashViewModel

@Composable
fun SplashScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                NavigateToHome -> {
                    navigator.navigate(MainRoute.Tabs.name) {
                        popUpTo(MainRoute.Splash.name) {
                            inclusive = true
                        }
                    }
                }
                NavigateToOnboarding -> {
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
