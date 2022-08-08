package com.epmedu.animeal.login.code.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.login.code.domain.model.EnterCodeState
import com.epmedu.animeal.login.code.ui.CodeRow
import com.epmedu.animeal.login.code.ui.ResendButton
import com.epmedu.animeal.resources.R

@Composable
internal fun EnterCodeScreenUi(
    state: EnterCodeState,
    focusRequester: FocusRequester,
    onBack: () -> Unit,
    onDigitChange: (position: Int, digit: Int?) -> Unit,
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
            modifier = Modifier.padding(horizontal = 24.dp),
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
                onDigitChange = onDigitChange
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

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EnterCodeScreenPreview() {
    AnimealTheme {
        EnterCodeScreenUi(
            state = EnterCodeState(
                code = listOf(null, null, null, null),
                phoneNumber = "558 49-99-69"
            ),
            focusRequester = FocusRequester(),
            onBack = {},
            onDigitChange = { _, _ -> },
            onResend = {}
        )
    }
}