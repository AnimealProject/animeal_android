package com.epmedu.animeal.tabs
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import com.epmedu.animeal.base.theme.CursorColor
import com.epmedu.animeal.base.theme.DarkerGrey
import com.epmedu.animeal.base.theme.LynxWhite
import com.epmedu.animeal.base.theme.NavigationItemColor

@Composable
fun TextInputField(
    modifier: Modifier,
    title: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    value: String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Center,
    ) {

        Text(
            text = title,
            modifier = Modifier
                .padding(bottom = 2.dp),
            style = TextStyle(
                color = NavigationItemColor,
                fontSize = 14.sp
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = LynxWhite,
                    shape = RoundedCornerShape(12.dp),
                ),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = NavigationItemColor,
                backgroundColor = Color.Transparent,
                cursorColor = CursorColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = DarkerGrey
                )
            },
            singleLine = true,
        )
    }
}

@Composable
fun FormattedInputView(
    modifier: Modifier,
    titleAndMaskFormat: Pair<String, String>,
    onValueChange: (String) -> Unit,
    value: String,
    prefixText: (@Composable () -> Unit)? = null,
) {
    val maxDigitCount = titleAndMaskFormat.second.replace("\\D".toRegex(), "").length

    Column(
        modifier = modifier,
    ) {
        Text(
            text = titleAndMaskFormat.first,
            modifier = Modifier.padding(bottom = 2.dp),
            style = TextStyle(
                color = NavigationItemColor,
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
            prefixText?.let { prefixText() }
            BasicTextField(
                value = value,
                onValueChange = {
                    if (it.length <= maxDigitCount) {
                        onValueChange(it)
                    }
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                visualTransformation = FormattedTransformation(titleAndMaskFormat.second),
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

@Preview(
    showBackground = true,
    name = "TextInputField",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
fun TextInputPreview() {
    AnimealTheme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            var name by remember { mutableStateOf("") }

            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = "Name",
                placeholder = "Enter your name",
                onValueChange = {
                    name = it
                },
                name
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "MaskInputField",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
fun PhoneInputPreview() {
    AnimealTheme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                var phoneNumber by remember { mutableStateOf("") }
                /**
                 *  PHONE NUMBER INPUT
                 */
                FormattedInputView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    titleAndMaskFormat = "Phone number" to "111 11-11-11",
                    onValueChange = { phoneNumber = it },
                    value = phoneNumber,
                    prefixText = {
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
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                var chipNumber by remember { mutableStateOf("") }
                /**
                 *  CHIP NUMBER INPUT
                 */
                FormattedInputView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    titleAndMaskFormat = "Chip number" to "111 111 111",
                    onValueChange = { chipNumber = it },
                    value = chipNumber
                )
            }
        }
    }
}
