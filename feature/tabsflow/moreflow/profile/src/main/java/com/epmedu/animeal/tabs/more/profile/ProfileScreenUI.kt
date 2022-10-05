package com.epmedu.animeal.tabs.more.profile

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.profile.ProfileScreenEvent.Discard
import com.epmedu.animeal.tabs.more.profile.ui.ProfileContent

@Composable
internal fun ProfileScreenUI(
    state: ProfileState,
    onBack: () -> Unit,
    onProfileScreenEvent: (ProfileScreenEvent) -> Unit,
    onProfileFormEvent: (ProfileInputFormEvent) -> Unit
) {
    var showDiscardAlert by rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler(!showDiscardAlert && state.isEditedOrHasErrors()) {
        showDiscardAlert = true
    }

    if (showDiscardAlert) {
        AnimealAlertDialog(
            title = stringResource(id = R.string.profile_edit_discard),
            dismissText = stringResource(id = R.string.no),
            acceptText = stringResource(id = R.string.yes),
            onDismissRequest = { showDiscardAlert = false },
            onDismiss = { showDiscardAlert = false },
            onConfirm = {
                showDiscardAlert = false
                onProfileScreenEvent(Discard)
            }
        )
    }
    ProfileContent(
        state = state,
        onBack = {
            when {
                state.isEditedOrHasErrors() -> showDiscardAlert = true
                else -> onBack()
            }
        },
        onScreenEvent = onProfileScreenEvent,
        onInputFormEvent = onProfileFormEvent
    )
}

@AnimealPreview
@Composable
private fun ProfileScreenPreview() {
    AnimealTheme {
        ProfileScreenUI(
            state = ProfileState(),
            onBack = {},
            onProfileScreenEvent = {},
            onProfileFormEvent = {}
        )
    }
}