package com.epmedu.animeal.signup.entercode.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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
    onNumberInput: (String) -> Unit,
    onDigitRemove: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    CustomPaddingOutlinedTextField(
        value = digit?.toString() ?: "",
        onValueChange = { value ->
            when {
                value.isEmpty() -> onDigitRemove()
                value.isDigitsOnly() -> onNumberInput(value)
            }
        },
        modifier = modifier
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colors.background)
            .size(width = 49.dp, height = 53.dp),
        textStyle = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center, lineHeight = 34.sp),
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.None
        ),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = LocalContentColor.current,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        customPadding = 8.dp
    )
}

@AnimealPreview
@Composable
private fun DigitFieldPreview() {
    AnimealTheme {
        Column {
            DigitField(
                digit = null,
                onNumberInput = {},
                onDigitRemove = {},
                modifier = Modifier.padding(8.dp)
            )
            Divider(modifier = Modifier.width(82.dp))
            DigitField(
                digit = 4,
                onNumberInput = {},
                onDigitRemove = {},
                modifier = Modifier.padding(8.dp),
                isError = true
            )
        }
    }
}