package com.epmedu.animeal.tabs.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.presentation.NavigationTab

@Composable
internal fun BottomAppBarFab(
    currentRoute: String?,
    associatedScreen: NavigationTab = NavigationTab.Home,
    onNavigate: (NavigationTab.Route) -> Unit
) {
    val route = associatedScreen.route

    Box(
        modifier = Modifier
            .padding(top = 32.dp)
            .size(64.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        Color.Transparent
                    )
                )
            )
            .padding(bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .size(56.dp)
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        when (currentRoute) {
                            route.name -> MaterialTheme.colors.primary
                            else -> MaterialTheme.colors.surface
                        }
                    )
                    .clickable {
                        onNavigate(route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = stringResource(associatedScreen.title),
                    tint = when (currentRoute) {
                        route.name -> MaterialTheme.colors.surface
                        else -> MaterialTheme.colors.onSurface
                    }
                )
            }
        }
    }
}

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
