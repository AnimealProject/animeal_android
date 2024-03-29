package com.epmedu.animeal.foundation.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor

@Composable
fun ChipNumberInput(
    title: String,
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    errorText: String? = null
) {
    val borderColor: Color = if (errorText == null) CustomColor.LynxWhite else CustomColor.Error
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            modifier = Modifier
                .padding(bottom = 2.dp),
            style = TextStyle(
                color = MaterialTheme.colors.onSurface,
                fontSize = 14.sp
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
                .background(
                    color = CustomColor.LynxWhite,
                    shape = RoundedCornerShape(12.dp)
                ),
            value = value,
            enabled = enabled,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            visualTransformation = ChipNumberFormatTransformation,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        errorText?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = it,
                textAlign = TextAlign.End,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

internal object ChipNumberFormatTransformation : VisualTransformation {
    private const val CHIP_NUMBER_FORMAT = "xxx xxx xxx"

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= 9) {
                text.text.substring(0..8)
            } else {
                text.text
            }
        val annotatedString = buildAnnotatedString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 2 || i == 5) {
                    append(" ")
                }
            }
            CHIP_NUMBER_FORMAT.takeLast(CHIP_NUMBER_FORMAT.length - length)
            toAnnotatedString()
        }
        return TransformedText(
            text = annotatedString,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return when {
                        offset <= 2 -> offset
                        offset <= 5 -> offset + 1
                        offset <= 9 -> offset + 2
                        else -> 11
                    }
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return when {
                        offset <= 2 -> offset
                        offset <= 5 -> offset - 1
                        offset <= 9 -> offset - 2
                        else -> 9
                    }
                }
            }
        )
    }
}

@AnimealPreview
@Composable
private fun ChipNumberInputPreview() {
    AnimealTheme {
        Column {
            ChipNumberInput(
                modifier = Modifier.padding(horizontal = 4.dp),
                title = "Chip number",
                onValueChange = {},
                value = "123456789",
                errorText = "Pet with this chip number already exists"
            )
            Divider()
            ChipNumberInput(
                modifier = Modifier.padding(horizontal = 4.dp),
                title = "Chip number",
                onValueChange = {},
                value = "123456789"
            )
        }
    }
}