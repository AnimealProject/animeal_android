package com.epmedu.animeal.tabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.R
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.more.MoreScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabActivity : ComponentActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AnimealTheme {
                val navigationController = rememberNavController()

                val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val onNavigate: (NavigationScreen.Route) -> Unit = { route ->
                    navigationController.navigate(route.name) {
                        navigationController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        navigationViewModel.onDestinationChanged(route)
                        launchSingleTop = true
                        restoreState = true
                    }
                }

                Scaffold(
                    isFloatingActionButtonDocked = true,
                    floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        BottomAppBarFab(currentRoute = currentRoute, onNavigate = onNavigate)
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            currentRoute = currentRoute,
                            onNavigate = onNavigate
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) { padding ->
                    NavigationScreens(navigationController, padding)
                }
            }
        }
    }

    @Composable
    private fun BottomAppBarFab(
        currentRoute: String?,
        associatedScreen: NavigationScreen = NavigationScreen.Home,
        onNavigate: (NavigationScreen.Route) -> Unit
    ) {
        val route = associatedScreen.route

        FloatingActionButton(
            modifier = Modifier.padding(top = 22.dp),
            shape = CircleShape,
            onClick = {
                onNavigate(route)
            },
            backgroundColor = when (currentRoute) {
                route.name -> MaterialTheme.colors.primary
                else -> MaterialTheme.colors.surface
            },
            contentColor = when (currentRoute) {
                route.name -> MaterialTheme.colors.surface
                else -> MaterialTheme.colors.onSurface
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_home),
                contentDescription = stringResource(associatedScreen.title)
            )
        }
    }

    @Composable
    private fun NavigationScreens(navigationController: NavHostController, padding: PaddingValues) {
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navigationController,
            startDestination = NavigationScreen.Home.route.name
        ) {
            composable(NavigationScreen.Search.route.name) { SearchScreen() }
            composable(NavigationScreen.Favorites.route.name) { FavoritesScreen() }
            composable(NavigationScreen.Home.route.name) { HomeScreen() }
            composable(NavigationScreen.Analytics.route.name) { AnalyticsScreen() }
            composable(NavigationScreen.More.route.name) { MoreScreen() }
        }
    }

    @Composable
    private fun BottomNavigationBar(
        currentRoute: String?,
        onNavigate: (NavigationScreen.Route) -> Unit
    ) {
        val items = listOf(
            NavigationScreen.Search,
            NavigationScreen.Favorites,
            NavigationScreen.Home,
            NavigationScreen.Analytics,
            NavigationScreen.More
        )
        BottomAppBar(backgroundColor = MaterialTheme.colors.surface) {
            items.forEach { item ->
                if (item == NavigationScreen.Home) {
                    Box(modifier = Modifier.weight(1f))
                } else {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(item.title)
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.title))
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = MaterialTheme.colors.onSurface,
                        alwaysShowLabel = false,
                        selected = currentRoute == item.route.name,
                        onClick = {
                            onNavigate(item.route)
                        }
                    )
                }
            }
        }
    }

    sealed class NavigationScreen(val route: Route, val icon: Int, @StringRes val title: Int) {
        object Search : NavigationScreen(
            route = Route.SEARCH,
            icon = R.drawable.icon_search,
            title = R.string.search
        )

        object Favorites : NavigationScreen(
            route = Route.FAVORITES,
            icon = R.drawable.icon_favorites,
            title = R.string.favorites
        )

        object Home : NavigationScreen(
            route = Route.HOME,
            icon = R.drawable.icon_home,
            title = R.string.home
        )

        object Analytics : NavigationScreen(
            route = Route.ANALYTICS,
            icon = R.drawable.icon_analytics,
            title = R.string.analytics
        )

        object More : NavigationScreen(
            route = Route.MORE,
            icon = R.drawable.icon_more,
            title = R.string.more
        )

        enum class Route {
            SEARCH, FAVORITES, HOME, ANALYTICS, MORE
        }
    }
}