package com.epmedu.animeal.tabs.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.more.TabMoreScreen
import com.epmedu.animeal.navigation.ScreenNavHost
import com.epmedu.animeal.tabs.presentation.ui.BottomAppBarFab

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TabsScreen(
    modifier: Modifier = Modifier,
) {
    val navigationController = rememberNavController()
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showBottomBar by remember { mutableStateOf(true) }

    val changeBottomBarVisibility: (Boolean) -> Unit = { visible ->
        showBottomBar = visible
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
        modifier = modifier.fillMaxSize(),
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomAppBarFab(currentRoute = currentRoute, onNavigate = onNavigate)
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate
                )
            }
        },
    ) { padding ->
        NavigationTabs(
            padding = padding,
            navigationController = navigationController,
            changeBottomBarVisibility = changeBottomBarVisibility,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NavigationTabs(
    padding: PaddingValues,
    navigationController: NavHostController,
    changeBottomBarVisibility: (Boolean) -> Unit,
) {
    ScreenNavHost(
        // modifier = Modifier.padding(padding),
        navController = navigationController,
        startDestination = NavigationTab.Home.route.name
    ) {
        screen(NavigationTab.Search.route.name) { SearchScreen() }
        screen(NavigationTab.Favorites.route.name) { FavoritesScreen() }
        screen(NavigationTab.Home.route.name) { HomeScreen(changeBottomBarVisibility) }
        screen(NavigationTab.Analytics.route.name) { AnalyticsScreen() }
        screen(NavigationTab.More.route.name) { TabMoreScreen() }
    }
}

@Composable
private fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (NavigationTab.Route) -> Unit
) {
    val items = listOf(
        NavigationTab.Search,
        NavigationTab.Favorites,
        NavigationTab.Home,
        NavigationTab.Analytics,
        NavigationTab.More
    )
    BottomAppBar(
        modifier = Modifier.height(56.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        items.forEach { item ->
            if (item == NavigationTab.Home) {
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
