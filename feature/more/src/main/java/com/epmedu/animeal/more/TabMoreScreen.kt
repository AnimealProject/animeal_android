package com.epmedu.animeal.more

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.more.about.AboutScreen
import com.epmedu.animeal.more.account.AccountScreen
import com.epmedu.animeal.more.donate.DonateScreen
import com.epmedu.animeal.more.help.HelpScreen
import com.epmedu.animeal.more.profile.ProfileScreen
import com.epmedu.animeal.more.root.MoreScreen
import com.epmedu.animeal.navigation.AnimatedScreenNavHost
import com.epmedu.animeal.resources.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TabMoreScreen() {
    AnimatedScreenNavHost(
        modifier = Modifier.statusBarsPadding(),
        startDestination = NavigationScreen.More.route.name,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) }
    ) {
        screen(
            NavigationScreen.More.route.name,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left) }
        ) {
            MoreScreen()
        }
        screen(NavigationScreen.ProfilePage.route.name) { ProfileScreen() }
        screen(NavigationScreen.Donate.route.name) { DonateScreen() }
        screen(NavigationScreen.Help.route.name) { HelpScreen() }
        screen(NavigationScreen.About.route.name) { AboutScreen() }
        screen(NavigationScreen.Account.route.name) { AccountScreen() }
    }
}

internal val screens = listOf(
    NavigationScreen.ProfilePage,
    NavigationScreen.Donate,
    NavigationScreen.Help,
    NavigationScreen.About,
    NavigationScreen.Account,
)

internal sealed class NavigationScreen(val route: Route, @StringRes val title: Int) {
    object More : NavigationScreen(route = Route.MORE, title = R.string.more)
    object ProfilePage : NavigationScreen(route = Route.PROFILE, title = R.string.page_profile)
    object Donate : NavigationScreen(route = Route.DONATE, title = R.string.page_donate)
    object Help : NavigationScreen(route = Route.HELP, title = R.string.page_help)
    object About : NavigationScreen(route = Route.ABOUT, title = R.string.page_about_detailed)

    object Account : NavigationScreen(route = Route.ACCOUNT, title = R.string.page_account)

    enum class Route { MORE, PROFILE, DONATE, HELP, ABOUT, ACCOUNT }
}
