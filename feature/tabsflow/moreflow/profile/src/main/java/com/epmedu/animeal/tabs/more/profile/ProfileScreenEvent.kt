package com.epmedu.animeal.tabs.more.profile

internal sealed interface ProfileScreenEvent {
    object Edit : ProfileScreenEvent
    object Discard : ProfileScreenEvent
    object Save : ProfileScreenEvent
}