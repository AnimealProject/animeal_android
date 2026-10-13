package com.epmedu.animeal.messages.presentation.viewmodel

import com.epmedu.animeal.foundation.common.UiText

data class MessageCreateDialogState(
    val body: String = "",
    val formState: FormState = FormState.Closed,
    val bodyError: UiText = UiText.Empty,
) {
    fun hasErrors() =
        listOf(
            bodyError,
        ).any { it !is UiText.Empty }

    enum class FormState {
        Opened,
        Confirmed,
        Closed
    }
}