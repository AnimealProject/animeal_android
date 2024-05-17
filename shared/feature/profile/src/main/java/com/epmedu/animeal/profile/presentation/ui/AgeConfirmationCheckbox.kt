package com.epmedu.animeal.profile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.checkbox.AnimealSquareCheckbox
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileInputFormState
import com.epmedu.animeal.resources.R

@Composable
internal fun AgeConfirmationCheckbox(
    state: ProfileInputFormState,
    onEvent: (ProfileInputFormEvent) -> Unit
) {
    AnimealSquareCheckbox(
        isChecked = state.isAgeConfirmed,
        onCheckedChange = { onEvent(ProfileInputFormEvent.AgeConfirmationChanged(it)) },
        label = stringResource(id = R.string.profile_age_confirmation),
        isEnabled = state.isAgeConfirmationEnabled
    )
}

@AnimealPreview
@Composable
private fun AgeConfirmationCheckboxPreview() {
    AnimealTheme {
        Column {
            AgeConfirmationCheckbox(
                state = ProfileInputFormState(isAgeConfirmationEnabled = true),
                onEvent = { }
            )
            Divider()
            AgeConfirmationCheckbox(
                state = ProfileInputFormState(
                    isAgeConfirmationEnabled = false,
                    isAgeConfirmed = true
                ),
                onEvent = { }
            )
            Divider()
            AgeConfirmationCheckbox(
                state = ProfileInputFormState(
                    isAgeConfirmationEnabled = true,
                    isAgeConfirmed = true
                ),
                onEvent = { }
            )
        }
    }
}