package com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed

import com.epmedu.animeal.home.presentation.HomeScreenEvent

internal interface WillFeedHandler {
    fun handleWillFeedEvent(event: HomeScreenEvent.WillFeedEvent)
}