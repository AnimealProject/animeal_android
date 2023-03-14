package com.epmedu.animeal.splash.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.splash.presentation.SplashScreenEvent.ErrorShown
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Home
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.SignUp
import com.epmedu.animeal.splash.presentation.viewmodel.SplashViewModel

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: SplashViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsState()

    if (state.isError) {
        Toast.makeText(
            context,
            stringResource(id = R.string.something_went_wrong),
            Toast.LENGTH_SHORT
        ).show()
        viewModel.handleEvents(ErrorShown)
    }

    state.nextDestination?.let { direction ->
        navigator.navigate(
            when (direction) {
                SignUp -> MainRoute.SignUp.name
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
