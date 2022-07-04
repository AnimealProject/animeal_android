package com.epmedu.animeal.more

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.epmedu.animeal.more.about.AboutScreen
import com.epmedu.animeal.more.donate.DonateScreen
import com.epmedu.animeal.more.help.HelpScreen
import com.epmedu.animeal.more.profile.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoreScreen() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationScreen.More.route.name,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) }
    ) {
        composable(
            NavigationScreen.More.route.name,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left) }
        ) {
            MoreScreenUi { route -> navController.navigate(route) }
        }
        composable(NavigationScreen.ProfilePage.route.name) { ProfileScreen(navController) }
        composable(NavigationScreen.Donate.route.name) { DonateScreen(navController) }
        composable(NavigationScreen.Help.route.name) { HelpScreen(navController) }
        composable(NavigationScreen.About.route.name) { AboutScreen(navController) }
    }
}

internal val screens = listOf(
    NavigationScreen.ProfilePage,
    NavigationScreen.Donate,
    NavigationScreen.Help,
    NavigationScreen.About
)

internal sealed class NavigationScreen(val route: Route, @StringRes val title: Int) {
    object More : NavigationScreen(route = Route.MORE, title = R.string.more)
    object ProfilePage : NavigationScreen(route = Route.PROFILE, title = R.string.profile)
    object Donate : NavigationScreen(route = Route.DONATE, title = R.string.donate)
    object Help : NavigationScreen(route = Route.HELP, title = R.string.help)
    object About : NavigationScreen(route = Route.ABOUT, title = R.string.about_detailed)

    enum class Route { MORE, PROFILE, DONATE, HELP, ABOUT }
}