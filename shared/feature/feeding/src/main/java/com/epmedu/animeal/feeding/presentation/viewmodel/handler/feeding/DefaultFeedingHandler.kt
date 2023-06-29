package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByIdUseCase
import com.epmedu.animeal.feeding.domain.usecase.RejectFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateFeedStateUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.AgreeWillFeed
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Cancel
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.DismissWillFeed
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Expired
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Finish
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.Reset
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.WillFeedClicked
import com.epmedu.animeal.feeding.presentation.mapper.toDomainFeedState
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.CameraPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.ConfirmationRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.Dismissed
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GeolocationPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GpsSettingRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingState
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.permissions.presentation.PermissionStatus.Granted
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionAsked
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import kotlinx.coroutines.CoroutineScope
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
    permissionsHandler: PermissionsHandler,
    private val fetchCurrentFeedingPointUseCase: FetchCurrentFeedingPointUseCase,
    private val getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase,
    private var updateFeedStateUseCase: UpdateFeedStateUseCase,
    private val startFeedingUseCase: StartFeedingUseCase,
    private val cancelFeedingUseCase: CancelFeedingUseCase,
    private val rejectFeedingUseCase: RejectFeedingUseCase,
    private val finishFeedingUseCase: FinishFeedingUseCase,
    private val gpsSettingsProvider: GpsSettingsProvider
) : FeedingHandler,
    StateDelegate<FeedState> by stateDelegate,
    ActionDelegate by actionDelegate,
    PermissionsHandler by permissionsHandler,
    FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    TimerHandler by timerHandler,
    ErrorHandler by errorHandler {

    private var askedToEnableGps = false
    private var feedingPointId: String? = null

    override var feedingStateFlow: StateFlow<FeedState> = stateFlow
    override fun CoroutineScope.fetchCurrentFeeding() {
        launch {
            fetchCurrentFeedingPointUseCase()?.let { feedingPoint ->
                updateState {
                    copy(
                        feedPoint = FeedingPointModel(feedingPoint),
                        feedingConfirmationState = FeedingConfirmationState.Dismissed
                    )
                }
                showSingleReservedFeedingPoint(FeedingPointModel(feedingPoint))
                startRoute()
            } ?: run {
                updateState {
                    copy(
                        feedingConfirmationState = FeedingConfirmationState.Dismissed
                    )
                }
            }
        }
        stateFlow.onEach {
            // updates state flow at repository
            updateFeedStateUseCase(it.toDomainFeedState())
        }.launchIn(this)
    }

    override fun CoroutineScope.handleFeedingEvent(event: FeedingEvent) {
        when (event) {
            is WillFeedClicked -> handleWillFeedClicked(this, event)
            is DismissWillFeed -> dismissWillFeed()
            is AgreeWillFeed -> handleAgreeWillFeed(this)
            Cancel -> cancelFeeding()
            Expired -> expireFeeding()
            is Finish -> finishFeeding(event.feedingPhotos)
            Reset -> restartFeedingState()
        }
    }

    private fun handleAgreeWillFeed(scope: CoroutineScope) {
        dismissWillFeed()
        scope.launch { feedingPointId?.let { startFeeding(it) } }
    }

    private fun handleWillFeedClicked(scope: CoroutineScope, event: WillFeedClicked) {
        feedingPointId = event.feedingPointId

        checkPermissionsAndGps(scope = scope)
    }

    private fun checkPermissionsAndGps(scope: CoroutineScope) {
        with(permissionsStateFlow.value) {
            when {
                cameraPermissionStatus !is Granted -> {
                    updateState { copy(willFeedState = CameraPermissionRequested) }
                }

                geolocationPermissionStatus !is Granted && !isGeolocationPermissionRequestedAgain -> {
                    scope.handlePermissionEvent(GeolocationPermissionAsked)
                    updateState { copy(willFeedState = GeolocationPermissionRequested) }
                }

                gpsSettingsProvider.gpsSettingState == GpsSettingState.Disabled && !askedToEnableGps -> {
                    askedToEnableGps = true
                    updateState { copy(willFeedState = GpsSettingRequested) }
                }

                else -> {
                    updateState { copy(willFeedState = ConfirmationRequested) }
                }
            }
        }
    }

    private fun dismissWillFeed() {
        updateState { copy(willFeedState = Dismissed) }
    }

    private fun restartFeedingState() {
        updateState {
            copy(
                feedPoint = null,
                feedingConfirmationState = FeedingConfirmationState.Dismissed
            )
        }
    }

    private suspend fun startFeeding(id: String) {
        val feedingPoint = getFeedingPointByIdUseCase(id)
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
                updateFeedingState(FeedingConfirmationState.FeedingStarted)
            },
            onError = {
                updateFeedingState(FeedingConfirmationState.FeedingWasAlreadyBooked)
            }
        )
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
                    updateFeedingState(FeedingConfirmationState.Dismissed)
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
                    updateFeedingState(FeedingConfirmationState.Dismissed)
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
        updateFeedingState(FeedingConfirmationState.Dismissed)
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

    private fun updateFeedingState(feedConfirmationState: FeedingConfirmationState) {
        updateState {
            copy(
                feedingConfirmationState = feedConfirmationState
            )
        }
    }

    companion object {
        private const val FEEDING_TIMER_EXPIRED = "Feeding time has expired"
    }
}