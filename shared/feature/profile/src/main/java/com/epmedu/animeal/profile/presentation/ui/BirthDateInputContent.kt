package com.epmedu.animeal.profile.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
internal fun BirthDateInputContent(
    value: String,
    error: String,
    onClick: () -> Unit,
    clickable: Boolean = true,
) {
    TextInputField(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (clickable) {
                    onClick()
                }
            },
        isEnabled = false,
        title = stringResource(R.string.profile_birth_date),
        errorText = error,
        hint = stringResource(R.string.profile_birthdate_placeholder),
        value = value,
        onValueChange = {},
        trailingIcon = {
            IconButton(
                onClick = onClick,
                enabled = clickable
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_birthdate),
                    tint = CustomColor.HitGrey,
                    contentDescription = null,
                )
            }
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BirthDateInputContentPreview() {
    AnimealTheme {
        Column {
            BirthDateInputContent(
                value = "",
                error = "",
                onClick = {}
            )
            Divider()
            BirthDateInputContent(
                value = "01 Jan, 1970",
                error = "",
                onClick = {}
            )
            Divider()
            BirthDateInputContent(
                value = "",
                error = "Select birthdate",
                onClick = {}
            )
        }
    }
}