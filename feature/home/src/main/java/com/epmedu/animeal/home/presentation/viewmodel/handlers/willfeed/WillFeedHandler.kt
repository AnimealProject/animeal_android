package com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed

import com.epmedu.animeal.home.presentation.HomeScreenEvent

interface WillFeedHandler {
    fun handleWillFeedEvent(event: HomeScreenEvent.WillFeedEvent)
}