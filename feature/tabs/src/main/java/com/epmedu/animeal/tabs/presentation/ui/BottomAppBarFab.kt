package com.epmedu.animeal.tabs.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.presentation.NavigationTab

@Composable
internal fun BottomAppBarFab(
    currentRoute: String?,
    associatedScreen: NavigationTab = NavigationTab.Home,
    onNavigate: (NavigationTab.Route) -> Unit
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
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = stringResource(associatedScreen.title)
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun BottomAppBarFabPreview() {
    AnimealTheme {
        Column {
            BottomAppBarFab(
                currentRoute = NavigationTab.Home.route.name,
                onNavigate = {},
            )
            BottomAppBarFab(
                currentRoute = NavigationTab.More.route.name,
                onNavigate = {},
            )
        }
    }
}
