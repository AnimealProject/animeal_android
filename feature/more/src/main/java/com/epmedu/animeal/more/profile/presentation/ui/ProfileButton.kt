package com.epmedu.animeal.more.profile.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.EDITABLE
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.EDITED
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.READ_ONLY
import com.epmedu.animeal.resources.R

@Composable
internal fun ProfileButton(
    state: FormState,
    onEdit: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (state == READ_ONLY) {
            AnimealButton(
                text = stringResource(R.string.edit),
                onClick = onEdit
            )
        } else {
            AnimealButton(
                text = stringResource(R.string.save),
                enabled = state == EDITED,
                onClick = onSave
            )
        }
    }
}

@AnimealPreview
@Composable
private fun ProfileButtonPreview() {
    AnimealTheme {
        Column {
            ProfileButton(
                state = READ_ONLY,
                onEdit = {},
                onSave = {}
            )
            Divider()
            ProfileButton(
                state = EDITABLE,
                onEdit = {},
                onSave = {}
            )
            Divider()
            ProfileButton(
                state = EDITED,
                onEdit = {},
                onSave = {}
            )
        }
    }
}