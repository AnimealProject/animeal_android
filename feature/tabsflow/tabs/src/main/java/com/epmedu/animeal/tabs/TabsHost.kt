package com.epmedu.animeal.tabs

import android.annotation.SuppressLint
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.common.route.TabsRoute
import com.epmedu.animeal.favourites.presentation.FavouritesScreen
import com.epmedu.animeal.foundation.animation.VerticalSlideAnimatedVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.epmedu.animeal.foundation.bottombar.LocalBottomBarVisibilityController
import com.epmedu.animeal.home.presentation.HomeScreen
import com.epmedu.animeal.navigation.ScreenNavHost
import com.epmedu.animeal.tabs.analytics.AnalyticsScreen
import com.epmedu.animeal.tabs.more.MoreHost
import com.epmedu.animeal.tabs.search.presentation.SearchScreen
import com.epmedu.animeal.tabs.ui.BottomAppBarFab
import com.epmedu.animeal.tabs.ui.BottomNavigationBar
import com.epmedu.animeal.tabs.viewmodel.TabsViewModel
import com.epmedu.animeal.timer.data.model.TimerState

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun TabsHost() {
    val viewModel = hiltViewModel<TabsViewModel>()
    val state by viewModel.stateFlow.collectAsState()

    val navigationController = rememberNavController()
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var bottomBarVisibility by rememberSaveable { mutableStateOf(SHOWN) }
    val onChangeBottomBarVisibility = { visibility: BottomBarVisibilityState ->
        bottomBarVisibility = visibility
    }

    val onNavigate: (TabsRoute) -> Unit = { route ->
        navigationController.navigate(route.name) {
            navigationController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) { saveState = true }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    if (state is TimerState.Expired) onNavigate(TabsRoute.Home)

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
private fun NavigationTabs(
    navigationController: NavHostController,
) {
    ScreenNavHost(
        navController = navigationController,
        startDestination = NavigationTab.Home.route.name
    ) {
        screen(TabsRoute.Search.name) { SearchScreen() }
        screen(TabsRoute.Favourites.name) { FavouritesScreen() }
        screen(TabsRoute.Home.name) { HomeScreen() }
        screen(TabsRoute.Analytics.name) { AnalyticsScreen() }
        screen(TabsRoute.More.name) { MoreHost() }
    }
}