package com.epmedu.animeal.tabs

import android.content.res.Configuration.UI_MODE_NIGHT_NO
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.LynxWhite

@Composable
fun PhoneNumberInputView(
    modifier: Modifier,
    title: String,
    onValueChange: (String) -> Unit,
    value: String
) {
    val phoneFormat = "111 11-11-11"
    val maxDigitCount = phoneFormat.replace("\\D".toRegex(), "").length

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
            horizontalArrangement = Center,
            modifier = Modifier
                .height(56.dp)
                .background(
                    color = LynxWhite,
                    shape = RoundedCornerShape(12.dp),
                )
        ) {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .padding(start = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+995",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
            }
            BasicTextField(
                value = value,
                onValueChange = {
                    if (it.length <= maxDigitCount) {
                        onValueChange(it)
                    }
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                visualTransformation = FormattedTransformation(phoneFormat),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .align(CenterVertically)
            )
        }
    }
}

class FormattedTransformation(format: String) : VisualTransformation {
    val mask: String = format
    private val cleanDigitLength = mask.replace("\\D".toRegex(), "").length
    private val map: MutableMap<Int, Char> = mutableMapOf()

    init {
        for (a in mask.indices) {
            if (mask[a] != '1') {
                map[a - map.size - 1] = mask[a]
            }
        }
        map[cleanDigitLength] = ' '
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= cleanDigitLength) text.text.substring(0 until cleanDigitLength) else text.text
        val annotatedString = AnnotatedString.Builder().run {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (map.containsKey(i)) {
                    append(map[i].toString())
                }
            }
            mask.takeLast(mask.length - length)
            toAnnotatedString()
        }
        return TransformedText(
            text = annotatedString,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    var lastIndex = 0
                    while (true) {
                        when {
                            lastIndex == map.keys.size -> {
                                return mask.length
                            }
                            offset <= map.keys.toList()[lastIndex] -> {
                                return offset + lastIndex
                            }
                            else -> {
                                lastIndex += 1
                            }
                        }
                    }
                }

                override fun transformedToOriginal(offset: Int): Int {
                    var lastIndex = 0
                    while (true) {
                        when {
                            lastIndex == map.keys.size -> {
                                return mask.length
                            }
                            offset <= map.keys.toList()[lastIndex] -> {
                                return offset - lastIndex
                            }
                            else -> {
                                lastIndex += 1
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun PhoneInputPreview() {
    AnimealTheme {
        Surface {
            var phoneNumber by remember { mutableStateOf("") }

            PhoneNumberInputView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = "Phone number",
                onValueChange = { phoneNumber = it },
                value = phoneNumber
            )
        }
    }
}
