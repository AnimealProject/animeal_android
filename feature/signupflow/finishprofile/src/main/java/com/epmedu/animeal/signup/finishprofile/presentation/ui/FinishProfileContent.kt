package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.ui.ProfileInputForm
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.resources.R

@Composable
internal fun FinishProfileContent(
    state: ProfileState,
    focusRequester: FocusRequester,
    onCancel: () -> Unit,
    onDone: () -> Unit,
    onInputFormEvent: (ProfileInputFormEvent) -> Unit
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
                BackButton(onClick = onCancel)
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
                onEvent = onInputFormEvent,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .focusRequester(focusRequester)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        FinishProfileButtonsRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 40.dp),
            onCancelClick = {
                focusManager.clearFocus()
                onCancel()
            },
            onDoneClick = {
                focusManager.clearFocus()
                onDone()
            }
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FinishProfileContentPreview() {
    AnimealTheme {
        FinishProfileContent(
            state = ProfileState(),
            focusRequester = FocusRequester(),
            onCancel = {},
            onDone = {},
            onInputFormEvent = {}
        )
    }
}
