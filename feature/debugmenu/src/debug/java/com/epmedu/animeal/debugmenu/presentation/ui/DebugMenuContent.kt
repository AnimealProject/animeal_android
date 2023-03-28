package com.epmedu.animeal.debugmenu.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent.SwitchUsingMockedFeedingPoints
import com.epmedu.animeal.foundation.theme.AnimealTheme
import kotlinx.coroutines.launch

@Composable
internal fun DebugMenuContent(
    initialState: DrawerValue = DrawerValue.Closed,
    onNavigate: (MainRoute) -> Unit,
    onEvent: (DebugMenuScreenEvent) -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = initialState)

    val menuItems = listOf(
        DebugMenuItem.Switch(
            title = "Use mocked feeding points",
            checkedInitially = false,
            onCheckedChange = { onEvent(SwitchUsingMockedFeedingPoints(it)) }
        ),
        DebugMenuItem.Divider,
        DebugMenuItem.Button(
            title = "Open Splash Screen",
            onClick = { onNavigate(MainRoute.Splash) }
        ),
        DebugMenuItem.Button(
            title = "Open SignUpFlow",
            onClick = { onNavigate(MainRoute.SignUp) }
        ),
        DebugMenuItem.Button(
            title = "Open TabsFlow",
            onClick = { onNavigate(MainRoute.Tabs) }
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        ModalDrawer(
            drawerContent = {
                DebugMenuColumn(menuItems = menuItems)
            },
            modifier = Modifier.statusBarsPadding(),
            gesturesEnabled = drawerState.isOpen,
            drawerState = drawerState,
            drawerShape = RectangleShape
        ) {
            content()
        }
        DebugMenuIconButton(
            onClick = {
                scope.launch {
                    when {
                        drawerState.isClosed -> drawerState.open()
                        else -> drawerState.close()
                    }
                }
            },
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopEnd)
        )
    }
}

@Preview
@Composable
private fun DebugMenuContentPreview() {
    AnimealTheme {
        DebugMenuContent(
            initialState = DrawerValue.Open,
            onNavigate = {},
            onEvent = {},
            content = {}
        )
    }
}