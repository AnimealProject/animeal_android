package com.epmedu.animeal

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.navigation.AnimatedScreenNavHost
import com.epmedu.animeal.signup.presentation.SignUpFlow
import com.epmedu.animeal.splash.presentation.SplashScreen
import com.epmedu.animeal.tabs.TabsHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainHost() {
    AnimatedScreenNavHost(
        startDestination = MainRoute.Splash.name,
        modifier = Modifier.navigationBarsPadding()
    ) {
        screen(MainRoute.Splash.name) { SplashScreen() }
        screen(MainRoute.SignUp.name) { SignUpFlow() }
        screen(MainRoute.Tabs.name) { TabsHost() }
    }
}