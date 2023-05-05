package com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding

import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.domain.usecases.CancelFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.home.domain.usecases.FinishFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.ForcedArgumentsUseCase
import com.epmedu.animeal.home.domain.usecases.RejectFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.StartFeedingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Cancel
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Expired
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Finish
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Start
import com.epmedu.animeal.home.presentation.model.FeedingConfirmationState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.ErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timer.TimerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
internal class DefaultFeedingHandler(
    stateDelegate: StateDelegate<HomeState>,
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
    StateDelegate<HomeState> by stateDelegate,
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
        updateState { copy(feedingConfirmationState = FeedingConfirmationState.Loading) }
        performFeedingAction(
            action = { feedingPointId ->
                finishFeedingUseCase(feedingPointId, state.feedingPhotos.map { it.name })
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