package com.epmedu.animeal.tabs.more.profile

import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent

internal sealed interface ProfileScreenEvent {
    data class InputFormEvent(val event: ProfileInputFormEvent) : ProfileScreenEvent
    object Edit : ProfileScreenEvent
    object Discard : ProfileScreenEvent
    object Save : ProfileScreenEvent
}