package com.epmedu.animeal.feature.login.code.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme

@Composable
internal fun CodeRow(
    code: List<Int?>,
    focusRequester: FocusRequester,
    onDigitChange: (position: Int, digit: Int?) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (index in code.indices) {
            DigitField(
                digit = code[index],
                onDigitInput = { digit ->
                    onDigitChange(index, digit)
                    if (index == code.lastIndex) focusManager.clearFocus()
                    else focusManager.moveFocus(FocusDirection.Next)
                },
                onDigitRemove = {
                    onDigitChange(index, null)
                    if (index > 0) focusManager.moveFocus(FocusDirection.Previous)
                },
                modifier = if (index == 0) Modifier.focusRequester(focusRequester) else Modifier,
                isError = isError
            )
        }
    }
}

@Preview
@Composable
private fun CodeRowPreview() {
    AnimealTheme {
        Surface {
            Column {
                CodeRow(
                    code = listOf(1, 2, null, null),
                    focusRequester = FocusRequester(),
                    onDigitChange = { _, _ -> },
                    modifier = Modifier.padding(8.dp)
                )
                Divider()
                CodeRow(
                    code = listOf(1, 2, 3, 9),
                    focusRequester = FocusRequester(),
                    onDigitChange = { _, _ -> },
                    modifier = Modifier.padding(8.dp),
                    isError = true
                )
            }
        }
    }
}