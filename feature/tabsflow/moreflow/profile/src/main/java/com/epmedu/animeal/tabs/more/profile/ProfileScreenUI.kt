package com.epmedu.animeal.tabs.more.profile

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.profile.ProfileScreenEvent.Discard
import com.epmedu.animeal.tabs.more.profile.ui.ProfileContent
import com.epmedu.animeal.tabs.more.profile.viewmodel.ProfileState

@Composable
internal fun ProfileScreenUI(
    state: ProfileState,
    onBack: () -> Unit,
    onProfileScreenEvent: (ProfileScreenEvent) -> Unit
) {
    val inputFormState = state.profileInputFormState
    var showDiscardAlert by rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler(!showDiscardAlert && inputFormState.isEditedOrHasErrors()) {
        showDiscardAlert = true
    }

    if (showDiscardAlert) {
        AnimealQuestionDialog(
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
        state = inputFormState,
        onBack = {
            when {
                inputFormState.isEditedOrHasErrors() -> showDiscardAlert = true
                else -> onBack()
            }
        },
        onScreenEvent = onProfileScreenEvent
    )
}

@AnimealPreview
@Composable
private fun ProfileScreenPreview() {
    AnimealTheme {
        ProfileScreenUI(
            state = ProfileState(),
            onBack = {},
            onProfileScreenEvent = {}
        )
    }
}
