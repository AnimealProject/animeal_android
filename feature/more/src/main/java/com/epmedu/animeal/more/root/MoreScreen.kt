package com.epmedu.animeal.more.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.more.MoreViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun MoreScreen() {
    val viewModel: MoreViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            if (it is MoreViewModel.Event.NavigateToOnboarding) {
                navigator.parent?.parent?.navigate(MainScreenRoute.Onboarding.name) {
                    popUpTo(MainScreenRoute.Tabs.name) {
                        inclusive = true
                    }
                }
            }
        }
    }

    MoreScreenUi(
        onLogout = viewModel::logout,
        onNavigate = navigator::navigate,
    )
}
