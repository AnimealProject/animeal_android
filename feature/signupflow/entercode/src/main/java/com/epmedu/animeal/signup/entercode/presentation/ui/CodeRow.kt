package com.epmedu.animeal.signup.entercode.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.modifier.focusOnGloballyPositioned
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CodeRow(
    code: ImmutableList<Int?>,
    focusRequester: FocusRequester,
    onNumberChange: (position: Int, number: String?) -> Unit,
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
                onNumberInput = { number ->
                    onNumberChange(index, number)
                    if (index < code.lastIndex && number.length == 1) {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                },
                onDigitRemove = {
                    onNumberChange(index, null)
                    if (index > 0) focusManager.moveFocus(FocusDirection.Previous)
                },
                modifier = if (index == 0) {
                    Modifier.focusOnGloballyPositioned(focusRequester)
                } else {
                    Modifier
                },
                isError = isError
            )
        }
    }
}

@AnimealPreview
@Composable
private fun CodeRowPreview() {
    AnimealTheme {
        Column {
            CodeRow(
                code = persistentListOf(1, 2, null, null, null, null),
                focusRequester = FocusRequester(),
                onNumberChange = { _, _ -> },
                modifier = Modifier.padding(8.dp)
            )
            Divider()
            CodeRow(
                code = persistentListOf(1, 2, 3, 9, 5, 7),
                focusRequester = FocusRequester(),
                onNumberChange = { _, _ -> },
                modifier = Modifier.padding(8.dp),
                isError = true
            )
        }
    }
}