package com.epmedu.animeal.tabs.presentation

import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.foundation.animation.VerticalAnimatedVisibility
import com.epmedu.animeal.home.HomeScreen
import com.epmedu.animeal.more.TabMoreScreen
import com.epmedu.animeal.navigation.ScreenNavHost
import com.epmedu.animeal.tabs.presentation.ui.BottomAppBarFab
import com.epmedu.animeal.tabs.presentation.ui.BottomNavigationBar

@Composable
fun TabsScreen() {
    val navigationController = rememberNavController()
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var isBottomBarVisible by rememberSaveable { mutableStateOf(true) }
    val onChangeBottomBarVisibility = { visible: Boolean ->
        isBottomBarVisible = visible
    }
    val onNavigate: (NavigationTab.Route) -> Unit = { route ->
        navigationController.navigate(route.name) {
            navigationController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) { saveState = true }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            VerticalAnimatedVisibility(
                visible = isBottomBarVisible
            ) {
                BottomAppBarFab(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate,
                )
            }
        },
        bottomBar = {
            VerticalAnimatedVisibility(
                visible = isBottomBarVisible
            ) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate
                )
            }
        },
    ) { padding ->
        NavigationTabs(navigationController, onChangeBottomBarVisibility)
    }
}

@Composable
private fun NavigationTabs(
    navigationController: NavHostController,
    onChangeBottomBarVisibility: (Boolean) -> Unit
) {
    ScreenNavHost(
        navController = navigationController,
        startDestination = NavigationTab.Home.route.name
    ) {
        screen(NavigationTab.Search.route.name) { SearchScreen() }
        screen(NavigationTab.Favorites.route.name) { FavoritesScreen() }
        screen(NavigationTab.Home.route.name) { HomeScreen() }
        screen(NavigationTab.Analytics.route.name) { AnalyticsScreen() }
        screen(NavigationTab.More.route.name) { TabMoreScreen(onChangeBottomBarVisibility) }
    }
}