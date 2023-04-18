package com.epmedu.animeal.feedconfirmation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feedconfirmation.domain.StartFeedingPointUseCase
import com.epmedu.animeal.feedconfirmation.domain.ValidateFeedingPointAvailableUseCase
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedConfirmationViewModel @Inject constructor(
    private val validateFeedingPointAvailableUseCase: ValidateFeedingPointAvailableUseCase,
    private val startFeedingPointUseCase: StartFeedingPointUseCase,
) : ViewModel(),
    StateDelegate<FeedConfirmationState>
    by DefaultStateDelegate(initialState = FeedConfirmationState()) {

        fun handleEvents(event: FeedConfirmationEvent) {
            when (event) {
                is FeedConfirmationEvent.AcceptFeedDialog -> handleAcceptFeed(event)
            }
        }

        private fun handleAcceptFeed(event: FeedConfirmationEvent.AcceptFeedDialog) {
            viewModelScope.launch {
                validateFeedingPointAvailableUseCase(event.feedingPointId)
                    .map { isAvailableYet ->
                        if (isAvailableYet) {
                            startFeedingPointUseCase(event.feedingPointId)
                        } else {
                            updateState {
                                copy(
                                    showLoader = false,
                                    showBookingException = true
                                )
                            }
                        }
                    }.collect()
            }
        }
    }