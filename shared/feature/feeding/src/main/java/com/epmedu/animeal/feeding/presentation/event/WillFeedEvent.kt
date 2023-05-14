package com.epmedu.animeal.feeding.presentation.event

interface WillFeedEvent {
    object ShowWillFeedDialog : WillFeedEvent
    object DismissWillFeedDialog : WillFeedEvent
    object OpenGpsSettings : WillFeedEvent
    object DeclineUseGps : WillFeedEvent
    object EmbeddedDialogClosed : WillFeedEvent
}