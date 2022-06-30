package com.epmedu.animeal.tabs

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.epmedu.animeal.base.theme.DarkerGrey
import com.epmedu.animeal.base.theme.LynxWhite
import com.epmedu.animeal.base.theme.NavigationItemColor

@Composable
fun TextInputField(
    modifier: Modifier,
    title: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    value: String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = title,
            modifier = modifier.padding(bottom = 2.dp),
            style = TextStyle(
                color = NavigationItemColor,
                fontSize = 14.sp
            )
        )

        TextField(
            modifier = modifier.background(
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
                cursorColor = Color.Black,
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
            keyboardOptions = keyboardOptions
        )
    }
}

@Composable
fun FormattedInputView(
    modifier: Modifier,
    title: String,
    maskFormat: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    value: String,
    prefixText: (@Composable () -> Unit)? = null,
) {
    val maxDigitCount = maskFormat.replace("\\D".toRegex(), "").length

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = modifier.padding(bottom = 2.dp),
            style = TextStyle(
                color = NavigationItemColor,
                fontSize = 14.sp
            )
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
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
                visualTransformation = FormattedTransformation(maskFormat),
                keyboardOptions = keyboardOptions,
                modifier = modifier
                    .align(CenterVertically)
            )
        }
    }
}

class FormattedTransformation(format: String) : VisualTransformation {
    val mask: String = format
    val cleanDigitLength = mask.replace("\\D".toRegex(), "").length
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
                    return cleanDigitLength
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(70.dp))

                var name by remember { mutableStateOf("") }
                /**
                 *  FIRST NUMBER INPUT
                 */
                TextInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    title = "Name",
                    placeholder = "Enter your name",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        name = it
                    },
                    name
                )

                Spacer(modifier = Modifier.height(20.dp))

                var phoneNumber by remember { mutableStateOf("") }
                /**
                 *  PHONE NUMBER INPUT
                 */
                FormattedInputView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    title = "Phone number",
                    maskFormat = "111 11-11-11",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = { phoneNumber = it },
                    phoneNumber,
                    prefixText = {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .padding(start = 16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "+995",
                                textAlign = TextAlign.Center,
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
                    title = "Chip number",
                    maskFormat = "111 111 111",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { chipNumber = it },
                    chipNumber
                )
            }
        }
    }
}
