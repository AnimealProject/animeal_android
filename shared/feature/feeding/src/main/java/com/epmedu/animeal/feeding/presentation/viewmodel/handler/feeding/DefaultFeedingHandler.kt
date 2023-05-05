package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding

import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.RejectFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("LongParameterList", "TooManyFunctions")
class DefaultFeedingHandler(
    stateDelegate: StateDelegate<FeedingPointState>,
    actionDelegate: ActionDelegate,
    routeHandler: RouteHandler,
    errorHandler: ErrorHandler,
    feedingPointHandler: FeedingPointHandler,
    timerHandler: TimerHandler,
    private val fetchCurrentFeedingPointUseCase: FetchCurrentFeedingPointUseCase,
    private val startFeedingUseCase: StartFeedingUseCase,
    private val cancelFeedingUseCase: CancelFeedingUseCase,
    private val rejectFeedingUseCase: RejectFeedingUseCase,
    private val finishFeedingUseCase: FinishFeedingUseCase,
    private val forcedArgumentsUseCase: ForcedArgumentsUseCase
) : FeedingHandler,
    StateDelegate<FeedingPointState> by stateDelegate,
    ActionDelegate by actionDelegate,
    FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    TimerHandler by timerHandler,
    ErrorHandler by errorHandler {

    override suspend fun fetchCurrentFeeding() {
        val forcedFeedingPointId = forcedArgumentsUseCase<String>(FORCED_FEEDING_POINT_ID, hashCode())
        if (forcedFeedingPointId != null) return

        fetchCurrentFeedingPointUseCase()?.let { feedingPoint ->
            showSingleReservedFeedingPoint(FeedingPointModel(feedingPoint))
            startRoute()
        }
    }

    override fun CoroutineScope.handleFeedingEvent(event: FeedingEvent) {
        when (event) {
            FeedingEvent.Start -> launch { startFeeding() }
            FeedingEvent.Cancel -> launch { cancelFeeding() }
            FeedingEvent.Expired -> launch { expireFeeding() }
            FeedingEvent.Finish -> launch { finishFeeding() }
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
                deselectFeedingPoint()
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
            action = { feedingPointId ->
                rejectFeedingUseCase(feedingPointId, FEEDING_TIMER_EXPIRED)
            },
            onSuccess = {
                deselectFeedingPoint()
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
                deselectFeedingPoint()
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

    companion object {
        private const val FEEDING_TIMER_EXPIRED = "Feeding time has expired"
    }
}