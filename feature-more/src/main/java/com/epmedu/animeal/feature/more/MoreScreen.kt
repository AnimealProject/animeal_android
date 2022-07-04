package com.epmedu.animeal.feature.more

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.navigation.AnimatedScreenNavHost
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.resources.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoreScreen() {
    val viewModel: MoreViewModel = viewModel()

    AnimatedScreenNavHost(
        startDestination = NavigationScreen.Home.route.name,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) }
    ) {
        screen(
            NavigationScreen.Home.route.name,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left) }
        ) {
            HomeScreen(onLogout = { viewModel.logout() })
        }
        screen(NavigationScreen.ProfilePage.route.name) { ProfilePageScreen() }
        screen(NavigationScreen.Donate.route.name) { DonateScreen() }
        screen(NavigationScreen.Help.route.name) { HelpScreen() }
        screen(NavigationScreen.About.route.name) { AboutScreen() }
    }
}

@Composable
private fun HomeScreen(onLogout: () -> Unit) {
    val navigator = LocalNavigator.currentOrThrow

    HomeScreenUI(
        onItemClicked = {
            navigator.navigate(it)
        },
        onLogout = onLogout,
    )
}

@Composable
private fun HomeScreenUI(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onLogout: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopBar(title = stringResource(id = R.string.more)) }
    ) {
        Column {
            LazyColumn {
                items(screens) { screen ->
                    MoreOption(
                        title = stringResource(id = screen.title),
                        onClick = { onItemClicked(screen.route.name) }
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
    object ProfilePage : NavigationScreen(route = Route.PROFILE_PAGE, title = R.string.page_profile)
    object Donate : NavigationScreen(route = Route.DONATE, title = R.string.page_donate)
    object Help : NavigationScreen(route = Route.HELP, title = R.string.page_help)
    object About : NavigationScreen(route = Route.ABOUT, title = R.string.page_about_detailed)

    enum class Route { HOME, PROFILE_PAGE, DONATE, HELP, ABOUT }
}

@Preview
@Composable
fun MoreScreenPreview() {
    AnimealTheme {
        HomeScreenUI(
            onItemClicked = {},
            onLogout = {},
        )
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
