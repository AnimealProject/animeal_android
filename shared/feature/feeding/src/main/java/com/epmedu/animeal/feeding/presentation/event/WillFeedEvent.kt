package com.epmedu.animeal.feeding.presentation.event

interface WillFeedEvent {
    object ShowWillFeedDialog : WillFeedEvent
    object AskCameraPermission : WillFeedEvent
    object DismissWillFeedDialog : WillFeedEvent
}