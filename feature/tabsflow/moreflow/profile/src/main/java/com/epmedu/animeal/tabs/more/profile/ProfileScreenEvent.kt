package com.epmedu.animeal.tabs.more.profile

import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent

internal sealed interface ProfileScreenEvent {
    data class InputFormEvent(val event: ProfileInputFormEvent) : ProfileScreenEvent
    data object Edit : ProfileScreenEvent
    data object Discard : ProfileScreenEvent
    data object Save : ProfileScreenEvent
}