package com.epmedu.animeal.profile.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Suppress("LongParameterList")
@Composable
internal fun EmailInput(
    value: String,
    onValueChange: (String) -> Unit,
    error: String,
    imeAction: ImeAction,
    isEnabled: Boolean = true,
    onNext: KeyboardActionScope.() -> Unit = {},
    onDone: KeyboardActionScope.() -> Unit = {}
) {
    TextInputField(
        isEnabled = isEnabled,
        title = stringResource(id = R.string.profile_email_title),
        hint = stringResource(id = R.string.profile_email_hint),
        onValueChange = onValueChange,
        value = value,
        errorText = error,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onDone = onDone,
            onNext = onNext
        )
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EmailInputPreview() {
    AnimealTheme {
        EmailInput(
            value = "Email",
            onValueChange = {},
            error = "",
            imeAction = ImeAction.Default
        )
    }
}