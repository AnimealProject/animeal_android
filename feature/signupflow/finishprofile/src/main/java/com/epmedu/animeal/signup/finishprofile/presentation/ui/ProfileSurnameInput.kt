package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

// TODO: Move to a separate module
@Composable
fun SurnameInput(
    value: String,
    error: String?,
    focusManager: FocusManager,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusRelease: () -> Unit,
) {
    TextInputField(
        isEnabled = isEnabled,
        title = stringResource(id = R.string.profile_surname_title),
        hint = stringResource(id = R.string.profile_surname_hint),
        onValueChange = onValueChange,
        value = value,
        errorText = error,
        onClearFocus = onFocusRelease,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SurnnameInputPreview() {
    AnimealTheme {
        Surface {
            SurnameInput(
                value = "Surname",
                error = null,
                focusManager = LocalFocusManager.current,
                onValueChange = {},
                onFocusRelease = {}
            )
        }
    }
}