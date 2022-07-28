package com.epmedu.animeal.login.profile.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.login.profile.ui.BirthDateInput
import com.epmedu.animeal.login.profile.ui.EmailInput
import com.epmedu.animeal.login.profile.ui.NameInput
import com.epmedu.animeal.login.profile.ui.SurnameInput
import com.epmedu.animeal.resources.R

@Suppress("LongMethod")
@Composable
internal fun FinishProfileScreenUI(
    state: FinishProfileState,
    onCancel: () -> Unit,
    onEvent: (FinishProfileEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val showCancellationAlert = rememberSaveable { mutableStateOf(false) }

    BackHandler(!showCancellationAlert.value) {
        showCancellationAlert.value = true
    }

    if (showCancellationAlert.value) {
        AnimealAlertDialog(
            title = stringResource(id = R.string.profile_registration_cancel),
            dismissText = stringResource(id = R.string.no),
            acceptText = stringResource(id = R.string.yes),
            onDismiss = {
                showCancellationAlert.value = false
            },
            onConfirm = {
                showCancellationAlert.value = false
                onCancel()
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(id = R.string.profile_title))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeightSpacer(12.dp)
            Text(text = stringResource(id = R.string.profile_subtitle))
            NameInput(
                value = state.name,
                error = state.nameError?.asString(),
                focusManager = focusManager,
                onValueChange = {
                    onEvent(FinishProfileEvent.NameChanged(it))
                },
                onFocusRelease = {
                    onEvent(FinishProfileEvent.ValidateName)
                }
            )
            SurnameInput(
                value = state.surname,
                error = state.surnameError?.asString(),
                focusManager = focusManager,
                onValueChange = {
                    onEvent(FinishProfileEvent.SurnameChanged(it))
                },
                onFocusRelease = {
                    onEvent(FinishProfileEvent.ValidateSurname)
                }
            )
            EmailInput(
                value = state.email,
                error = state.emailError?.asString(),
                focusManager = focusManager,
                onValueChange = {
                    onEvent(FinishProfileEvent.EmailChanged(it))
                },
                onFocusRelease = {
                    onEvent(FinishProfileEvent.ValidateEmail)
                }
            )
            PhoneNumberInput(
                title = stringResource(id = R.string.profile_phone_number),
                value = state.phoneNumber,
                isEnabbled = false
            )
            BirthDateInput(
                value = state.birthDate,
                error = state.birthDateError?.asString(),
                initialDate = state.initialDate,
                focusManager = focusManager,
                onValueChange = {
                    onEvent(FinishProfileEvent.BirthDateChanged(it))
                },
                onFocusRelease = {
                    onEvent(FinishProfileEvent.ValidateBirthDate)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                AnimealButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.cancel),
                    onClick = {
                        focusManager.clearFocus()

                        showCancellationAlert.value = true
                    },
                )

                AnimealButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.done),
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(FinishProfileEvent.Submit)
                    },
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EnterPhoneScreenPreview() {
    AnimealTheme {
        FinishProfileScreen()
    }
}