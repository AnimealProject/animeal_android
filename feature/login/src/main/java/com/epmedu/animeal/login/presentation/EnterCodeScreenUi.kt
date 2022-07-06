package com.epmedu.animeal.login.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.login.domain.EnterCodeViewModel.Companion.PHONE_NUMBER_PLACEHOLDER
import com.epmedu.animeal.login.domain.model.EnterCodeScreenModel
import com.epmedu.animeal.login.ui.CodeRow
import com.epmedu.animeal.login.ui.ResendButton
import com.epmedu.animeal.resources.R

@Composable
internal fun EnterCodeScreenUi(
    state: EnterCodeScreenModel,
    focusRequester: FocusRequester,
    onBack: () -> Unit,
    onDigitChange: (position: Int, digit: Int?) -> Unit,
    onResend: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
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
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.enter_verification_code),
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "${stringResource(id = R.string.code_was_sent_to)} ${state.phoneNumber}",
                modifier = Modifier.padding(top = 8.dp)
            )
            CodeRow(
                code = state.code,
                isError = state.isCodeCorrect == false,
                focusRequester = focusRequester,
                modifier = Modifier.padding(top = 36.dp),
                onDigitChange = onDigitChange
            )
            Spacer(modifier = Modifier.weight(1f))
            ResendButton(
                isEnabled = state.isResendEnabled,
                resendDelay = state.resendDelay,
                onClick = onResend,
                modifier = Modifier
                    .padding(bottom = 20.dp)
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
            state = EnterCodeScreenModel(
                phoneNumber = PHONE_NUMBER_PLACEHOLDER,
                code = listOf(null, null, null, null)
            ),
            focusRequester = FocusRequester(),
            onBack = {},
            onDigitChange = { _, _ -> },
            onResend = {}
        )
    }
}