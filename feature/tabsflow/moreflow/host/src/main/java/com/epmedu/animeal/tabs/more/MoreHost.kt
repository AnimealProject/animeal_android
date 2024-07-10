package com.epmedu.animeal.tabs.more

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.feedings.presentation.FeedingsScreen
import com.epmedu.animeal.navigation.AnimatedScreenNavHost
import com.epmedu.animeal.tabs.more.about.AboutScreen
import com.epmedu.animeal.tabs.more.account.AccountScreen
import com.epmedu.animeal.tabs.more.donate.presentation.DonateScreen
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreen
import com.epmedu.animeal.tabs.more.presentation.MoreScreen
import com.epmedu.animeal.tabs.more.profile.ProfileScreen

@Composable
fun MoreHost() {
    AnimatedScreenNavHost(
        startDestination = MoreRoute.More.name,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
    ) {
        screen(
            MoreRoute.More.name,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) }
        ) {
            MoreScreen()
        }
        screen(MoreRoute.Profile.name) { ProfileScreen() }
        screen(MoreRoute.Feedings.name) { FeedingsScreen() }
        screen(MoreRoute.Donate.name) { DonateScreen() }
        screen(MoreRoute.FAQ.name) { FAQScreen() }
        screen(MoreRoute.About.name) { AboutScreen() }
        screen(MoreRoute.Account.name) { AccountScreen() }
    }
}
