package com.epmedu.animeal.feeding.presentation.viewmodel.handler

import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent

interface WillFeedHandler {
    fun handleWillFeedEvent(event: WillFeedEvent)
}