package com.epmedu.animeal.signup.finishprofile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.effect.DisplayedEffect
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.NavigatedToNextDestination
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ScreenDisplayed
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileNextDestination.EnterCode
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileNextDestination.Onboarding
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileNextDestination.Tabs
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileViewModel

@Composable
fun FinishProfileScreen() {
    val viewModel: FinishProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.nextDestination) {
        when (state.nextDestination) {
            Onboarding -> navigator.popBackStackOrNavigate(SignUpRoute.Onboarding.name)
            Tabs -> navigator.navigateToTabs()
            EnterCode -> navigator.navigate(SignUpRoute.EnterCode.name)
            else -> {}
        }

        if (state.nextDestination != null) viewModel.handleScreenEvents(NavigatedToNextDestination)
    }

    DisplayedEffect {
        viewModel.handleScreenEvents(ScreenDisplayed)
    }

    FinishProfileScreenUI(
        state = state,
        focusRequester = focusRequester,
        onEvent = viewModel::handleScreenEvents
    )
}

private fun Navigator.navigateToTabs() {
    parent?.navigate(MainRoute.Tabs.name) {
        popUpTo(MainRoute.SignUp.name) {
            inclusive = true
        }
    }
}