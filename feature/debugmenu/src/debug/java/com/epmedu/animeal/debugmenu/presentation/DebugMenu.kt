package com.epmedu.animeal.debugmenu.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.epmedu.animeal.debugmenu.presentation.ui.DebugMenuContent
import com.epmedu.animeal.debugmenu.presentation.viewmodel.DebugMenuViewModel

@Composable
fun DebugMenu(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val viewModel: DebugMenuViewModel = hiltViewModel()

    DebugMenuContent(
        onNavigate = { mainRoute -> navController.navigate(mainRoute.name) },
        onEvent = viewModel::handleEvents
    ) {
        content()
    }
}