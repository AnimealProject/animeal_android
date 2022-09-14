package com.epmedu.animeal.foundation.bottombar

enum class BottomBarVisibilityState {
    SHOWN, HIDDEN;

    fun isShown() = this == SHOWN
}