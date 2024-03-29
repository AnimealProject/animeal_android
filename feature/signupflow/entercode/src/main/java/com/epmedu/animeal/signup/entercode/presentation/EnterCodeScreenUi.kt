package com.epmedu.animeal.signup.entercode.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.entercode.presentation.ui.CodeRow
import com.epmedu.animeal.signup.entercode.presentation.ui.ResendButton
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeState
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeViewModel.Companion.emptyCode

@Composable
internal fun EnterCodeScreenUi(
    state: EnterCodeState,
    focusRequester: FocusRequester,
    onBack: () -> Unit,
    onNumberChange: (position: Int, digit: String?) -> Unit,
    onResend: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopBar(
                title = stringResource(R.string.enter_code_title),
                navigationIcon = {
                    BackButton(onClick = onBack)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(R.string.enter_code_subtitle, state.phoneNumber),
                style = MaterialTheme.typography.subtitle1,
            )
            CodeRow(
                modifier = Modifier.padding(top = 32.dp),
                code = state.code,
                isError = state.isError,
                focusRequester = focusRequester,
                onNumberChange = onNumberChange
            )
            Spacer(modifier = Modifier.weight(1f))
            ResendButton(
                isEnabled = state.isResendEnabled,
                resendDelay = state.resendDelay,
                onClick = onResend,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@AnimealPreview
@Composable
private fun EnterCodeScreenPreview() {
    AnimealTheme {
        EnterCodeScreenUi(
            state = EnterCodeState(
                code = emptyCode(),
                phoneNumber = "558 49-99-69"
            ),
            focusRequester = FocusRequester(),
            onBack = {},
            onNumberChange = { _, _ -> },
            onResend = {}
        )
    }
}