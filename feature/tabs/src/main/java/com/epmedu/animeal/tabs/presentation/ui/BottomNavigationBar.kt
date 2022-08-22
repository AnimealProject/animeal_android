package com.epmedu.animeal.tabs.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.tabs.presentation.NavigationTab

@Composable
internal fun BottomNavigationBar(
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
        modifier = Modifier.height(bottomBarHeight),
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BottomNavigationBarPreview() {
    AnimealTheme {
        Column {
            BottomNavigationBar(
                currentRoute = NavigationTab.Search.route.name,
                onNavigate = {}
            )
            Divider()
            BottomNavigationBar(
                currentRoute = NavigationTab.More.route.name,
                onNavigate = {}
            )
        }
    }
}