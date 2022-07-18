package com.epmedu.animeal.foundation.input

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Suppress("LongParameterList")
@Composable
fun BirthDateInput(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onIconClick: () -> Unit,
    errorText: String? = null,
    isEnabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    val borderColor: Color = if (errorText == null) CustomColor.LynxWhite else CustomColor.Error
    Column(modifier = modifier.fillMaxWidth()) {
        BirthDateInputTitle(title = title)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
                .background(
                    color = CustomColor.LynxWhite,
                    shape = RoundedCornerShape(12.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.align(Alignment.CenterVertically),
                value = value,
                onValueChange = {
                    if (it.length <= 8) {
                        onValueChange(it)
                    }
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onSurface,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.profile_birthdate_placeholder),
                        fontSize = 16.sp,
                        color = CustomColor.DarkerGrey
                    )
                },
                visualTransformation = BirthDateFormatTransformation,
                enabled = isEnabled,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
            )
            IconButton(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp),
                onClick = { if (isEnabled) onIconClick() },
                enabled = isEnabled
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_birthdate),
                    contentDescription = null,
                    tint = CustomColor.HitGrey,
                )
            }
        }
        errorText?.let { BirthDateInputErrorText(it) }
    }
}

@Composable
private fun BirthDateInputTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(bottom = 2.dp),
        style = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
private fun BirthDateInputErrorText(errorText: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        text = errorText,
        textAlign = TextAlign.End,
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption
    )
}
private const val BIRTH_DATE_FORMAT = "xx.xx.xxxx"

object BirthDateFormatTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= 8) {
                text.text.substring(0..7)
            } else {
                text.text
            }
        val annotatedString = buildAnnotatedString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 1 || i == 3) {
                    append(".")
                }
            }
            BIRTH_DATE_FORMAT.takeLast(BIRTH_DATE_FORMAT.length - length)
            toAnnotatedString()
        }
        @Suppress("ReturnCount")
        return TransformedText(
            text = annotatedString,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 1) return offset
                    if (offset <= 3) return offset + 1
                    if (offset <= 8) return offset + 2
                    return 10
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 1) return offset
                    if (offset <= 3) return offset - 1
                    if (offset <= 8) return offset - 2
                    return 8
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun BirthDateInputPreview() {
    AnimealTheme {
        Surface {
            BirthDateInput(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "BirthDate",
                isEnabled = true,
                onIconClick = {},
                value = "",
                onValueChange = {},
            )
        }
    }
}