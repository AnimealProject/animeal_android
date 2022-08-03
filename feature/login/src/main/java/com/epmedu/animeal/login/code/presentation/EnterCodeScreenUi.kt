package com.epmedu.animeal.login.code.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.spacer.HeightSpacer
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
                title = "",
                navigationIcon = {
                    BackButton(onClick = onBack)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            HeightSpacer(12.dp)
            Text(
                text = stringResource(R.string.enter_code_title),
                style = MaterialTheme.typography.h5
            )
            Text(
                text = stringResource(R.string.enter_code_subtitle, state.phoneNumber),
                modifier = Modifier.padding(top = 8.dp)
            )
            CodeRow(
                code = state.code,
                isError = state.isError,
                focusRequester = focusRequester,
                modifier = Modifier.padding(top = 32.dp),
                onDigitChange = onDigitChange
            )
            Spacer(modifier = Modifier.weight(1f))
            ResendButton(
                isEnabled = state.isResendEnabled,
                resendDelay = state.resendDelay,
                onClick = onResend,
                modifier = Modifier
                    .padding(bottom = 32.dp)
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