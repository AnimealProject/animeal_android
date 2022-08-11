package com.epmedu.animeal.login.phone.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealShortButton
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.login.phone.domain.model.EnterPhoneState
import com.epmedu.animeal.resources.R

@Composable
internal fun EnterPhoneScreenUI(
    state: EnterPhoneState,
    focusRequester: FocusRequester,
    onNumberChange: (String) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopBar(
                title = stringResource(R.string.enter_phone_title),
                navigationIcon = {
                    BackButton(onClick = onBack)
                }
            )
        },
        floatingActionButton = {
            AnimealShortButton(
                text = stringResource(id = R.string.next),
                enabled = state.isNextEnabled,
                onClick = onNext
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            PhoneNumberInput(
                modifier = Modifier
                    .padding(top = 56.dp)
                    .focusRequester(focusRequester),
                title = stringResource(id = R.string.phone_number),
                onValueChange = onNumberChange,
                value = state.phoneNumber
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EnterPhoneScreenPreview() {
    AnimealTheme {
        EnterPhoneScreen()
    }
}