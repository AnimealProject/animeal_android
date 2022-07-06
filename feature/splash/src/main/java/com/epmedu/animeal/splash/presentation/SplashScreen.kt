package com.epmedu.animeal.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen() {
    val navController = LocalNavigator.currentOrThrow
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // todo delete it after login logic implementation
            delay(2000)
            navController.navigate(MainScreenRoute.SignIn.name) {
                popUpTo(MainScreenRoute.Splash.name) {
                    inclusive = true
                }
            }
        }
    }

    SplashScreenUI()
}
