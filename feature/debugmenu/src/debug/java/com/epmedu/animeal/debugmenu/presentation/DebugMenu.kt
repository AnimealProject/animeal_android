package com.epmedu.animeal.debugmenu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent.SwitchUsingMockedFeedingPoints
import com.epmedu.animeal.debugmenu.presentation.ui.DebugMenuButton
import com.epmedu.animeal.debugmenu.presentation.viewmodel.DebugMenuViewModel
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun DebugMenu(
    navController: NavController,
    initialState: DrawerValue = DrawerValue.Closed,
    content: @Composable () -> Unit
) {
    val viewModel: DebugMenuViewModel = hiltViewModel()
    var useMockedFeedingPoints by remember { mutableStateOf(false) }

    ModalDrawer(
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Switch(
                        checked = useMockedFeedingPoints,
                        onCheckedChange = {
                            viewModel.handleEvents(SwitchUsingMockedFeedingPoints(it))
                            useMockedFeedingPoints = it
                        }
                    )
                    Text(text = "Use mocked feeding points")
                }
                Divider()
                DebugMenuButton(
                    text = "Open Splash Screen",
                    onClick = { navController.navigate(route = MainRoute.Splash.name) }
                )
                DebugMenuButton(
                    text = "Open SignUpFlow",
                    onClick = { navController.navigate(route = MainRoute.SignUp.name) }
                )
                DebugMenuButton(
                    text = "Open TabsFlow",
                    onClick = { navController.navigate(route = MainRoute.Tabs.name) }
                )
                Divider()
                AnimealButton(onClick = { /*TODO*/ }) {
                    Text(text = "Logout")
                }
                AnimealButton(onClick = { /*TODO*/ }) {
                    Text(text = "Delete user")
                }
            }
        },
        modifier = Modifier.statusBarsPadding(),
        drawerState = DrawerState(initialState),
        drawerShape = RectangleShape
    ) {
        content()
    }
}

@AnimealPreview
@Composable
fun DebugMenuPreview() {
    AnimealTheme {
        DebugMenu(
            navController = NavController(LocalContext.current),
            initialState = DrawerValue.Open,
            content = {}
        )
    }
}