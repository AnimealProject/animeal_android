package com.epmedu.animeal.tabs.more.account

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent.NavigatedToOnboarding
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent.ToastShown
import com.epmedu.animeal.tabs.more.account.viewmodel.AccountToast
import com.epmedu.animeal.tabs.more.account.viewmodel.AccountViewModel

@Composable
fun AccountScreen() {
    val viewModel: AccountViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current

    BottomBarVisibility(HIDDEN)

    when (state.toastToShow) {
        AccountToast.SuccessfulLogout -> {
            Toast.makeText(context, R.string.profile_logout_success, Toast.LENGTH_LONG).show()
            viewModel.handleScreenEvent(ToastShown)
        }
        AccountToast.SuccessfulDelete -> {
            Toast.makeText(context, R.string.account_delete_success, Toast.LENGTH_LONG).show()
            viewModel.handleScreenEvent(ToastShown)
        }
        null -> {}
    }

    if (state.isNavigatingToOnboarding) {
        navigator.parent?.parent?.navigate(MainRoute.SignUp.name) {
            popUpTo(MainRoute.Tabs.name) {
                inclusive = true
            }
        }
        viewModel.handleScreenEvent(NavigatedToOnboarding)
    }

    AccountScreenUI(
        onBack = navigator::popBackStack,
        onEvent = viewModel::handleScreenEvent,
    )
}
