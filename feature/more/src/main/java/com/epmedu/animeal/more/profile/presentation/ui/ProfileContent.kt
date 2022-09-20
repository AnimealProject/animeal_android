package com.epmedu.animeal.more.profile.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.Edit
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.Save
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.resources.R

@Composable
internal fun ProfileContent(
    state: ProfileState,
    onBack: () -> Unit,
    onEvent: (ProfileScreenEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        TopBar(
            title = stringResource(id = R.string.profile_title),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(id = R.string.profile_subtitle),
                style = MaterialTheme.typography.subtitle1,
            )
            ProfileInputForm(
                state = state,
                onEvent = onEvent,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ProfileButton(
            state = state.formState,
            onEdit = { onEvent(Edit) },
            onSave = {
                focusManager.clearFocus()
                onEvent(Save)
            },
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .padding(top = 16.dp, bottom = 40.dp)
        )
    }
}

@AnimealPreview
@Composable
private fun ProfileContentPreview() {
    AnimealTheme {
        Surface {
            ProfileContent(
                state = ProfileState(),
                onBack = {},
                onEvent = {}
            )
        }
    }
}