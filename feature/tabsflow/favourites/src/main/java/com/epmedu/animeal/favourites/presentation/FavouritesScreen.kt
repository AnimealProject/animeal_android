package com.epmedu.animeal.favourites.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.favourites.presentation.ui.FavouritesScreenUI
import com.epmedu.animeal.favourites.presentation.viewmodel.FavouritesViewModel
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouritesScreen() {
    val viewModel = hiltViewModel<FavouritesViewModel>()

    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(
        initialValue = AnimealBottomSheetValue.Hidden,
    )

    FavouritesScreenUI(state, bottomSheetState, onEvent = {
        viewModel.handleEvents(it)
    })
    if (state.showingFeedSpot != null) {
        LaunchedEffect(Unit) { bottomSheetState.expand() }
    }
}