package com.epmedu.animeal.foundation.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.foundation.theme.CustomColor.DarkerGrey
import com.epmedu.animeal.foundation.theme.CustomColor.LynxWhite
import com.epmedu.animeal.foundation.theme.interFontFamily

@Suppress("LongParameterList")
@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    title: String,
    hint: String,
    onValueChange: (String) -> Unit,
    value: String,
    isEnabled: Boolean = true,
    errorText: String = "",
    onClearFocus: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var focusedDirty by rememberSaveable { mutableStateOf(false) }
    val borderColor: Color = if (errorText.isEmpty()) LynxWhite else CustomColor.Error

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 2.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle2
        )

        TextInputFieldBox(
            value = value,
            hint = hint,
            isEnabled = isEnabled,
            onValueChange = onValueChange,
            onFocusChange = { focusState ->
                if (focusState.isFocused && !focusedDirty) {
                    focusedDirty = true
                }

                if (!focusState.isFocused && focusedDirty) {
                    onClearFocus()
                }
            },
            borderColor = borderColor,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = errorText,
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraLight
        )
    }
}

@Suppress("LongParameterList")
@Composable
private fun TextInputFieldBox(
    value: String,
    hint: String,
    isEnabled: Boolean,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    borderColor: Color,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { onFocusChange(it) }
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .background(
                color = LynxWhite,
                shape = RoundedCornerShape(12.dp)
            ),
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = interFontFamily
        ),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Black,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = {
            Text(
                text = hint,
                color = DarkerGrey,
                fontWeight = FontWeight.ExtraLight
            )
        },
        singleLine = true,
        enabled = isEnabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@AnimealPreview
@Composable
private fun TextInputFieldPreview() {
    AnimealTheme {
        Column {
            TextInputField(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Empty",
                hint = "Hint",
                onValueChange = {},
                value = ""
            )
            Divider()
            TextInputField(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "With value",
                hint = "Enter your name",
                onValueChange = {},
                value = "Value"
            )
            Divider()
            TextInputField(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "With error",
                hint = "Hint",
                onValueChange = {},
                value = "Wrong value",
                errorText = "Value is wrong"
            )
            Divider()
            TextInputField(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Disabled",
                hint = "Hint",
                onValueChange = {},
                value = "Wrong value",
                isEnabled = false
            )
        }
    }
}
