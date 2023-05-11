package com.epmedu.animeal.feeding.presentation.viewmodel.handler.willfeed

import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import kotlinx.coroutines.flow.StateFlow

interface WillFeedHandler {

    var willFeedStateFlow: StateFlow<WillFeedState>

    fun handleWillFeedEvent(event: WillFeedEvent)

    fun showWillFeedDialog()

    fun dismissWillFeedDialog()
}