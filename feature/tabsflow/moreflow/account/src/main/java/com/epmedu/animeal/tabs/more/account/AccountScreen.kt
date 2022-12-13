package com.epmedu.animeal.tabs.more.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.more.account.viewmodel.AccountEvent.NavigateToOnboarding
import com.epmedu.animeal.tabs.more.account.viewmodel.AccountViewModel

@Composable
fun AccountScreen() {
    val viewModel: AccountViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow

    BottomBarVisibility(HIDDEN)

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            if (it is NavigateToOnboarding) {
                navigator.parent?.parent?.navigate(MainRoute.SignUp.name) {
                    popUpTo(MainRoute.Tabs.name) {
                        inclusive = true
                    }
                }
            }
        }
    }

    AccountScreenUI(
        onBack = navigator::popBackStack,
        onLogout = viewModel::logout,
    )
}
