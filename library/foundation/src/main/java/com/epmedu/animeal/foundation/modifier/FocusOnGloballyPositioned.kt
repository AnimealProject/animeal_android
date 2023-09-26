package com.epmedu.animeal.foundation.modifier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned

fun Modifier.focusOnGloballyPositioned(
    focusRequester: FocusRequester,
    focusOnce: Boolean = true,
): Modifier = composed {
    var isInitiallyFocused by remember { mutableStateOf(false) }

    this
        .focusRequester(focusRequester)
        .onGloballyPositioned {
            when {
                focusOnce && isInitiallyFocused.not() -> {
                    focusRequester.requestFocus()
                    isInitiallyFocused = true
                }
                focusOnce.not() -> {
                    focusRequester.requestFocus()
                }
            }
        }
}