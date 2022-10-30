package com.epmedu.animeal.signup.entercode.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.epmedu.animeal.foundation.input.CustomPaddingOutlinedTextField
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
    CustomPaddingOutlinedTextField(
        value = digit?.toString() ?: "",
        onValueChange = { value ->
            when {
                value.isOneDigit() -> onDigitInput(value.toInt())
                value.isEmpty() -> onDigitRemove()
            }
        },
        modifier = modifier.size(width = 49.dp, height = 53.dp),
        textStyle = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center, lineHeight = 34.sp),
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = LocalContentColor.current,
            focusedBorderColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        customPadding = 8.dp
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