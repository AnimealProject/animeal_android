package com.epmedu.animeal.login.profile.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

internal sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText

    @Suppress("SpreadOperator")
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }
}