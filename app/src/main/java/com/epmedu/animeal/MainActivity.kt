package com.epmedu.animeal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.TransparentSystemUi
import com.epmedu.animeal.navigation.AnimatedScreenNavHost
import com.epmedu.animeal.navigation.route.MainRoute
import com.epmedu.animeal.signup.SignUpScreen
import com.epmedu.animeal.splash.presentation.SplashScreen
import com.epmedu.animeal.tabs.presentation.TabsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AnimealTheme {
                TransparentSystemUi {
                    AnimatedScreenNavHost(
                        startDestination = MainRoute.Splash.name,
                        modifier = Modifier.navigationBarsPadding()
                    ) {
                        screen(MainRoute.Splash.name) { SplashScreen() }
                        screen(MainRoute.SignUp.name) { SignUpScreen() }
                        screen(MainRoute.Tabs.name) { TabsScreen() }
                    }
                }
            }
        }
    }
}
