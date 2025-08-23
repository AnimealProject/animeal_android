package com.epmedu.animeal.tabs

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.epmedu.animeal.common.route.TabsRoute
import com.epmedu.animeal.foundation.icons.AnimealIcons
import com.epmedu.animeal.foundation.icons.outlined.Analytics
import com.epmedu.animeal.foundation.icons.outlined.Favorites
import com.epmedu.animeal.foundation.icons.outlined.Home
import com.epmedu.animeal.foundation.icons.outlined.More
import com.epmedu.animeal.foundation.icons.outlined.Search
import com.epmedu.animeal.resources.R

sealed class NavigationTab(
    val route: TabsRoute,
    val icon: ImageVector,
    @StringRes val contentDescription: Int
) {
    data object Search : NavigationTab(
        route = TabsRoute.Search,
        icon = AnimealIcons.Outlined.Search,
        contentDescription = R.string.tab_search
    )
    data object Favorites : NavigationTab(
        route = TabsRoute.Favourites,
        icon = AnimealIcons.Outlined.Favorites,
        contentDescription = R.string.tab_favourites
    )
    data object Home : NavigationTab(
        route = TabsRoute.Home,
        icon = AnimealIcons.Outlined.Home,
        contentDescription = R.string.tab_home
    )
    data object Analytics : NavigationTab(
        route = TabsRoute.Analytics,
        icon = AnimealIcons.Outlined.Analytics,
        contentDescription = R.string.tab_analytics
    )
    data object More : NavigationTab(
        route = TabsRoute.More,
        icon = AnimealIcons.Outlined.More,
        contentDescription = R.string.tab_more
    )
}
