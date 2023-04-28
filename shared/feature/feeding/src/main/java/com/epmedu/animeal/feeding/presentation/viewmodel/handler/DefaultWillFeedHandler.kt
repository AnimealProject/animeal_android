package com.epmedu.animeal.feeding.presentation.viewmodel.handler

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DefaultWillFeedHandler(
    stateDelegate: StateDelegate<WillFeedState>,
) : WillFeedHandler, StateDelegate<WillFeedState> by stateDelegate {
    override var willFeedStateFlow: StateFlow<WillFeedState> = stateFlow

    override fun CoroutineScope.registerWillFeedState(updateCall: (WillFeedState) -> Unit) {
        launch {
            willFeedStateFlow.collectLatest { updateCall(it) }
        }
    }

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