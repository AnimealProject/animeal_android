package com.epmedu.animeal.more.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealShortButton
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.login.profile.ui.BirthDateInput
import com.epmedu.animeal.login.profile.ui.EmailInput
import com.epmedu.animeal.login.profile.ui.NameInput
import com.epmedu.animeal.login.profile.ui.SurnameInput
import com.epmedu.animeal.resources.R

@Suppress("LongMethod")
@Composable
internal fun ProfileScreenUI(
    state: ProfileState,
    onBack: () -> Unit,
    onEvent: (ProfileEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val showDiscardAlert = rememberSaveable { mutableStateOf(false) }

    BackHandler(!showDiscardAlert.value && !state.readonly) {
        showDiscardAlert.value = true
    }

    if (showDiscardAlert.value) {
        AnimealAlertDialog(
            title = stringResource(id = R.string.profile_edit_discard),
            dismissText = stringResource(id = R.string.no),
            acceptText = stringResource(id = R.string.yes),
            onDismiss = {
                showDiscardAlert.value = false
            },
            onConfirm = {
                showDiscardAlert.value = false
                onEvent(ProfileEvent.Discard)
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(id = R.string.profile_title)) {
                BackButton(onClick = {
                    if (state.readonly) {
                        onBack()
                    } else {
                        showDiscardAlert.value = true
                    }
                })
            }
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
                isEnabled = !state.readonly,
                value = state.name,
                error = state.nameError?.asString(),
                focusManager = focusManager,
                onValueChange = {
                    onEvent(ProfileEvent.NameChanged(it))
                },
                onFocusRelease = {
                    onEvent(ProfileEvent.ValidateName)
                }
            )
            SurnameInput(
                isEnabled = !state.readonly,
                value = state.surname,
                error = state.surnameError?.asString(),
                focusManager = focusManager,
                onValueChange = {
                    onEvent(ProfileEvent.SurnameChanged(it))
                },
                onFocusRelease = {
                    onEvent(ProfileEvent.ValidateSurname)
                }
            )
            EmailInput(
                isEnabled = !state.readonly,
                value = state.email,
                error = state.emailError?.asString(),
                focusManager = focusManager,
                onValueChange = {
                    onEvent(ProfileEvent.EmailChanged(it))
                },
                onFocusRelease = {
                    onEvent(ProfileEvent.ValidateEmail)
                }
            )
            PhoneNumberInput(
                title = stringResource(id = R.string.profile_phone_number),
                value = state.phoneNumber,
                isEnabbled = false
            )
            BirthDateInput(
                isEnabled = !state.readonly,
                value = state.birthDate,
                error = state.birthDateError?.asString(),
                initialDate = state.initialDate,
                focusManager = focusManager,
                onValueChange = {
                    onEvent(ProfileEvent.BirthDateChanged(it))
                },
                onFocusRelease = {
                    onEvent(ProfileEvent.ValidateBirthDate)
                }
            )

            if (state.readonly) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    contentAlignment = Alignment.BottomStart,
                ) {
                    AnimealShortButton(
                        text = stringResource(id = R.string.edit),
                        onClick = {
                            onEvent(ProfileEvent.Edit)
                        },
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(48.dp)
                ) {
                    AnimealButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.discard),
                        onClick = {
                            focusManager.clearFocus()
                            showDiscardAlert.value = true
                        },
                    )

                    AnimealButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.save),
                        onClick = {
                            focusManager.clearFocus()
                            onEvent(ProfileEvent.Save)
                        },
                    )
                }
            }
        }
    }
}
