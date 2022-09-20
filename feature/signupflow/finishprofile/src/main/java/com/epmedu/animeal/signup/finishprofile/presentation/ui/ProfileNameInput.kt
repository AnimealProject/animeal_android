package com.epmedu.animeal.signup.finishprofile.presentation.ui

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

// TODO: Move to a separate module
@Composable
fun NameInput(
    value: String,
    onValueChange: (String) -> Unit,
    error: String,
    isEnabled: Boolean = true,
    onClearFocus: () -> Unit = {},
    onNext: KeyboardActionScope.() -> Unit = {},
) {
    TextInputField(
        isEnabled = isEnabled,
        title = stringResource(id = R.string.profile_name_title),
        hint = stringResource(id = R.string.profile_name_hint),
        onValueChange = onValueChange,
        value = value,
        errorText = error,
        onClearFocus = onClearFocus,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = onNext)
    )
}

@AnimealPreview
@Composable
private fun NameInputPreview() {
    AnimealTheme {
        NameInput(
            value = "Name",
            onValueChange = {},
            error = ""
        )
    }
}