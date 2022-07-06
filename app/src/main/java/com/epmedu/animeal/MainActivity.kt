package com.epmedu.animeal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.login.presentation.SignInScreen
import com.epmedu.animeal.navigation.AnimatedScreenNavHost
import com.epmedu.animeal.splash.presentation.SplashScreen
import com.epmedu.animeal.tabs.presentation.TabsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnimealTheme {
                AnimatedScreenNavHost(
                    startDestination = MainScreenRoute.Splash.name
                ) {
                    screen(MainScreenRoute.SignIn.name) { SignInScreen() }
                    screen(MainScreenRoute.Splash.name) { SplashScreen() }
                    screen(MainScreenRoute.Tabs.name) { TabsScreen() }
                }
            }
        }
    }
}
