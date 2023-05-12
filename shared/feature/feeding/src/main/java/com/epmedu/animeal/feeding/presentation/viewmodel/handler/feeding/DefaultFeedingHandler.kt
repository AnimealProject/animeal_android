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
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Cancel
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Expired
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Finish
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Start
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
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

    override var feedingStateFlow: StateFlow<FeedingPointState> = stateFlow

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
            Start -> launch { startFeeding() }
            Cancel -> launch { cancelFeeding() }
            Expired -> launch { expireFeeding() }
            is Finish -> launch { finishFeeding(event.feedingPhotos) }
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

    private suspend fun finishFeeding(feedingPhotos: List<FeedingPhotoItem>) {
        updateState { copy(feedingConfirmationState = FeedingConfirmationState.Loading) }
        performFeedingAction(
            action = { feedingPointId ->
                finishFeedingUseCase(feedingPointId, feedingPhotos.map { it.name })
            },
            onSuccess = {
                displayThankYouDialog()
                stopRoute()
                fetchFeedingPoints()
            },
            onError = {
                updateState { copy(feedingConfirmationState = FeedingConfirmationState.Dismissed) }
            }
        )
    }

    private fun displayThankYouDialog() {
        updateState { copy(feedingConfirmationState = FeedingConfirmationState.Showing) }
    }

    private suspend fun performFeedingAction(
        action: suspend (String) -> ActionResult,
        onSuccess: suspend (FeedingPointModel) -> Unit,
        onError: () -> Unit = {}
    ) {
        state.currentFeedingPoint?.let { currentFeedingPoint ->
            performAction(
                action = { action(currentFeedingPoint.id) },
                onSuccess = { onSuccess(currentFeedingPoint) },
                onError = ::showError
            )
        } ?: run {
            onError()
            showError()
        }
    }

    companion object {
        private const val FEEDING_TIMER_EXPIRED = "Feeding time has expired"
    }
}