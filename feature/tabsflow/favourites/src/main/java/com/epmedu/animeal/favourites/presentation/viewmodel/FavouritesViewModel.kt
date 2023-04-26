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
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.WillFeedHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouritesViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getFavouriteFeedingPointsUseCase: GetFavouriteFeedingPointsUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
    private val willFeedHandler: WillFeedHandler,
) : ViewModel(),
    WillFeedHandler by willFeedHandler,
    StateDelegate<FavouritesState> by DefaultStateDelegate(initialState = FavouritesState()),
    ActionDelegate by actionDelegate {

    init {
        viewModelScope.launch {
            getFavouriteFeedingPointsUseCase().collect { feedingPoints ->
                updateState { copy(favourites = feedingPoints.toImmutableList()) }
            }
        }
    }

    fun handleEvents(event: FavouritesScreenEvent) {
        when (event) {
            is FavouriteChange -> handleFavouriteChange(event)
            is FeedingPointSelected -> updateState { copy(showingFeedingPoint = event.feedingPoint) }
            is FeedingPointHidden -> updateState { copy(showingFeedingPoint = null) }
            /*is ShowWillFeedDialog -> updateState { copy(showingWillFeedDialog = true) }
            is DismissWillFeedDialog -> updateState { copy(showingWillFeedDialog = false) }*/
        }
    }

    private fun handleFavouriteChange(event: FavouriteChange) {
        when {
            event.isFavourite -> addFeedingPointToFavourites(event.feedingPoint)
            else -> removeFeedingPointFromFavourites(event.feedingPoint)
        }
    }

    private fun addFeedingPointToFavourites(feedingPoint: FeedingPoint) {
        markFeedingPointAsFavourite(feedingPoint)
        tryAddingFeedingPointToFavourites(feedingPoint)
    }

    private fun removeFeedingPointFromFavourites(feedingPoint: FeedingPoint) {
        unmarkFeedingPointFromFavourites(feedingPoint)
        tryRemovingFeedingPointFromFavourites(feedingPoint)
    }

    private fun markFeedingPointAsFavourite(feedingPoint: FeedingPoint) {
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

    private fun unmarkFeedingPointFromFavourites(feedingPoint: FeedingPoint) {
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

    private fun tryAddingFeedingPointToFavourites(feedingPoint: FeedingPoint) {
        viewModelScope.launch {
            performAction(
                action = { addFeedingPointToFavouritesUseCase(feedingPoint.id) },
                onError = { unmarkFeedingPointFromFavourites(feedingPoint) }
            )
        }
    }

    private fun tryRemovingFeedingPointFromFavourites(feedingPoint: FeedingPoint) {
        viewModelScope.launch {
            performAction(
                action = { removeFeedingPointFromFavouritesUseCase(feedingPoint.id) },
                onError = { markFeedingPointAsFavourite(feedingPoint) }
            )
        }
    }
}
