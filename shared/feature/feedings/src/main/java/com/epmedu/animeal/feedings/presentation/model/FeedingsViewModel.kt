package com.epmedu.animeal.feedings.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingInProgressUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetFeedingHistoriesUseCase
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent
import com.epmedu.animeal.feedings.domain.usecase.GetFeedingPointsUseCase
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FeedingsViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getFeedingPointsUseCase: GetFeedingPointsUseCase,
    private val getFeedingInProgressUseCase: GetFeedingInProgressUseCase,
    private val getFeedingHistoriesUseCase: GetFeedingHistoriesUseCase
) : ViewModel(),
    StateDelegate<FeedingsState> by DefaultStateDelegate(initialState = FeedingsState()),
    ActionDelegate by actionDelegate {

    init {
        viewModelScope.launch { collectFeedingPoints() }
    }

    fun handleEvents(event: FeedingsScreenEvent) {
        when (event) {
            is FeedingsScreenEvent.FeedingApprove -> {
                tryApproveFeeding(event.feedingItem)
            }
            is FeedingsScreenEvent.FeedingReject -> {
                tryRejectFeeding(event.feedingItem, "no_reason")
            }
        }
    }

    private suspend fun collectFeedingPoints() {
        getFeedingPointsUseCase().collect { feedingPoints ->
            val feedingItems = mutableListOf<FeedingItem>()
            feedingPoints.map { feedingPoint ->
                getFeedingInProgressUseCase(feedingPoint.id).combine(
                    getFeedingHistoriesUseCase(feedingPoint.id, "approved") //TODO: get status from UI
                ) { feedingInProgress, feedingHistories ->
                    if (feedingInProgress != null) {
                        feedingItems.add(feedingInProgress.toFeedingItem(feedingPoint))
                    }
                    feedingHistories.forEach { feedingHistory ->
                        feedingItems.add(feedingHistory.toFeedingItem(feedingPoint))
                    }
                }.collect()
            }
            updateState {
                copy(
                    feedings = feedingItems.toImmutableList(),
                )
            }
        }
    }

    private fun tryRejectFeeding(feedingItem: FeedingItem, reason: String) {
        viewModelScope.launch {
//TODO: implement me
        }
    }

    private fun tryApproveFeeding(feedingItem: FeedingItem) {
        viewModelScope.launch {
//TODO: implement me
        }
    }
}