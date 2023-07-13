package com.epmedu.animeal

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.debugmenu.presentation.DebugMenu
import com.epmedu.animeal.navigation.AnimatedScreenNavHost
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.presentation.SignUpFlow
import com.epmedu.animeal.splash.presentation.SplashScreen
import com.epmedu.animeal.tabs.TabsHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainHost() {
    val navController = rememberAnimatedNavController()
    val context = LocalContext.current

    val viewModel = hiltViewModel<MainViewModel>()
    val state by viewModel.stateFlow.collectAsState()

    DebugMenu(navController) {
        AnimatedScreenNavHost(
            startDestination = MainRoute.Splash.name,
            modifier = Modifier.navigationBarsPadding(),
            navController = navController
        ) {
            screen(MainRoute.Splash.name) { SplashScreen() }
            screen(MainRoute.SignUp.name) { SignUpFlow() }
            screen(MainRoute.Tabs.name) { TabsHost() }
        }
    }

    if (state.navigateToStartDestination) {
        Toast.makeText(
            context,
            context.getString(R.string.session_expired),
            Toast.LENGTH_SHORT
        ).show()
        navController.navigate(MainRoute.Splash.name) {
            navController.currentDestination?.route?.let { route ->
                popUpTo(route) {
                    inclusive = true
                }
            }
        }
        viewModel.confirmRefreshTokenExpirationWasHandled()
    }
}