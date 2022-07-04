package com.epmedu.animeal.tabs.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.base.theme.NavigationItemColor
import com.epmedu.animeal.base.theme.NavigationItemSelectedColor
import com.epmedu.animeal.feature.more.MoreScreen
import com.epmedu.animeal.navigation.ScreenNavHost

@Composable
fun TabsScreen(
    modifier: Modifier = Modifier,
) {
    val navigationController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navigationController) },
    ) { padding ->
        NavigationScreens(navigationController, padding)
    }
}

@Composable
private fun NavigationScreens(navigationController: NavHostController, padding: PaddingValues) {
    ScreenNavHost(
        modifier = Modifier.padding(padding),
        navController = navigationController,
        startDestination = NavigationTab.Home.route.name
    ) {
        screen(NavigationTab.Search.route.name) { SearchScreen() }
        screen(NavigationTab.Favorites.route.name) { FavoritesScreen() }
        screen(NavigationTab.Home.route.name) { HomeScreen() }
        screen(NavigationTab.Analytics.route.name) { AnalyticsScreen() }
        screen(NavigationTab.More.route.name) { MoreScreen() }
    }
}

@Composable
private fun BottomNavigationBar(
    navigationController: NavController,
) {
    val items = listOf(
        NavigationTab.Search,
        NavigationTab.Favorites,
        NavigationTab.Home,
        NavigationTab.Analytics,
        NavigationTab.More
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
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
