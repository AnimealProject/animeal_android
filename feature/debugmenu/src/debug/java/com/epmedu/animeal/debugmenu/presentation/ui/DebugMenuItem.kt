package com.epmedu.animeal.debugmenu.presentation.ui

internal sealed interface DebugMenuItem {

    data object Divider : DebugMenuItem

    data class Switch(
        val title: String,
        val checkedInitially: Boolean,
        val onCheckedChange: (Boolean) -> Unit
    ) : DebugMenuItem

    data class Button(
        val title: String,
        val onClick: () -> Unit
    ) : DebugMenuItem
}