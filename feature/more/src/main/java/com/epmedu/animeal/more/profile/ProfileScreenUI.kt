package com.epmedu.animeal.more.profile

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealShortButton
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.DisabledButtonColor
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
            onDismissRequest = { showDiscardAlert.value = false },
            onDismiss = { showDiscardAlert.value = false },
            onConfirm = {
                showDiscardAlert.value = false
                onEvent(ProfileEvent.Discard)
            }
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            ProfileTopBar(onBack = {
                if (state.readonly) onBack()
                else showDiscardAlert.value = true
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = R.string.profile_subtitle)
                )
                ProfileInputForm(
                    state = state,
                    focusManager = focusManager,
                    onEvent = onEvent,
                )
            }

            ProfileButtonsRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                readonly = state.readonly,
                onEditClick = {
                    onEvent(ProfileEvent.Edit)
                },
                onDiscardClick = {
                    focusManager.clearFocus()
                    showDiscardAlert.value = true
                },
                onSaveClick = {
                    focusManager.clearFocus()
                    onEvent(ProfileEvent.Save)
                }
            )
        }
    }
}

@Composable
private fun ProfileTopBar(
    onBack: () -> Unit,
) {
    TopBar(
        title = stringResource(id = R.string.profile_title),
        navigationIcon = {
            BackButton(onClick = onBack)
        }
    )
}

@Composable
private fun ProfileInputForm(
    state: ProfileState,
    focusManager: FocusManager,
    onEvent: (ProfileEvent) -> Unit,
) {
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
        isEnabled = false
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
}

@Composable
private fun ProfileButtonsRow(
    modifier: Modifier = Modifier,
    readonly: Boolean,
    onEditClick: () -> Unit,
    onDiscardClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    if (readonly) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomStart,
        ) {
            AnimealShortButton(
                text = stringResource(id = R.string.edit),
                onClick = onEditClick,
            )
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimealButton(
                modifier = Modifier.weight(1f),
                backgroundColor = DisabledButtonColor,
                contentColor = MaterialTheme.colors.onPrimary,
                text = stringResource(id = R.string.discard),
                onClick = onDiscardClick,
            )

            AnimealButton(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.save),
                onClick = onSaveClick,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileScreenPreview() {
    AnimealTheme {
        ProfileScreenUI(
            state = ProfileState(),
            onBack = {},
            onEvent = {},
        )
    }
}
