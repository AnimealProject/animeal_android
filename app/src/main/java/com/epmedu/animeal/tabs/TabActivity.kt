package com.epmedu.animeal.tabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.R
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.NavigationItemColor
import com.epmedu.animeal.base.theme.NavigationItemSelectedColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimealTheme {
                val navigationController = rememberNavController()
                val navigationViewModel: NavigationViewModel by viewModels()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navigationController, navigationViewModel) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationScreens(navigationController)
                }
            }
        }
    }

    @Composable
    private fun NavigationScreens(navigationController: NavHostController) {
        NavHost(navigationController, startDestination = NavigationScreen.Home.route.name) {
            composable(NavigationScreen.Search.route.name) { SearchScreen() }
            composable(NavigationScreen.Favorites.route.name) { FavoritesScreen() }
            composable(NavigationScreen.Home.route.name) { HomeScreen() }
            composable(NavigationScreen.Analytics.route.name) { AnalyticsScreen() }
            composable(NavigationScreen.More.route.name) { MoreScreen() }
        }
    }

    @Composable
    private fun BottomNavigationBar(navigationController: NavController, navigationViewModel: NavigationViewModel) {
        val items = listOf(
            NavigationScreen.Search,
            NavigationScreen.Favorites,
            NavigationScreen.Home,
            NavigationScreen.Analytics,
            NavigationScreen.More
        )
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = NavigationItemColor
        ) {
            val navBackStackEntry by navigationController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = stringResource(item.title)) },
                    selectedContentColor = NavigationItemSelectedColor,
                    unselectedContentColor = NavigationItemColor,
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route.name,
                    onClick = {
                        navigationController.navigate(item.route.name) {
                            navigationController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) { saveState = true }
                            }
                            navigationViewModel.onDestinationChanged()
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    private sealed class NavigationScreen(val route: Route, val icon: Int, @StringRes val title: Int) {
        object Search : NavigationScreen(route = Route.SEARCH, icon = R.drawable.icon_search, title = R.string.search)
        object Favorites : NavigationScreen(route = Route.FAVORITES, icon = R.drawable.icon_favorites, title = R.string.favorites)
        object Home : NavigationScreen(route = Route.HOME, icon = R.drawable.icon_home, title = R.string.home)
        object Analytics : NavigationScreen(route = Route.ANALYTICS, icon = R.drawable.icon_analytics, title = R.string.analytics)
        object More : NavigationScreen(route = Route.MORE, icon = R.drawable.icon_more, title = R.string.more)

        enum class Route {
            SEARCH, FAVORITES, HOME, ANALYTICS, MORE
        }
    }
}