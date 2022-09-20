package com.epmedu.animeal.foundation.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Suppress("LongParameterList")
@Composable
fun BirthDateInput(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    clickable: Boolean = false,
    errorText: String = "",
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
) {
    TextInputField(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (clickable) onClick()
            },
        isEnabled = false,
        title = title,
        errorText = errorText,
        hint = stringResource(R.string.profile_birthdate_placeholder),
        onValueChange = onValueChange,
        value = value,
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

@AnimealPreview
@Composable
private fun BirthDateInputPreview() {
    AnimealTheme {
        BirthDateInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = "1, Sep 1939",
            title = "BirthDate",
            onClick = {},
            onValueChange = {},
        )
    }
}