package com.epmedu.animeal.favourites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.favourites.domain.GetFavouriteFeedingPointsUseCase
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FavouriteChange
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FeedingPointHidden
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent.FeedingPointSelected
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingHistoriesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingInProgressUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.presentation.model.Feeding
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
internal class FavouritesViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getFavouriteFeedingPointsUseCase: GetFavouriteFeedingPointsUseCase,
    private val getFeedingInProgressUseCase: GetFeedingInProgressUseCase,
    private val getFeedingHistoriesUseCase: GetFeedingHistoriesUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
    private val permissionsHandler: PermissionsHandler,
    private val feedingHandler: FeedingHandler,
) : ViewModel(),
    StateDelegate<FavouritesState> by DefaultStateDelegate(initialState = FavouritesState()),
    PermissionsHandler by permissionsHandler,
    FeedingHandler by feedingHandler,
    ActionDelegate by actionDelegate {

    private var fetchFeedingsJob: Job? = null

    init {
        viewModelScope.launch { collectFeedingPoints() }
        viewModelScope.launch { collectPermissionsState() }
        viewModelScope.launch {
            feedingStateFlow.collectLatest { feedingState ->
                updateState {
                    copy(
                        feedState = feedingState
                    )
                }
            }
        }
    }

    private suspend fun collectFeedingPoints() {
        getFavouriteFeedingPointsUseCase().collect { domainFeedingPoints ->
            val feedingPoints = domainFeedingPoints.map { FeedingPointModel(it) }.toImmutableList()
            val showingFeedingPoint = feedingPoints.find { it.id == state.showingFeedingPoint?.id }

            val feedingPointToShow = when (showingFeedingPoint?.feedStatus) {
                state.showingFeedingPoint?.feedStatus -> {
                    showingFeedingPoint?.copy(
                        feedings = state.showingFeedingPoint?.feedings
                    )
                }
                else -> {
                    showingFeedingPoint?.let { fetchFeedings(showingFeedingPoint.id) }
                    showingFeedingPoint
                }
            }
            showingFeedingPoint?.let { fetchFeedings(showingFeedingPoint.id) }
            updateState {
                copy(
                    favourites = feedingPoints,
                    showingFeedingPoint = feedingPointToShow
                )
            }
        }
    }

    fun handleEvents(event: FavouritesScreenEvent) {
        when (event) {
            is FavouriteChange -> handleFavouriteChange(event)
            is FeedingPointSelected -> handleFeedingPointSelected(event)
            is FeedingPointHidden -> updateState { copy(showingFeedingPoint = null) }
        }
    }

    private fun handleFeedingPointSelected(event: FeedingPointSelected) {
        updateState { copy(showingFeedingPoint = event.feedingPoint) }
        fetchFeedings(event.feedingPoint.id)
    }

    private fun fetchFeedings(feedingPointId: String) {
        fetchFeedingsJob?.cancel()
        fetchFeedingsJob = viewModelScope.launch {
            getFeedingInProgressUseCase(feedingPointId).combine(
                getFeedingHistoriesUseCase(feedingPointId)
            ) { feedingInProgress, feedingHistories ->
                val feedings = listOf(
                    feedingInProgress?.let { Feeding.InProgress(feedingInProgress) }
                ) + feedingHistories.map { Feeding.History(it) }

                updateState {
                    copy(
                        showingFeedingPoint = showingFeedingPoint?.copy(
                            feedings = feedings.filterNotNull()
                        )
                    )
                }
            }.collect()
        }
    }

    fun handlePermissionsEvent(event: PermissionsEvent) {
        viewModelScope.handlePermissionEvent(event)
    }

    private suspend fun collectPermissionsState() {
        permissionsStateFlow.collect { permissionsState ->
            updateState { copy(permissionsState = permissionsState) }
        }
    }

    private fun handleFavouriteChange(event: FavouriteChange) {
        when {
            event.isFavourite -> addFeedingPointToFavourites(event.feedingPoint)
            else -> removeFeedingPointFromFavourites(event.feedingPoint)
        }
    }

    fun handleFeedingEvent(event: FeedingEvent) {
        viewModelScope.handleFeedingEvent(event)
    }

    private fun addFeedingPointToFavourites(feedingPoint: FeedingPointModel) {
        markFeedingPointAsFavourite(feedingPoint)
        tryAddingFeedingPointToFavourites(feedingPoint)
    }

    private fun removeFeedingPointFromFavourites(feedingPoint: FeedingPointModel) {
        unmarkFeedingPointFromFavourites(feedingPoint)
        tryRemovingFeedingPointFromFavourites(feedingPoint)
    }

    private fun markFeedingPointAsFavourite(feedingPoint: FeedingPointModel) {
        updateState {
            copy(
                favourites = (favourites + feedingPoint).toImmutableList(),
                showingFeedingPoint = when (showingFeedingPoint) {
                    feedingPoint -> feedingPoint.copy(isFavourite = true)
                    else -> showingFeedingPoint
                }
            )
        }
    }

    private fun unmarkFeedingPointFromFavourites(feedingPoint: FeedingPointModel) {
        updateState {
            copy(
                favourites = (favourites - feedingPoint).toImmutableList(),
                showingFeedingPoint = when (showingFeedingPoint) {
                    feedingPoint -> feedingPoint.copy(isFavourite = false)
                    else -> showingFeedingPoint
                }
            )
        }
    }

    private fun tryAddingFeedingPointToFavourites(feedingPoint: FeedingPointModel) {
        viewModelScope.launch {
            performAction(
                action = { addFeedingPointToFavouritesUseCase(feedingPoint.id) },
                onError = { unmarkFeedingPointFromFavourites(feedingPoint) }
            )
        }
    }

    private fun tryRemovingFeedingPointFromFavourites(feedingPoint: FeedingPointModel) {
        viewModelScope.launch {
            performAction(
                action = { removeFeedingPointFromFavouritesUseCase(feedingPoint.id) },
                onError = { markFeedingPointAsFavourite(feedingPoint) }
            )
        }
    }
}
