package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedStateUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByIdUseCase
import com.epmedu.animeal.feeding.domain.usecase.RejectFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateFeedStateUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Cancel
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Expired
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Finish
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Reset
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Start
import com.epmedu.animeal.feeding.presentation.mapper.toDomainFeedState
import com.epmedu.animeal.feeding.presentation.mapper.toPresentationFeedingConfirmationState
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState.Dismissed
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState.FeedingStarted
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState.FeedingWasAlreadyBooked
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private val rejectFeedingUseCase: RejectFeedingUseCase,
    private val finishFeedingUseCase: FinishFeedingUseCase,
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
                updateFeedingState(
                    feedingConfirmationState = Dismissed,
                    feedPoint = FeedingPointModel(feedingPoint)
                )
                showSingleReservedFeedingPoint(FeedingPointModel(feedingPoint))
                startRoute()
            } ?: run {
                updateFeedingState(
                    feedingConfirmationState = Dismissed
                )
            }
        }
        stateFlow.onEach {
            // updates state flow at repository
            updateFeedStateUseCase(it.toDomainFeedState())
        }.launchIn(this)
        launch {
            getFeedStateUseCase().collect { feedState ->
                updateFeedingState(
                    feedingConfirmationState = feedState.feedingConfirmationState.toPresentationFeedingConfirmationState(),
                    feedPoint = feedState.feedPoint?.let { FeedingPointModel(it) },
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
            Reset -> restartFeedingConfirmationState()
        }
    }

    private fun restartFeedingConfirmationState() {
        updateFeedingState(
            feedingConfirmationState = Dismissed
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
        /** stopRoute() is outside of onSuccess to immediately remove timer bar on TimerExpired Dialog */
        stopRoute()
        launch {
            performFeedingAction(
                action = { feedingPointId ->
                    rejectFeedingUseCase(feedingPointId, FEEDING_TIMER_EXPIRED)
                },
                onSuccess = {
                    deselectFeedingPoint()
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
                    fetchFeedingPoints()
                },
                onError = {
                    dismissThankYouDialog()
                }
            )
        }
    }

    private fun displayThankYouDialog() {
        updateFeedingState(FeedingConfirmationState.Showing)
    }

    override fun dismissThankYouDialog() {
        updateFeedingState(Dismissed)
    }

    private suspend fun performFeedingAction(
        action: suspend (String) -> ActionResult<Unit>,
        onSuccess: suspend (FeedingPointModel) -> Unit,
        onError: () -> Unit = { showError() },
    ) {
        state.feedPoint?.let { currentFeedingPoint ->
            performAction(
                action = { action(currentFeedingPoint.id) },
                onSuccess = { onSuccess(currentFeedingPoint) },
                onError = onError,
            )
        } ?: run {
            onError()
            showError()
        }
    }

    private fun updateFeedingState(
        feedingConfirmationState: FeedingConfirmationState,
        feedPoint: FeedingPointModel? = null
    ) {
        updateState {
            copy(
                feedPoint = feedPoint,
                feedingConfirmationState = feedingConfirmationState
            )
        }
    }

    companion object {
        private const val FEEDING_TIMER_EXPIRED = "Feeding time has expired"
    }
}