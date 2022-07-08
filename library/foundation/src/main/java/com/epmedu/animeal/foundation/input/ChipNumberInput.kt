package com.epmedu.animeal.foundation.input

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.CustomColor

@Composable
fun ChipNumberInput(
    modifier: Modifier = Modifier,
    title: String,
    onValueChange: (String) -> Unit,
    value: String,
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
            if (text.text.length >= 9) text.text.substring(0..8) else text.text
        val annotatedString = AnnotatedString.Builder().run {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 2 || i == 5) {
                    append(" ")
                }
            }
            CHIP_NUMBER_FORMAT.takeLast(CHIP_NUMBER_FORMAT.length - length)
            toAnnotatedString()
        }
        @Suppress("ReturnCount")
        return TransformedText(
            text = annotatedString,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset + 1
                    if (offset <= 9) return offset + 2
                    return 11
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset - 1
                    if (offset <= 9) return offset - 2
                    return 9
                }
            }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ChipNumberInputPreview() {
    AnimealTheme {
        Surface {
            Column {
                var chipNumber by remember { mutableStateOf("") }
                ChipNumberInput(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    title = "Chip number",
                    onValueChange = { chipNumber = it },
                    value = chipNumber,
                    errorText = "Pet with this chip number already exists"
                )
                ChipNumberInput(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    title = "Chip number",
                    onValueChange = { chipNumber = it },
                    value = chipNumber
                )
            }
        }
    }
}