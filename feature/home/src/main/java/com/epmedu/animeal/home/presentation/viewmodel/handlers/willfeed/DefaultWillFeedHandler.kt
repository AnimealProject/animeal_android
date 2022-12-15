package com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.WillFeedEvent.DismissWillFeedDialog
import com.epmedu.animeal.home.presentation.HomeScreenEvent.WillFeedEvent.ShowWillFeedDialog
import com.epmedu.animeal.home.presentation.model.WillFeedState.Dismissed
import com.epmedu.animeal.home.presentation.model.WillFeedState.Showing
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

class DefaultWillFeedHandler(
    stateDelegate: StateDelegate<HomeState>
) : WillFeedHandler, StateDelegate<HomeState> by stateDelegate {

    override fun handleWillFeedEvent(event: HomeScreenEvent.WillFeedEvent) {
        when (event) {
            DismissWillFeedDialog -> dismissWillFeedDialog()
            ShowWillFeedDialog -> showWillFeedDialog()
        }
    }

    private fun showWillFeedDialog() {
        updateState {
            copy(willFeedState = Showing)
        }
    }

    private fun dismissWillFeedDialog() {
        updateState {
            copy(willFeedState = Dismissed)
        }
    }
}