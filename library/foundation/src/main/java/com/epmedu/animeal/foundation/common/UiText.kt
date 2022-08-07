package com.epmedu.animeal.foundation.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class RawString(val value: String) : UiText
    class StringResource(@StringRes val resId: Int, vararg val args: Any) : UiText

    @Suppress("SpreadOperator")
    @Composable
    fun asString(): String {
        return when (this) {
            is RawString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }
}