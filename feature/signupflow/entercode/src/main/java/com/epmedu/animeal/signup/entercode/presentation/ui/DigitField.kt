package com.epmedu.animeal.signup.entercode.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun DigitField(
    digit: Int?,
    onDigitInput: (Int?) -> Unit,
    onDigitRemove: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = digit?.toString() ?: "",
        onValueChange = { value ->
            when {
                value.isOneDigit() -> onDigitInput(value.toInt())
                value.isEmpty() -> onDigitRemove()
            }
        },
        modifier = modifier.size(width = 50.dp, height = 54.dp),
        textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center, lineHeight = 34.sp),
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = LocalContentColor.current,
            focusedBorderColor = Color.Gray
        )
    )
}

private fun String.isOneDigit() = isDigitsOnly() && length == 1

@AnimealPreview
@Composable
private fun DigitFieldPreview() {
    AnimealTheme {
        Column {
            DigitField(
                digit = null,
                onDigitInput = {},
                onDigitRemove = {},
                modifier = Modifier.padding(8.dp)
            )
            Divider(modifier = Modifier.width(82.dp))
            DigitField(
                digit = 4,
                onDigitInput = {},
                onDigitRemove = {},
                modifier = Modifier.padding(8.dp),
                isError = true
            )
        }
    }
}