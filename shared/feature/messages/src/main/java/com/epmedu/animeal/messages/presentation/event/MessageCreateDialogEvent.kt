package com.epmedu.animeal.messages.presentation.event

sealed interface MessageCreateDialogEvent {
    data class BodyChanged(val value: String) : MessageCreateDialogEvent
    data object BodyFocusCleared : MessageCreateDialogEvent

    data object Confirm : MessageCreateDialogEvent

    data object Open : MessageCreateDialogEvent
    data object Close : MessageCreateDialogEvent
}