package com.epmedu.animeal.tabs.search.presentation

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.tabs.search.presentation.dogs.DogsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class SearchState(
    val dogsState: DogsState = DogsState(),
    val showingFeedSpot: FeedingPoint? = null,
    val showingWillFeedDialog: Boolean = false
)

@HiltViewModel
class SearchScreenViewModel @Inject constructor() : ViewModel(),
    StateDelegate<SearchState> by DefaultStateDelegate(initialState = SearchState()) {


}