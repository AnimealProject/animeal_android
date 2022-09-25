package com.epmedu.animeal.tabs

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.foundation.animation.VerticalSlideAnimatedVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.epmedu.animeal.foundation.bottombar.LocalBottomBarVisibilityController
import com.epmedu.animeal.home.presentation.HomeScreen
import com.epmedu.animeal.navigation.ScreenNavHost
import com.epmedu.animeal.tabs.analytics.AnalyticsScreen
import com.epmedu.animeal.tabs.more.MoreFlow
import com.epmedu.animeal.tabs.search.SearchScreen
import com.epmedu.animeal.tabs.ui.BottomAppBarFab
import com.epmedu.animeal.tabs.ui.BottomNavigationBar

@Composable
fun TabsFlow() {
    val navigationController = rememberNavController()
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var bottomBarVisibility by rememberSaveable { mutableStateOf(SHOWN) }
    val onChangeBottomBarVisibility = { visibility: BottomBarVisibilityState ->
        bottomBarVisibility = visibility
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
            VerticalSlideAnimatedVisibility(
                visible = bottomBarVisibility.isShown()
            ) {
                BottomAppBarFab(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate,
                )
            }
        },
        bottomBar = {
            VerticalSlideAnimatedVisibility(
                visible = bottomBarVisibility.isShown()
            ) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate
                )
            }
        },
    ) { padding ->
        CompositionLocalProvider(
            LocalBottomBarVisibilityController provides onChangeBottomBarVisibility
        ) {
            NavigationTabs(navigationController)
        }
    }
}

@Composable
private fun NavigationTabs(navigationController: NavHostController) {
    ScreenNavHost(
        navController = navigationController,
        startDestination = NavigationTab.Home.route.name
    ) {
        screen(NavigationTab.Search.route.name) { SearchScreen() }
        screen(NavigationTab.Favorites.route.name) { FavoritesScreen() }
        screen(NavigationTab.Home.route.name) { HomeScreen() }
        screen(NavigationTab.Analytics.route.name) { AnalyticsScreen() }
        screen(NavigationTab.More.route.name) { MoreFlow() }
    }
}