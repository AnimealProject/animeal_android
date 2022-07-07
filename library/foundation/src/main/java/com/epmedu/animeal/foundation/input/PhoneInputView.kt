package com.epmedu.animeal.foundation.input

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.CustomColor.LynxWhite

@Composable
fun PhoneNumberInputView(
    modifier: Modifier = Modifier,
    title: String,
    onValueChange: (String) -> Unit,
    value: String,
    enabled: Boolean
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 2.dp),
            style = TextStyle(
                color = MaterialTheme.colors.onSurface,
                fontSize = 14.sp
            )
        )
        Row(
            modifier = Modifier
                .height(56.dp)
                .background(
                    color = LynxWhite,
                    shape = RoundedCornerShape(12.dp),
                ),
            horizontalArrangement = Center,
        ) {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .padding(start = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "+995",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
            }
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .align(CenterVertically),
                value = value,
                onValueChange = {
                    if (it.length <= 9) {
                        onValueChange(it)
                    }
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                visualTransformation = PhoneFormatTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = enabled
            )
        }
    }
}

internal object PhoneFormatTransformation : VisualTransformation {
    private const val PHONE_NUMBER_FORMAT = "xxx xx-xx-xx"

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= 9) text.text.substring(0..8) else text.text
        val annotatedString = AnnotatedString.Builder().run {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 2) {
                    append(" ")
                } else if (i == 4 || i == 6) {
                    append("-")
                }
            }
            PHONE_NUMBER_FORMAT.takeLast(PHONE_NUMBER_FORMAT.length - length)
            toAnnotatedString()
        }
        @Suppress("ReturnCount")
        return TransformedText(
            text = annotatedString,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 4) return offset + 1
                    if (offset <= 6) return offset + 2
                    if (offset <= 9) return offset + 3
                    return 12
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 4) return offset - 1
                    if (offset <= 8) return offset - 2
                    if (offset <= 10) return offset - 3
                    return 9
                }
            }
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PhoneInputPreview() {
    AnimealTheme {
        Surface {
            var phoneNumber by remember { mutableStateOf("") }
            PhoneNumberInputView(
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                title = "Phone number",
                onValueChange = { phoneNumber = it },
                value = phoneNumber,
                enabled = true
            )
        }
    }
}
