package com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding

import android.util.Log
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.domain.usecases.CancelFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.FinishFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.StartFeedingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Cancel
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Finish
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent.Start
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.ErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("LongParameterList")
internal class DefaultFeedingHandler @Inject constructor(
    stateDelegate: StateDelegate<HomeState>,
    routeHandler: RouteHandler,
    errorHandler: ErrorHandler,
    feedingPointHandler: FeedingPointHandler,
    private val startFeedingUseCase: StartFeedingUseCase,
    private val cancelFeedingUseCase: CancelFeedingUseCase,
    private val finishFeedingUseCase: FinishFeedingUseCase,
    private val dispatchers: Dispatchers
) : FeedingHandler,
    StateDelegate<HomeState> by stateDelegate,
    FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    ErrorHandler by errorHandler {

    override fun CoroutineScope.handleFeedingEvent(event: FeedingEvent) {
        when (event) {
            Start -> launch { startFeeding() }
            Cancel -> launch { cancelFeeding() }
            Finish -> launch { finishFeeding() }
        }
    }

    private suspend fun startFeeding() {
        performFeedingAction(
            action = startFeedingUseCase::invoke,
            onSuccess = { currentFeedingPoint ->
                hideOtherFeedingPoints(currentFeedingPoint)
                startRouteAndTimer()
            }
        )
    }

    private suspend fun cancelFeeding() {
        performFeedingAction(
            action = cancelFeedingUseCase::invoke,
            onSuccess = {
                stopRouteAndTimer()
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
                stopRouteAndTimer()
                fetchFeedingPoints()
            }
        )
    }

    private suspend fun performFeedingAction(
        action: suspend (String) -> ActionResult,
        onSuccess: suspend (FeedingPointModel) -> Unit
    ) {
        state.currentFeedingPoint?.let { currentFeedingPoint ->
            coroutineScope {
                when (val result = withContext(dispatchers.IO) { action(currentFeedingPoint.id) }) {
                    is ActionResult.Success -> {
                        onSuccess(currentFeedingPoint)
                    }
                    is ActionResult.Failure -> {
                        showError()
                        Log.e(LOG_TAG, result.error.toString())
                    }
                }
            }
        } ?: showError()
    }

    private companion object {
        const val LOG_TAG = "DefaultFeedingHandler"
    }
}