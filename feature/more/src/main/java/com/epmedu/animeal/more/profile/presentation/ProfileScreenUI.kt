package com.epmedu.animeal.more.profile.presentation

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.Discard
import com.epmedu.animeal.more.profile.presentation.ui.ProfileContent
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.resources.R

@Composable
internal fun ProfileScreenUI(
    state: ProfileState,
    onBack: () -> Unit,
    onEvent: (ProfileScreenEvent) -> Unit
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
                onEvent(Discard)
            }
        )
    }
    ProfileContent(
        state = state,
        onEvent = onEvent,
        onBack = {
            when {
                state.isEditedOrHasErrors() -> showDiscardAlert = true
                else -> onBack()
            }
        }
    )
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
