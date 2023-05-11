package com.epmedu.animeal.feeding.presentation.viewmodel.handler

import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface WillFeedHandler {

    var willFeedStateFlow: StateFlow<WillFeedState>

    fun CoroutineScope.registerWillFeedState(updateCall: (WillFeedState) -> Unit)

    fun handleWillFeedEvent(event: WillFeedEvent)

    fun askCameraPermission()

    fun showWillFeedDialog()

    fun dismissWillFeedDialog()
}