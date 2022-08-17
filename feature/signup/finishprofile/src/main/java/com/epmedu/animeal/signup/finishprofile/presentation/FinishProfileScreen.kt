package com.epmedu.animeal.signup.finishprofile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.navigation.route.MainRoute
import com.epmedu.animeal.navigation.route.SignUpRoute
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileViewModel

@Composable
fun FinishProfileScreen() {
    val viewModel: FinishProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()

    FinishProfileScreenUI(
        state = state,
        onCancel = {
            navigator.popBackStack(SignUpRoute.EnterPhone.name)
        }
    ) { event ->
        viewModel.handleEvents(event)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                FinishProfileEvent.Saved -> {
                    navigator.navigateToTabs()
                }
            }
        }
    }
}

private fun Navigator.navigateToTabs() {
    parent?.navigate(MainRoute.Tabs.name) {
        popUpTo(MainRoute.SignUp.name) {
            inclusive = true
        }
    }
}