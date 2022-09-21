package com.epmedu.animeal.more

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.more.about.AboutScreen
import com.epmedu.animeal.more.account.AccountScreen
import com.epmedu.animeal.more.donate.DonateScreen
import com.epmedu.animeal.more.help.HelpScreen
import com.epmedu.animeal.more.profile.ProfileScreen
import com.epmedu.animeal.navigation.AnimatedScreenNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoreFlow() {
    AnimatedScreenNavHost(
        startDestination = MoreRoute.More.name,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) }
    ) {
        screen(
            MoreRoute.More.name,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left) }
        ) {
            MoreScreen()
        }
        screen(MoreRoute.Profile.name) { ProfileScreen() }
        screen(MoreRoute.Donate.name) { DonateScreen() }
        screen(MoreRoute.Help.name) { HelpScreen() }
        screen(MoreRoute.About.name) { AboutScreen() }
        screen(MoreRoute.Account.name) { AccountScreen() }
    }
}
