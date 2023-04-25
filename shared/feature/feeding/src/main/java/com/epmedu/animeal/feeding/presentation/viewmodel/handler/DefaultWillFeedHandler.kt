package com.epmedu.animeal.feeding.presentation.viewmodel.handler

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState

class DefaultWillFeedHandler(
    stateDelegate: StateDelegate<WillFeedState>
) : WillFeedHandler, StateDelegate<WillFeedState> by stateDelegate {

    override fun handleWillFeedEvent(event: WillFeedEvent) {
        when (event) {
            WillFeedEvent.DismissWillFeedDialog -> dismissWillFeedDialog()
            WillFeedEvent.ShowWillFeedDialog -> showWillFeedDialog()
        }
    }

    private fun showWillFeedDialog() {
        updateState { WillFeedState.Showing }
    }

    private fun dismissWillFeedDialog() {
        updateState { WillFeedState.Dismissed }
    }
}