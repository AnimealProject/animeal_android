package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.model.FeedingConfirmationState
import com.epmedu.animeal.feeding.domain.model.FeedingConfirmationState.Dismissed
import com.epmedu.animeal.feeding.domain.model.FeedingConfirmationState.FeedingStarted
import com.epmedu.animeal.feeding.domain.model.FeedingConfirmationState.FeedingWasAlreadyBooked
import com.epmedu.animeal.feeding.domain.model.FeedingConfirmationState.Showing
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.ExpireFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedStateUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByIdUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateFeedStateUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Cancel
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Expired
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Finish
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Reset
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Start
import com.epmedu.animeal.feeding.presentation.mapper.toDomainFeedState
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.networkuser.domain.usecase.GetIsTrustedUseCase
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
class DefaultFeedingHandler(
    stateDelegate: StateDelegate<FeedState>,
    actionDelegate: ActionDelegate,
    routeHandler: RouteHandler,
    errorHandler: ErrorHandler,
    feedingPointHandler: FeedingPointHandler,
    timerHandler: TimerHandler,
    private val fetchCurrentFeedingPointUseCase: FetchCurrentFeedingPointUseCase,
    private val getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase,
    private val getFeedStateUseCase: GetFeedStateUseCase,
    private val updateFeedStateUseCase: UpdateFeedStateUseCase,
    private val startFeedingUseCase: StartFeedingUseCase,
    private val cancelFeedingUseCase: CancelFeedingUseCase,
    private val expireFeedingUseCase: ExpireFeedingUseCase,
    private val finishFeedingUseCase: FinishFeedingUseCase,
    private val getIsTrustedUseCase: GetIsTrustedUseCase
) : FeedingHandler,
    StateDelegate<FeedState> by stateDelegate,
    ActionDelegate by actionDelegate,
    FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    TimerHandler by timerHandler,
    ErrorHandler by errorHandler {

    override var feedingStateFlow: StateFlow<FeedState> = stateFlow

    private var fetchCurrentFeedingJob: Job? = null

    override fun CoroutineScope.fetchCurrentFeeding() {
        fetchCurrentFeedingJob?.cancel()
        fetchCurrentFeedingJob = launch {
            fetchCurrentFeedingPointUseCase()?.let { feedingPoint ->
                if (feedingRouteStateFlow.value is FeedingRouteState.Disabled) {
                    updateFeedingState(
                        feedingConfirmationState = Dismissed,
                        feedPoint = FeedingPointModel(feedingPoint)
                    )
                    showSingleReservedFeedingPoint(FeedingPointModel(feedingPoint))
                    startRoute()
                }
            } ?: run {
                updateFeedingState(
                    feedingConfirmationState = Dismissed
                )
            }
        }
        launch {
            getFeedStateUseCase().collect { feedState ->
                if (feedingRouteStateFlow.value is FeedingRouteState.Disabled && feedState.feedPoint != null) {
                    showSingleReservedFeedingPoint(FeedingPointModel(feedState.feedPoint))
                    startRoute()
                }
                updateFeedingState(
                    feedingConfirmationState = feedState.feedingConfirmationState,
                    feedPoint = feedState.feedPoint?.let { FeedingPointModel(it) },
                    updateGlobally = false
                )
            }
        }
    }

    override fun CoroutineScope.handleFeedingEvent(event: FeedingEvent) {
        when (event) {
            is Start -> launch { startFeeding(event.id) }
            Cancel -> cancelFeeding()
            Expired -> expireFeeding()
            is Finish -> finishFeeding(event.feedingPhotos)
            Reset -> launch { restartFeedingConfirmationState() }
        }
    }

    private suspend fun restartFeedingConfirmationState() {
        updateFeedingState(
            feedingConfirmationState = Dismissed,
            updateGlobally = false
        )
    }

    private suspend fun startFeeding(id: String) {
        getFeedingPointByIdUseCase(id)?.let { feedingPoint ->
            updateState {
                copy(
                    feedPoint = FeedingPointModel(feedingPoint)
                )
            }
            performFeedingAction(
                action = startFeedingUseCase::invoke,
                onSuccess = { currentFeedingPoint ->
                    showSingleReservedFeedingPoint(currentFeedingPoint)
                    startRoute()
                    startTimer()
                    updateFeedingState(
                        feedPoint = FeedingPointModel(feedingPoint),
                        feedingConfirmationState = FeedingStarted
                    )
                },
                onError = {
                    updateFeedingState(FeedingWasAlreadyBooked)
                }
            )
        }
    }

    override fun CoroutineScope.cancelFeeding() {
        launch {
            performFeedingAction(
                action = cancelFeedingUseCase::invoke,
                onSuccess = {
                    deselectFeedingPoint()
                    stopRoute()
                    disableTimer()
                    fetchFeedingPoints()
                    updateFeedingState(Dismissed)
                }
            )
        }
    }

    override fun CoroutineScope.expireFeeding() {
        launch {
            performFeedingAction(
                onStart = {
                    /** stopRoute() is outside of onSuccess to immediately remove timer bar on TimerExpired Dialog */
                    stopRoute()
                    deselectFeedingPoint()
                },
                action = { feedingPointId ->
                    expireFeedingUseCase(feedingPointId)
                },
                onFinish = {
                    // if the feeding is expired by backend, expireFeedingUseCase will return an error,
                    // so we need to fetch feeding points no matter what the result is
                    fetchFeedingPoints()
                    updateFeedingState(Dismissed)
                }
            )
        }
    }

    private fun CoroutineScope.finishFeeding(feedingPhotos: List<FeedingPhotoItem>) {
        updateState { copy(feedingConfirmationState = FeedingConfirmationState.Loading) }
        launch {
            performFeedingAction(
                action = { feedingPointId ->
                    finishFeedingUseCase(feedingPointId, feedingPhotos.map { it.name })
                },
                onSuccess = {
                    displayThankYouDialog()
                    stopRoute()
                    disableTimer()
                    fetchFeedingPoints()
                },
                onError = {
                    dismissThankYouDialog()
                }
            )
        }
    }

    private suspend fun displayThankYouDialog() {
        val isUserTrustedResult = getIsTrustedUseCase()
        val isUserTrusted = isUserTrustedResult is ActionResult.Success && isUserTrustedResult.result

        updateFeedingState(Showing(isAutoApproved = isUserTrusted))
    }

    override suspend fun dismissThankYouDialog() {
        updateFeedingState(Dismissed)
    }

    private suspend fun performFeedingAction(
        action: suspend (String) -> ActionResult<Unit>,
        onSuccess: suspend (FeedingPointModel) -> Unit = {},
        onError: suspend () -> Unit = { showError() },
        onStart: suspend (String) -> Unit = {},
        onFinish: suspend () -> Unit = {},
    ) {
        state.feedPoint?.let { currentFeedingPoint ->
            performAction(
                action = { action(currentFeedingPoint.id) },
                onStart = { onStart(currentFeedingPoint.id) },
                onSuccess = { onSuccess(currentFeedingPoint) },
                onError = onError,
                onFinish = onFinish
            )
        } ?: run {
            onError()
            showError()
        }
    }

    private suspend fun updateFeedingState(
        feedingConfirmationState: FeedingConfirmationState,
        feedPoint: FeedingPointModel? = null,
        updateGlobally: Boolean = true
    ) {
        updateState {
            copy(
                feedPoint = feedPoint,
                feedingConfirmationState = feedingConfirmationState
            )
        }
        if (updateGlobally) updateFeedStateUseCase(state.toDomainFeedState())
    }
}