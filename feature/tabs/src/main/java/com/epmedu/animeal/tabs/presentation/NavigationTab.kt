package com.epmedu.animeal.tabs.presentation

import androidx.annotation.StringRes
import com.epmedu.animeal.resources.R

sealed class NavigationTab(val route: Route, val icon: Int, @StringRes val title: Int) {
    object Search : NavigationTab(route = Route.SEARCH, icon = R.drawable.ic_search, title = R.string.tab_search)
    object Favorites : NavigationTab(route = Route.FAVORITES, icon = R.drawable.ic_favorites, title = R.string.tab_favorites)
    object Home : NavigationTab(route = Route.HOME, icon = R.drawable.ic_home, title = R.string.tab_home)
    object Analytics : NavigationTab(route = Route.ANALYTICS, icon = R.drawable.ic_analytics, title = R.string.tab_analytics)
    object More : NavigationTab(route = Route.MORE, icon = R.drawable.ic_more, title = R.string.tab_more)

    enum class Route {
        SEARCH, FAVORITES, HOME, ANALYTICS, MORE
    }
}
