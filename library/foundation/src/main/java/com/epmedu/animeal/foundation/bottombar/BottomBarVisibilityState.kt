package com.epmedu.animeal.foundation.bottombar

enum class BottomBarVisibilityState {
    SHOWN, HIDDEN;

    fun isShown() = this == SHOWN

    companion object {
        fun ofBoolean(shown: Boolean) = if (shown) SHOWN else HIDDEN
    }
}