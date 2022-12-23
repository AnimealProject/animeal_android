package com.epmedu.animeal.signup.enterphone.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.signup.enterphone.presentation.viewmodel.EnterPhoneEvent
import com.epmedu.animeal.signup.enterphone.presentation.viewmodel.EnterPhoneViewModel

@Composable
fun EnterPhoneScreen() {
    val viewModel: EnterPhoneViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow
    val state by viewModel.stateFlow.collectAsState()
    val focusRequester = remember { FocusRequester() }

    EnterPhoneScreenUi(
        state = state,
        focusRequester = focusRequester,
        onEvent = viewModel::handleEvents,
        onBack = navigator::popBackStack
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            if (it is EnterPhoneEvent.NavigateToEnterCode) {
                navigator.navigate(SignUpRoute.EnterCode.name)
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}