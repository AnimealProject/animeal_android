package com.epmedu.animeal.foundation.input

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.input.PhoneFormatTransformation.PHONE_NUMBER_FORMAT
import com.epmedu.animeal.foundation.input.PhoneFormatTransformation.PHONE_NUMBER_LENGTH
import com.epmedu.animeal.foundation.input.PhoneFormatTransformation.PHONE_NUMBER_PREFIX
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    title: String,
    onValueChange: (String) -> Unit = {},
    value: String,
    isEnabled: Boolean = true
) {
    TextInputField(
        modifier = modifier,
        isEnabled = isEnabled,
        title = title,
        hint = PHONE_NUMBER_FORMAT,
        onValueChange = {
            if (it.length <= PHONE_NUMBER_LENGTH) {
                onValueChange(it)
            }
        },
        value = value,
        visualTransformation = PhoneFormatTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        leadingIcon = {
            Row(
                modifier = Modifier.padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(R.drawable.ic_georgia),
                    contentDescription = null
                )
                Text(
                    text = PHONE_NUMBER_PREFIX,
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 16.sp,
                    ),
                )
            }
        }
    )
}

object PhoneFormatTransformation : VisualTransformation {
    internal const val PHONE_NUMBER_LENGTH = 9
    internal const val PHONE_NUMBER_PREFIX = "+995"
    internal const val PHONE_NUMBER_FORMAT = "xxx xx-xx-xx"

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= PHONE_NUMBER_LENGTH) {
                text.text.substring(0..8)
            } else {
                text.text
            }
        val annotatedString = buildAnnotatedString {
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

@Composable
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun PhoneNumberInputPreview() {
    AnimealTheme {
        var phoneNumber by remember { mutableStateOf("") }
        PhoneNumberInput(
            modifier = Modifier
                .padding(horizontal = 4.dp),
            title = "Phone number",
            onValueChange = { phoneNumber = it },
            value = phoneNumber,
            isEnabled = true
        )
    }
}
