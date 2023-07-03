package com.epmedu.animeal.favourites.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.favourites.presentation.ui.FavouritesScreenUI
import com.epmedu.animeal.favourites.presentation.viewmodel.FavouritesViewModel
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedViewModel
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouritesScreen() {
    val favouritesViewModel = hiltViewModel<FavouritesViewModel>()
    val willFeedViewModel = hiltViewModel<WillFeedViewModel>()

    val state by favouritesViewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(
        initialValue = AnimealBottomSheetValue.Hidden,
    )

    FavouritesScreenUI(
        state = state,
        bottomSheetState = bottomSheetState,
        onEvent = favouritesViewModel::handleEvents,
        onFeedingEvent = favouritesViewModel::handleFeedingEvent,
        onPermissionsEvent = favouritesViewModel::handlePermissionsEvent,
        onWillFeedEvent = willFeedViewModel::handleEvent
    )
    if (state.showingFeedingPoint != null) {
        LaunchedEffect(Unit) { bottomSheetState.expand() }
    }
}