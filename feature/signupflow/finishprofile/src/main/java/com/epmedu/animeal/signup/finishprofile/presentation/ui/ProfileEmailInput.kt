package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

// TODO: Move to a separate module
@Composable
fun EmailInput(
    value: String,
    error: String?,
    focusManager: FocusManager,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusRelease: () -> Unit,
) {
    TextInputField(
        isEnabled = isEnabled,
        title = stringResource(id = R.string.profile_email_title),
        hint = stringResource(id = R.string.profile_email_hint),
        onValueChange = onValueChange,
        value = value,
        errorText = error,
        onClearFocus = onFocusRelease,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EmailInputPreview() {
    AnimealTheme {
        EmailInput(
            value = "Email",
            error = null,
            focusManager = LocalFocusManager.current,
            onValueChange = {},
            onFocusRelease = {}
        )
    }
}