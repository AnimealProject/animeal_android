package com.epmedu.animeal.feeding.presentation.viewmodel.handler.willfeed

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import kotlinx.coroutines.flow.StateFlow

class DefaultWillFeedHandler(
    stateDelegate: StateDelegate<WillFeedState>,
) : WillFeedHandler, StateDelegate<WillFeedState> by stateDelegate {
    override var willFeedStateFlow: StateFlow<WillFeedState> = stateFlow

    override fun handleWillFeedEvent(event: WillFeedEvent) {
        when (event) {
            WillFeedEvent.DismissWillFeedDialog -> dismissWillFeedDialog()
            WillFeedEvent.ShowWillFeedDialog -> showWillFeedDialog()
        }
    }

    override fun showWillFeedDialog() {
        updateState { WillFeedState.Showing }
    }

    override fun dismissWillFeedDialog() {
        updateState { WillFeedState.Dismissed }
    }
}