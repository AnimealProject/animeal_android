package com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.CancellationRequestState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.timer.data.model.TimerState
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class DefaultTimerCancellationHandler(
    stateDelegate: StateDelegate<HomeState>,
    feedingPointHandler: FeedingPointHandler,
    timerHandler: TimerHandler,
    feedingHandler: FeedingHandler,
) : TimerCancellationHandler,
    FeedingPointHandler by feedingPointHandler,
    FeedingHandler by feedingHandler,
    TimerHandler by timerHandler,
    StateDelegate<HomeState> by stateDelegate {

    override fun CoroutineScope.handleTimerCancellationEvent(event: HomeScreenEvent.TimerCancellationEvent) {
        when (event) {
            HomeScreenEvent.TimerCancellationEvent.CancellationAttempt -> showCancellationRequest()
            HomeScreenEvent.TimerCancellationEvent.CancellationAccepted -> launch {
                dismissCancellation()
                when (state.timerState) {
                    is TimerState.Active -> cancelFeeding()
                    is TimerState.Expired -> {
                        fetchFeedingPoints()
                        disableTimer()
                    }
                    else -> Unit
                }
            }
            HomeScreenEvent.TimerCancellationEvent.CancellationDismissed -> dismissCancellation()
        }
    }

    private fun showCancellationRequest() {
        updateState { copy(cancellationRequestState = CancellationRequestState.Showing) }
    }

    private fun dismissCancellation() {
        updateState { copy(cancellationRequestState = CancellationRequestState.Dismissed) }
    }
}