package com.epmedu.animeal.debugmenu.presentation

sealed interface DebugMenuScreenEvent {

    data class SwitchUsingMockedFeedingPoints(
        val useMockedFeedingPoint: Boolean
    ) : DebugMenuScreenEvent

    object SetFinishProfileAsStartDestination : DebugMenuScreenEvent
    object ResetGeolocationPermissionRequestedAgain : DebugMenuScreenEvent
}