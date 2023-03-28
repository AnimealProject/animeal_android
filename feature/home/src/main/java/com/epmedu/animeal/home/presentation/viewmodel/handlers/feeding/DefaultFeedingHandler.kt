package com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.domain.usecases.CancelFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.FinishFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.StartFeedingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Cancel
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Expired
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Finish
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Start
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.ErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timer.TimerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
internal class DefaultFeedingHandler @Inject constructor(
    stateDelegate: StateDelegate<HomeState>,
    actionDelegate: ActionDelegate,
    routeHandler: RouteHandler,
    errorHandler: ErrorHandler,
    feedingPointHandler: FeedingPointHandler,
    timerHandler: TimerHandler,
    private val startFeedingUseCase: StartFeedingUseCase,
    private val cancelFeedingUseCase: CancelFeedingUseCase,
    private val finishFeedingUseCase: FinishFeedingUseCase
) : FeedingHandler,
    StateDelegate<HomeState> by stateDelegate,
    ActionDelegate by actionDelegate,
    FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    TimerHandler by timerHandler,
    ErrorHandler by errorHandler {

    override fun CoroutineScope.handleFeedingEvent(event: FeedingEvent) {
        when (event) {
            Start -> launch { startFeeding() }
            Cancel -> launch { cancelFeeding() }
            Expired -> launch { expireFeeding() }
            Finish -> launch { finishFeeding() }
        }
    }

    private suspend fun startFeeding() {
        performFeedingAction(
            action = startFeedingUseCase::invoke,
            onSuccess = { currentFeedingPoint ->
                showSingleReservedFeedingPoint(currentFeedingPoint)
                startRoute()
                startTimer()
            }
        )
    }

    override suspend fun cancelFeeding() {
        performFeedingAction(
            action = cancelFeedingUseCase::invoke,
            onSuccess = {
                stopRoute()
                disableTimer()
                fetchFeedingPoints()
            }
        )
    }

    override suspend fun expireFeeding() {
        /** stopRoute() is outside of onSuccess to immediately remove timer bar on TimerExpired Dialog */
        stopRoute()
        performFeedingAction(
            action = cancelFeedingUseCase::invoke,
            onSuccess = {
                fetchFeedingPoints()
            }
        )
    }

    private suspend fun finishFeeding() {
        performFeedingAction(
            action = { feedingPointId ->
                finishFeedingUseCase(feedingPointId, listOf(""))
            },
            onSuccess = {
                stopRoute()
                fetchFeedingPoints()
            }
        )
    }

    private suspend fun performFeedingAction(
        action: suspend (String) -> ActionResult,
        onSuccess: suspend (FeedingPointModel) -> Unit
    ) {
        state.currentFeedingPoint?.let { currentFeedingPoint ->
            performAction(
                action = { action(currentFeedingPoint.id) },
                onSuccess = { onSuccess(currentFeedingPoint) },
                onError = ::showError
            )
        } ?: showError()
    }
}