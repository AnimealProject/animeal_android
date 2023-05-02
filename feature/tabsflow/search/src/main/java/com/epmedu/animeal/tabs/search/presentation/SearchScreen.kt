package com.epmedu.animeal.tabs.search.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.tabs.search.presentation.ui.SearchScreenUi
import com.epmedu.animeal.tabs.search.presentation.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen() {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchState by searchViewModel.stateFlow.collectAsState()

    val bottomSheetState = rememberAnimealBottomSheetState(AnimealBottomSheetValue.Hidden)

    SearchScreenUi(
        searchState,
        bottomSheetState,
        onEvent = searchViewModel::handleEvents,
        onWillFeedEvent = searchViewModel::handleWillFeedEvent,
    )

    if (searchState.showingFeedingPoint != null) {
        LaunchedEffect(Unit) { bottomSheetState.expand() }
    }
}