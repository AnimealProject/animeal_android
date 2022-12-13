package com.epmedu.animeal.signup.enterphone.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealShortButton
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.enterphone.presentation.viewmodel.EnterPhoneState

@Composable
internal fun EnterPhoneScreenUi(
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
                value = state.phoneNumber,
                modifier = Modifier
                    .padding(top = 56.dp)
                    .focusRequester(focusRequester),
                onValueChange = onNumberChange,
                error = if (state.isError) stringResource(id = R.string.enter_phone_error) else ""
            )
        }
    }
}

@AnimealPreview
@Composable
private fun EnterPhoneScreenPreview() {
    AnimealTheme {
        EnterPhoneScreenUi(
            focusRequester = FocusRequester(),
            state = EnterPhoneState(),
            onNumberChange = {},
            onBack = {},
            onNext = {}
        )
    }
}