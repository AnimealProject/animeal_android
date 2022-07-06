package com.epmedu.animeal.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epmedu.animeal.login.domain.CodeConfirmationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

internal const val FOCUS_DELAY = 300L

@Composable
fun CodeConfirmationScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val viewModel: CodeConfirmationViewModel = viewModel()
    val focusRequester = remember { FocusRequester() }
    val state by viewModel.state.collectAsState()

    CodeConfirmationScreenUi(
        state = state,
        focusRequester = focusRequester,
        onBack = onBack,
        onDigitChange = { position, digit ->
            viewModel.changeDigit(position, digit)
        },
        onResend = {
            viewModel.resendCode()
            focusRequester.requestFocus()
        }
    )

    LaunchedEffect(Unit) {
        delay(FOCUS_DELAY)
        focusRequester.requestFocus()

        viewModel.state.collectLatest {
            if (it.isCodeCorrect == true) onNext()
        }
    }
}