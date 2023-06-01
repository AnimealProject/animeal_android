package com.epmedu.animeal.foundation.input

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.common.validation.Constants.GE_PHONE_NUMBER_LENGTH
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun PhoneNumberInput(
    value: String,
    prefix: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    error: String = "",
    isEnabled: Boolean = true,
    isFlagClickable: Boolean = false,
    flag: Flag = Flag(iconFlag = R.drawable.ic_georgia),
    useNumberFormatter: Boolean = true,
    format: String = GE_PHONE_NUMBER_FORMAT,
    numberLength: Int = GE_PHONE_NUMBER_LENGTH,
    onCountryClick: (() -> Unit)? = null,
    onDone: KeyboardActionScope.() -> Unit = {},
) {
    TextInputField(
        modifier = modifier,
        title = stringResource(id = R.string.phone_number),
        hint = format,
        onValueChange = {
            if (it.length <= numberLength) {
                onValueChange(it)
            }
        },
        value = value,
        isEnabled = isEnabled,
        errorText = error,
        leadingIcon = {
            Row(
                modifier = Modifier
                    .clickable(enabled = isFlagClickable) { onCountryClick?.invoke() }
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (flag.iconFlag != null) {
                    Image(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(flag.iconFlag),
                        contentDescription = null
                    )
                }
                Text(
                    text = if (flag.emojiFlag == null) prefix else "${flag.emojiFlag} $prefix",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraLight
                )
            }
        },
        visualTransformation = if (useNumberFormatter) GePhoneFormatTransformation else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        keyboardActions = KeyboardActions(onDone = onDone)
    )
}

internal const val GE_PHONE_NUMBER_FORMAT = "xxx xx-xx-xx"

object GePhoneFormatTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= GE_PHONE_NUMBER_LENGTH) {
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
            GE_PHONE_NUMBER_FORMAT.takeLast(GE_PHONE_NUMBER_FORMAT.length - length)
            toAnnotatedString()
        }
        return TransformedText(
            text = annotatedString,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return when {
                        offset <= 2 -> offset
                        offset <= 4 -> offset + 1
                        offset <= 6 -> offset + 2
                        offset <= 9 -> offset + 3
                        else -> 12
                    }
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return when {
                        offset <= 2 -> offset
                        offset <= 4 -> offset - 1
                        offset <= 8 -> offset - 2
                        offset <= 10 -> offset - 3
                        else -> 9
                    }
                }
            }
        )
    }
}

@AnimealPreview
@Composable
private fun PhoneNumberInputPreview() {
    AnimealTheme {
        Column {
            PhoneNumberInput(
                value = "",
                prefix = "+995",
                format = GE_PHONE_NUMBER_FORMAT,
                onCountryClick = {},
            )
            Divider()
            PhoneNumberInput(
                value = "123456789",
                prefix = "+995",
                format = GE_PHONE_NUMBER_FORMAT,
                isEnabled = false,
                onCountryClick = {}
            )
            Divider()
            PhoneNumberInput(
                value = "1234567",
                prefix = "+995",
                format = GE_PHONE_NUMBER_FORMAT,
                isEnabled = false,
                error = "Phone Number is too short",
                onCountryClick = {}
            )
        }
    }
}