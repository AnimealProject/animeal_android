package com.epmedu.animeal.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.domain.EnterCodeViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun EnterCodeScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: EnterCodeViewModel = viewModel()
    val focusRequester = remember { FocusRequester() }
    val model by viewModel.model.collectAsState()

    EnterCodeScreenUi(
        model = model,
        focusRequester = focusRequester,
        onBack = navigator::popBackStack,
        onDigitChange = { position, digit ->
            viewModel.changeDigit(position, digit)
        },
        onResend = {
            viewModel.resendCode()
            focusRequester.requestFocus()
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()

        viewModel.isCodeCorrect.collectLatest { isCorrect ->
            if (isCorrect) {
                // TODO: Replace route with FinishProfileScreen
                navigator.navigateToTabs()
            }
        }
    }
}