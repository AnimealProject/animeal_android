package com.epmedu.animeal.tabs.more.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.openWebsite
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.presentation.viewmodel.MoreToast
import com.epmedu.animeal.tabs.more.presentation.viewmodel.MoreViewModel

@Composable
fun MoreScreen(
    onDisablingRouteForGuest: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel = hiltViewModel<MoreViewModel>()
    val state by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current

    val optionNameToUrl: Map<String, String> = stringArrayResource(R.array.legal_links)
        .map {
            val (option, _, url) = it.split("|")
            option to url
        }
        .toMap()

    BottomBarVisibility(viewModel.parentRoute?.let { HIDDEN } ?: SHOWN)

    when (state.toastToShow) {
        MoreToast.SuccessfulLogout -> {
            Toast.makeText(context, R.string.profile_logout_success, Toast.LENGTH_LONG).show()
            viewModel.handleScreenEvent(MoreScreenEvent.ToastShown)
        }
        MoreToast.SuccessfulDelete -> {
            Toast.makeText(context, R.string.account_delete_success, Toast.LENGTH_LONG).show()
            viewModel.handleScreenEvent(MoreScreenEvent.ToastShown)
        }
        null -> {}
    }

    if (state.isNavigatingToOnboarding) {
        navigator.parent?.parent?.navigate(MainRoute.SignUp.name) {
            popUpTo(MainRoute.Tabs.name) {
                inclusive = true
            }
        }
        viewModel.handleScreenEvent(MoreScreenEvent.GoToOnboarding)
    }

    MoreScreenUi(
        state = state,
        onBack = navigator::popBackStack,
        onDisablingRouteForGuest = onDisablingRouteForGuest,
        onNavigate = {
            when (it) {
                MoreRoute.Account.name -> navigator.navigate("${MoreRoute.More.name}/$it")
                MoreRoute.Terms.name,
                MoreRoute.Policy.name -> optionNameToUrl[it]?.let(context::openWebsite)
                else -> navigator.navigate(it)
            }
        },
        onEvent = viewModel::handleScreenEvent
    )
}
