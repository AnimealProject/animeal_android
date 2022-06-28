package com.epmedu.animeal.feature.more

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.ui.TopBar
import com.epmedu.animeal.feature_more.R
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoreScreen() {
    val navController = rememberAnimatedNavController()
    val viewModel: MoreViewModel = viewModel()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationScreen.Home.route.name,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) }
    ) {
        composable(
            NavigationScreen.Home.route.name,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left) }
        ) {
            HomeScreen(navController = navController, onLogout = { viewModel.logout() })
        }
        composable(NavigationScreen.ProfilePage.route.name) { ProfilePageScreen(navController) }
        composable(NavigationScreen.Donate.route.name) { DonateScreen(navController) }
        composable(NavigationScreen.Help.route.name) { HelpScreen(navController) }
        composable(NavigationScreen.About.route.name) { AboutScreen(navController) }
    }
}

@Composable
private fun HomeScreen(navController: NavController, onLogout: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(title = stringResource(id = R.string.more)) }
    ) {
        Column {
            LazyColumn {
                items(screens) { screen ->
                    MoreOption(
                        title = stringResource(id = screen.title),
                        onClick = { navController.navigate(screen.route.name) }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            LogoutButton(onClick = onLogout)
        }
    }
}

@Composable
private fun MoreOption(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() }
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = title,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .padding(32.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = stringResource(R.string.logout),
            style = MaterialTheme.typography.body1
        )
    }
}

private val screens = listOf(
    NavigationScreen.ProfilePage,
    NavigationScreen.Donate,
    NavigationScreen.Help,
    NavigationScreen.About
)

private sealed class NavigationScreen(val route: Route, @StringRes val title: Int) {
    object Home : NavigationScreen(route = Route.HOME, title = R.string.more)
    object ProfilePage : NavigationScreen(route = Route.PROFILE_PAGE, title = R.string.profile_page)
    object Donate : NavigationScreen(route = Route.DONATE, title = R.string.donate)
    object Help : NavigationScreen(route = Route.HELP, title = R.string.help)
    object About : NavigationScreen(route = Route.ABOUT, title = R.string.about_detailed)

    enum class Route { HOME, PROFILE_PAGE, DONATE, HELP, ABOUT }
}

@Preview
@Composable
fun MoreScreenPreview() {
    AnimealTheme {
        HomeScreen(navController = NavController(LocalContext.current)) {}
    }
}

@Preview
@Composable
fun MoreOptionPreview() {
    AnimealTheme {
        Surface {
            MoreOption("Profile Page") {}
        }
    }
}
